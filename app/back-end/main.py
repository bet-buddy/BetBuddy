import json
import ast
import requests
import firebase_admin
from firebase_admin import credentials
from firebase_admin import db
from flask import Flask

API_KEY = 'a31718bb64296d9f4f3b529e317ab7ff'
SPORT_KEY = 'soccer_epl'
BET365 = 5

app = Flask(__name__)

# Fetch the service account key JSON file contents
cred = credentials.Certificate('./bet-buddy-odds-firebase-adminsdk.json')
# Initialize the app with a service account, granting admin privileges
firebase_admin.initialize_app(cred, {
    'databaseURL': 'https://bet-buddy-odds.firebaseio.com/'
})
ref = db.reference('matches')

odds_list = []

def parseEvent(json):
    result = []
    #parsed_json = ast.literal_eval(json)
    #parsed_json = json.loads(json_file)
    result.append(json["teams"][0])
    result.append(json["teams"][1])
    result.append(json["sites"][BET365]["odds"]["h2h"])
    return result;

odds_response = requests.get('https://api.the-odds-api.com/v3/odds', params={
    'api_key': API_KEY,
    'sport': SPORT_KEY,
    'region': 'uk', # uk | us | au
    'mkt': 'h2h' # h2h | spreads | totals
})

odds_json = json.loads(odds_response.text)
if not odds_json['success']:
    print(
        'There was a problem with the odds request:',
        odds_json['msg']
    )
else:
    #print(odds_json['data'][0])
    print('Remaining requests', odds_response.headers['x-requests-remaining'])
    print('Used requests', odds_response.headers['x-requests-used'])
    for x in range(0, len(odds_json['data'])):
        odds = odds_json['data'][x]
        odds_list.append(parseEvent(odds_json['data'][x]));

    print(odds_list[2][2][2])
    ref.push(odds_list)
