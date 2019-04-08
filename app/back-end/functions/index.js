const functions = require('firebase-functions');

const admin = require('firebase-admin');
admin.initializeApp();

exports.sendNotification =  functions.firestore.document('Notifications/{NotificationId}')
    .onCreate((snapshot, context)=>{
        var notif = snapshot.data();
        var receiver = notif.receiver;
        var sender = notif.sender;
        console.log("Odds : ",notif.odds);

        const payload = {
            notification : {
                title : sender+' challenges you!',
                body : notif.home_team + ' VS '+notif.away_team
            },
            data : {
            	sender: notif.sender,
            	away_team : notif.away_team,
            	home_team : notif.home_team,
            	away_team_odd : notif.odds[1].toString(),
            	home_team_odd : notif.odds[0].toString(),
            	senderbet : notif.senderbet,
            	senderpoints : notif.senderpoints.toString(),
            	senderid : notif.senderid,
            	topic : notif.topic
            }
        };
        return admin.messaging().sendToDevice(notif.receivertoken,payload)
            .then(response => {
                console.log("Successfully sent message: ",response);
                return response;
            })
            .catch(error => {
                return console.log("Error sending messagge : ",error);
            })
    });


exports.onBetCreate = functions.firestore.document('Pendingbets/{PendingBetId}').onCreate((snapshot,context) =>
{
	pendingBet = snapshot.data();
	topic = pendingBet.topic;
	console.log("topic = "+topic);
	const payload = {
            notification : {
                title : 'Paul Estano won this bet!',
                body : pendingBet.home_team + ' VS '+pendingBet.away_team
            }
        };
    return new Promise((resolve,reject) => {setTimeout(()=>{
    	return admin.messaging().sendToTopic(topic,payload)
            .then(response => {
                console.log("Successfully sent message: ",response);
                return response;
            })
            .catch(error => {
                return console.log("Error sending messagge : ",error);
            })
    },10000);});
	
});