/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.E3;

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

        // 1. 修改用户状态
        user.setStatus("SUSPENDED");

        // 2. 更新工单状态
        req.setStatus("SUSPENDED");

        // ✅ 3. 统一审计入口（关键）
        req.resolve(admin, "SUSPEND_USER", reason);
    }

    /**
     * Reactivate a user that was previously suspended
     */
     public void reactivateUser(AccountStatusReviewRequest req,
                               UserAccount admin,
                               String reason) {

        UserAccount user = req.getTargetUser();

        // 1. 用户恢复 ACTIVE
        user.setStatus("ACTIVE");

        // 2. 更新工单状态
        req.setStatus("REACTIVATED");

        // ✅ 3. 统一审计入口（关键）
        req.resolve(admin, "REACTIVATE_USER", reason);
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
}
    
public void rejectUser(AccountStatusReviewRequest req, UserAccount admin, String reason) {

    UserAccount user = req.getTargetUser();

    user.setStatus("REJECTED");
    req.setStatus("REJECTED");

    // ✅ 关键：统一审计入口
    req.resolve(admin, "REJECT_USER", reason);
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

    // ✅ 统一可审计
    req.resolve(admin, "ISSUE_WARNING",
            "Total warnings = " + user.getWarningCount());
}

public void issueWarningByRequest(PolicyViolationRequest req,
                                  UserAccount admin) {

    UserAccount user = req.getTargetUser();

    // 1. 警告次数 +1
    user.incrementWarning();

    if (!"BANNED".equals(user.getStatus())) {
        user.setStatus("ACTIVE");
    }

    if (user.getWarningCount() >= 3) {
        user.setStatus("BANNED");
    }

    // ✅ 不在这里 resolve，由 PolicyEnforcementService 统一 resolve
}
}

