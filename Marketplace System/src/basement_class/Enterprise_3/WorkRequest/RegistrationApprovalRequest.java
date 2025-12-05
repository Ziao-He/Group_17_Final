/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_3.WorkRequest;

import basement_class.UserAccount;
import basement_class.WorkRequest;

/**
 *
 * @author Liniyiyang
 */
public class RegistrationApprovalRequest extends WorkRequest{
    private UserAccount newUser;
    private String reason;

    public RegistrationApprovalRequest(UserAccount newUser) {
        super();
        this.newUser = newUser;
        this.setStatus("Pending");
    }

    public UserAccount getNewUser() {
        return newUser;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    
}
