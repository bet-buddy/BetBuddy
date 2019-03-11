package e.iot.betbuddy.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Bet {
    private String sport_key;
    private String sport_nice;
    private ArrayList<String> teams;
    private long commence_time;
    private String home_team;
    private ArrayList<Site> sites;

    public String getSport_key() {
        return sport_key;
    }

    public void setSport_key(String sport_key) {
        this.sport_key = sport_key;
    }

    public String getSport_nice() {
        return sport_nice;
    }

    public void setSport_nice(String sport_nice) {
        this.sport_nice = sport_nice;
    }

    public ArrayList<String> getTeams() {
        return teams;
    }

    public void setTeams(ArrayList<String> teams) {
        this.teams = teams;
    }

    public long getCommence_time() {
        return commence_time;
    }

    public void setCommence_time(long commence_time) {
        this.commence_time = commence_time;
    }

    public String getHome_team() {
        return home_team;
    }

    public void setHome_team(String home_team) {
        this.home_team = home_team;
    }

    public ArrayList<Site> getSites() {
        return sites;
    }

    public void setSites(ArrayList<Site> sites) {
        this.sites = sites;
    }

    public Bet() {}
}
