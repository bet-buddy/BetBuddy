package e.iot.betbuddy.model;

import java.util.ArrayList;

public class Bets {

    private Boolean success;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
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
