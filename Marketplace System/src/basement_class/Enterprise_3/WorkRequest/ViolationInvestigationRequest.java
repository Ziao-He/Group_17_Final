/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_3.WorkRequest;

import basement_class.UserAccount;
import basement_class.WorkRequest;

/**
 *
 * @author Linyiyang
 */
public class ViolationInvestigationRequest extends WorkRequest {

    private UserAccount targetUser;
    private String violationInfo;

    public ViolationInvestigationRequest(UserAccount user, String violationInfo) {
        super();
        this.targetUser = user;
        this.violationInfo = violationInfo;
        this.setStatus("Pending");
    }

    public UserAccount getTargetUser() { 
        return targetUser; 
    }
    
    public String getViolationInfo() { 
        return violationInfo; 
    }    
    
}
