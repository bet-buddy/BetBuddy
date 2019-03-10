import json
import ast
import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
import uuid

API_KEY = 'b3496429f5a38cffe315865f31719b21'


from flask import Flask

import requests
from flask import request
app = Flask(__name__)


cred = credentials.Certificate("./bet-buddy-odds-firebase-adminsdk-uxwyx-d5715be64f.json")
firebase_admin.initialize_app(cred)
db = firestore.client()

#default_app = firebase_admin.initialize_app()

#the odds-api-uri:
API_URL = 'https://api.the-odds-api.com/v3/odds/'

# Use the App Engine Requests adapter. This makes sure that Requests uses
# URLFetch.

# If `entrypoint` is not defined in app.yaml, App Engine will look for an app
# called `app` in `main.py`.
app = Flask(__name__)

regions = ['au','uk','us']

mkts = ['h2h','uk','us']

def valid_game(team1,team2,region,sport,mkt):
	if(team1!=None and team2!=None and (region in regions) and (mkt in mkts) and sport != None ):
		return True
	else:
		return False

# Retrieve games from api according to parameters chosen by client 
def retrieveGames(sport,region,mkt):
	params = '&sport='+sport+'&region='+region+'&mkt='+mkt
	url = API_URL+'?'+'apiKey='+API_KEY+params
	response = requests.get(url)
	data = response.json()["data"]
	return data

# Find odds according to params set by client
def findOdds(data,team1,team2):
	for game in data:
			if((team1 in game["teams"]) and (team2 in game["teams"]) ):
				print(game["teams"])
				print(game["sites"][0]["odds"]["h2h"])
				odds = game["sites"][0]["odds"]["h2h"]
				return odds
	return None

# Endpoint :  GET request => every parameter is in the URL
@app.route('/bets',methods=['GET'])
def bets():
	error = None
	team1 = request.args.get('team1')
	team2 = request.args.get('team2')
	region = request.args.get('region')
	sport = request.args.get('sport')
	mkt = request.args.get('mkt')
	if(valid_game(team1,team2,region,sport,mkt)):
		data = retrieveGames(sport,region,mkt)
		odds = findOdds(data,team1,team2)
		oid = uuid.uuid1()
		odd_dict = {'odds':odds,'teams':[team1,team2],'oid':str(oid)}
		db.collection('odds').document(str(oid)).set(odd_dict)
		if(odds == None):
			return 'bad game error'
		else:
			return '200-ok'

	else:
		error = 'Invalid game'
	return error

if __name__ == '__main__':
    # This is used when running locally only. When deploying to Google App
    # Engine, a webserver process such as Gunicorn will serve the app. This
    # can be configured by adding an `entrypoint` to app.yaml.
    app.run(host='127.0.0.1', port=8080, debug=True)
# [END gae_python37_app]