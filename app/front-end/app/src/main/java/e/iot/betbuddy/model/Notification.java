package e.iot.betbuddy.model;

import java.util.ArrayList;
import java.util.List;

public class Notification {
    private String receiver = "";
    private String receiverid = "";
    private String sender = "";

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

    public int getSenderPoints() {
        return senderPoints;
    }

    public void setSenderPoints(int senderPoints) {
        this.senderPoints = senderPoints;
    }

    private String away_team = "";
    private List<Float> odds = new ArrayList<>();
    private int senderPoints = 0;
    private String senderid = "";
    private String type = "";
    public Notification() {}
}
