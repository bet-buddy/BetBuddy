package e.iot.betbuddy.model;

import java.util.ArrayList;

public class Bets {

    private Boolean succes;

    public Boolean getSucces() {
        return succes;
    }

    public void setSucces(Boolean succes) {
        this.succes = succes;
    }

    public ArrayList<Bet> getData() {
        return data;
    }

    public void setData(ArrayList<Bet> data) {
        this.data = data;
    }

    private ArrayList<Bet> data;

    public Bets() {}


}
