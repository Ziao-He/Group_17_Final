/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.E3;

import basement_class.DAO.WorkRequestFileDAO;
import basement_class.EcoSystem;
import basement_class.Enterprise_3.WorkRequest.AccountStatusReviewRequest;
import basement_class.Enterprise_3.WorkRequest.PolicyViolationRequest;
import basement_class.Enterprise_3.WorkRequest.RegistrationApprovalRequest;
import basement_class.UserAccount;

/**
 *
 * @author linyiyang
 */


public class UserManagementService{
    public void suspendUser(AccountStatusReviewRequest req,
                            UserAccount admin,
                            String reason) {

        UserAccount user = req.getTargetUser();

        user.setStatus("SUSPENDED");

        req.setStatus("SUSPENDED");

        req.resolve(admin, "SUSPEND_USER", reason);
        new WorkRequestFileDAO().append(req);
            EcoSystem.getInstance()
    .getWorkRequestDirectory()
    .addWorkRequest(req);                       
        WorkRequestRouter.routeToEnterprise3(req);
    }

    /**
     * Reactivate a user that was previously suspended
     */
     public void reactivateUser(AccountStatusReviewRequest req,
                               UserAccount admin,
                               String reason) {

        UserAccount user = req.getTargetUser();


        user.setStatus("ACTIVE");

        req.setStatus("REACTIVATED");


        req.resolve(admin, "REACTIVATE_USER", reason);
        new WorkRequestFileDAO().append(req);
            EcoSystem.getInstance()
    .getWorkRequestDirectory()
    .addWorkRequest(req);                       
    WorkRequestRouter.routeToEnterprise3(req);
    }


    /**
     * Permanently ban a user
     * @param req
     */
public void banUser(AccountStatusReviewRequest req, UserAccount admin,String reason) {
    UserAccount user = req.getTargetUser();
    
    user.setStatus("BANNED");
    req.setStatus("BANNED");
    req.resolve(admin, "REJECT_USER", reason);
    new WorkRequestFileDAO().append(req);
        EcoSystem.getInstance()
    .getWorkRequestDirectory()
    .addWorkRequest(req);                       
    WorkRequestRouter.routeToEnterprise3(req);
}
    
public void rejectUser(AccountStatusReviewRequest req, UserAccount admin, String reason) {

    UserAccount user = req.getTargetUser();

    user.setStatus("REJECTED");
    req.setStatus("REJECTED");


    req.resolve(admin, "REJECT_USER", reason);
    new WorkRequestFileDAO().append(req);
        EcoSystem.getInstance()
    .getWorkRequestDirectory()
    .addWorkRequest(req);                       
    WorkRequestRouter.routeToEnterprise3(req);
}


public void issueWarning(AccountStatusReviewRequest req, UserAccount admin) {

    UserAccount user = req.getTargetUser();

    user.incrementWarning();

    if (!"BANNED".equals(user.getStatus())) {
        user.setStatus("ACTIVE");
    }

    if (user.getWarningCount() >= 3) {
        user.setStatus("BANNED");
    }

    req.setStatus("WARNING_ISSUED");


    req.resolve(admin, "ISSUE_WARNING",
            "Total warnings = " + user.getWarningCount());
    new WorkRequestFileDAO().append(req);
        EcoSystem.getInstance()
    .getWorkRequestDirectory()
    .addWorkRequest(req);                       
    WorkRequestRouter.routeToEnterprise3(req);
}

public void issueWarningByRequest(PolicyViolationRequest req,
                                  UserAccount admin) {

    UserAccount user = req.getTargetUser();


    user.incrementWarning();

    if (!"BANNED".equals(user.getStatus())) {
        user.setStatus("ACTIVE");
    }

    if (user.getWarningCount() >= 3) {
        user.setStatus("BANNED");
    }

    // PolicyEnforcementService resolve
}
}

