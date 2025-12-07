/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.E3;

import basement_class.Enterprise_3.WorkRequest.RegistrationApprovalRequest;
import basement_class.UserAccount;

/**
 *
 * @author Linyiyang
 */
public class RegistrationService {
    
public void approve(RegistrationApprovalRequest req, UserAccount admin) {
    req.getNewUser().setStatus("ACTIVE");
    req.setStatus("APPROVED");
    req.resolve(admin, "APPROVE", "Registration approved");
}

public void reject(RegistrationApprovalRequest req, UserAccount admin, String reason) {
    req.getNewUser().setStatus("REJECTED");
    req.setStatus("REJECTED");
    req.resolve(admin, "REJECT", reason);
}
}
