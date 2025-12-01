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
    private UserAccount targetUser;
    private String action; // suspend, reactivate…

    public AccountStatusReviewRequest(UserAccount user, String action) {
        super();
        this.targetUser = user;
        this.action = action;
        this.setStatus("Pending");
    }

    public UserAccount getTargetUser() { 
        return targetUser; 
    }
    
    public String getAction() { 
        return action; 
    }    
    
}
