package e.iot.betbuddy.model;

import java.util.ArrayList;
import java.util.List;

public class Notification {
    private String receiver = "";
    private String receiverid = "";
    private String sender = "";

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    private String topic = "";
    public int getSenderpoints() {
        return senderpoints;
    }

    public void setSenderpoints(int senderpoints) {
        this.senderpoints = senderpoints;
    }

    private int senderpoints = 0;

    public int getReceiverpoints() {
        return receiverpoints;
    }

    public void setReceiverpoints(int receiverpoints) {
        this.receiverpoints = receiverpoints;
    }

    private int receiverpoints =0;
    public String getSenderbet() {
        return senderbet;
    }

    public void setSenderbet(String senderbet) {
        this.senderbet = senderbet;
    }

    public String getReceiverbet() {
        return receiverbet;
    }

    public void setReceiverbet(String receiverbet) {
        this.receiverbet = receiverbet;
    }

    private String senderbet = "";
    private String receiverbet = "";
    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiverid() {
        return receiverid;
    }

    public void setReceiverid(String receiverid) {
        this.receiverid = receiverid;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    private String home_team ="";

    public String getHome_team() {
        return home_team;
    }

    public void setHome_team(String home_team) {
        this.home_team = home_team;
    }

    public String getAway_team() {
        return away_team;
    }

    public void setAway_team(String away_team) {
        this.away_team = away_team;
    }

    public List<Float> getOdds() {
        return odds;
    }

    public void setOdds(List<Float> odds) {
        this.odds = odds;
    }



    private String away_team = "";
    private List<Float> odds = new ArrayList<>();

    private String senderid = "";
    private String type = "";

    public String getReceivertoken() {
        return receivertoken;
    }

    public void setReceivertoken(String receivertoken) {
        this.receivertoken = receivertoken;
    }

    private String receivertoken ="";
    public Notification() {}
}
