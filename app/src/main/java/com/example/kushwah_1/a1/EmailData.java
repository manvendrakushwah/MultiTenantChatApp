package com.example.kushwah_1.a1;

public class EmailData {
    String subject;
    String  sender;
    String[] receivers;

    public EmailData(String subject, String sender, String[] receivers) {
        this.subject = subject;
        this.sender = sender;
        this.receivers = receivers;
    }

    public String getSubject() {
        return subject;
    }

    public String getSender() {
        return sender;
    }

    public String[] getReceivers() {
        return receivers;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setRecievers(String[] receivers) {
        this.receivers = receivers;
    }


}
