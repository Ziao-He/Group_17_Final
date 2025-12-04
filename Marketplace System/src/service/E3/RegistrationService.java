/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.E3;

import basement_class.Enterprise_3.WorkRequest.RegistrationApprovalRequest;
import basement_class.UserAccount;

/**
 *
 * @author Administrator
 */
public class RegistrationService {
    
       public void approve(RegistrationApprovalRequest req) {
        UserAccount account = req.getNewUser();
        account.setStatus("ACTIVE");
        req.setStatus("APPROVED");
        req.resolve();
    }

    public void reject(RegistrationApprovalRequest req, String reason) {
        UserAccount account = req.getNewUser();
        account.setStatus("REJECTED");
        req.setStatus("REJECTED: " + reason);
        req.resolve();
    }
}
