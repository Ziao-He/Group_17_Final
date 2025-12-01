/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.E3;

import basement_class.Enterprise_3.WorkRequest.ViolationInvestigationRequest;



/**
 *
 * @author Linyiyang
 */
public class PolicyEnforcementService {
    
    public void handleViolation(ViolationInvestigationRequest req, boolean banUser) {

        if (banUser) {
            req.getTargetUser().setStatus("Banned");
            req.setStatus("User Banned");
        } else {
            req.setStatus("Warning Issued");
        }

        req.resolve();
    }
}
