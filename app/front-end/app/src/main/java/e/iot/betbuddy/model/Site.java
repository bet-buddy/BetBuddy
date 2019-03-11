package e.iot.betbuddy.model;

import java.util.ArrayList;


public class Site {
    private String site_key;
    private String site_nice;
    private long last_update;
    private ArrayList<Odds> odds;

    public String getSite_key() {
        return site_key;
    }

    public void setSite_key(String site_key) {
        this.site_key = site_key;
    }

    public String getSite_nice() {
        return site_nice;
    }

    public void setSite_nice(String site_nice) {
        this.site_nice = site_nice;
    }

    public long getLast_update() {
        return last_update;
    }

    public ArrayList<Odds> getOdds() {
        return odds;
    }

    public void setOdds(ArrayList<Odds> odds) {
        this.odds = odds;
    }

    public Site() {}

}
