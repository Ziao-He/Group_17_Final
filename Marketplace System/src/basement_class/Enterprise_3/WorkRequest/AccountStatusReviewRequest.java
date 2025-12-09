/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_3.WorkRequest;

import basement_class.UserAccount;
import basement_class.WorkRequest;

/**
 *
 * @author Administrator
 */
public class AccountStatusReviewRequest extends WorkRequest {
    private static int counter=1;
    private UserAccount targetUser;            
    private String action;                     // suspend, reactivate, ban
    private String requestDescription;         
    private String reviewerDecisionReason;     

    public AccountStatusReviewRequest(UserAccount user, String action, String requestDescription) {
        super();
        this.targetUser = user;
        this.action = action;
        this.requestDescription = requestDescription;
        this.setStatus("PENDING");
        this.setId("ASRQ"+counter++);
    }

    public UserAccount getTargetUser() { 
        return targetUser; 
    }
    
    public String getAction() { 
        return action; 
    }

    public String getRequestDescription() {
        return requestDescription;
    }

    public void setRequestDescription(String desc) {
        this.requestDescription = desc;
    }

    public String getReviewerDecisionReason() {
        return reviewerDecisionReason;
    }

    public void setReviewerDecisionReason(String reason) {
        this.reviewerDecisionReason = reason;
    }


}