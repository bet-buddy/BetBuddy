package e.iot.betbuddy.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User {
    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String uid="";
    private List<Group> groupList=new ArrayList<>();
    private String name="";
    public User(){}
//    public User(String uid,List<Group> groupList,String name) {
//        this.uid = uid;
//        this.groupList = groupList;
//        this.name = name;
//    }

    public String getName() {return name;}
    public String getUid() {return uid;}
    public List<Group> getGroupList() {return groupList;}
}

