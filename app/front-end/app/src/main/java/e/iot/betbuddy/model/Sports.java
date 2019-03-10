package e.iot.betbuddy.model;

import java.util.ArrayList;

public class Sports {

    private Boolean succes;

    public Boolean getSucces() {
        return succes;
    }

    public void setSucces(Boolean succes) {
        this.succes = succes;
    }

    public ArrayList<Sport> getData() {
        return data;
    }

    public void setData(ArrayList<Sport> data) {
        this.data = data;
    }

    private ArrayList<Sport> data;

    public Sports() {}


}
