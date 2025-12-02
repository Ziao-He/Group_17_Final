/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_4;

import basement_class.BaseEntity;
import basement_class.UserAccount;

/**
 *
 * @author yujie-liang
 */
public class Message extends BaseEntity{
    private UserAccount sender;
    private UserAccount receiver;
    private String content;
    private boolean isFlagged;
    private boolean flaggedByUser;

    public Message(UserAccount sender, UserAccount receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.isFlagged = false;
        this.flaggedByUser = false;
    }
    
    public void contentIsInappropriate(){
        isFlagged = true;
        this.touch();
    }
    
    public void flagByUser(){
        flaggedByUser = true;
        this.touch();
    }

    public UserAccount getSender() {
        return sender;
    }

    public void setSender(UserAccount sender) {
        this.sender = sender;
    }

    public UserAccount getReceiver() {
        return receiver;
    }

    public void setReceiver(UserAccount receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isIsFlagged() {
        return isFlagged;
    }

    public void setIsFlagged(boolean isFlagged) {
        this.isFlagged = isFlagged;
    }



    public boolean isFlaggedByUser() {
        return flaggedByUser;
    }

    public void setFlaggedByUser(boolean flaggedByUser) {
        this.flaggedByUser = flaggedByUser;
    }
    
    
}

