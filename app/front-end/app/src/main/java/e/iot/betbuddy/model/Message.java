package e.iot.betbuddy.model;

import com.google.firebase.Timestamp;

public class Message {

    public String getMid() {
        return mid;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public Timestamp getTime() {
        return time;
    }

    public String mid;

    public String author;
    public String content;
    public String title;
    public Timestamp time;

    public Message() {}

}
