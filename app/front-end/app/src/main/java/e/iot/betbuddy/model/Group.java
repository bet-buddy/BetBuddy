package e.iot.betbuddy.model;

import java.util.ArrayList;
import java.util.List;

public class Group {
    public String gid;
    public String group;
    public ArrayList<User> users;

    public String getGid() {
        return gid;
    }

    public String getGroup() {
        return group;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public ArrayList<Message> messages;
    public Group(){}

}
