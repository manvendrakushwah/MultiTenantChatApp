package com.example.kushwah_1.a1;

public class MessageClass {
    String Date;
    String sender;
    String subject;
    String text;
    String time;
    String senderUid;

    public MessageClass(   String Date,String sender,String subject,String text,String time, String senderUid) {
        this.subject = subject;
        this.text = text;
        this.sender = sender;
        this.Date=Date;
        this.time=time;
        this.senderUid=senderUid;
    }

    public String getSenderUid() {
        return senderUid;
    }

    public void setSenderUid(String senderUid) {
        this.senderUid = senderUid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getDateTime(){
        return Date+""+time;
    }
}