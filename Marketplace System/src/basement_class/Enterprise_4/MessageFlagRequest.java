/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_4;

import basement_class.UserAccount;
import basement_class.WorkRequest;

/**
 *
 * @author yujie-liang
 */
public class MessageFlagRequest extends WorkRequest {

    private Message flaggedMessage;   
    private String reason;

    public MessageFlagRequest(Message flaggedMessage,
                              UserAccount reporter,
                              String reason) {
        super();
        this.flaggedMessage = flaggedMessage;
        this.sender = reporter;
        this.reason = reason;
        this.setStatus("Pending");
    }

    public Message getFlaggedMessage() {
        return flaggedMessage;
    }

    public String getReason() {
        return reason;
    }
}