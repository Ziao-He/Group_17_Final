/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.E3;

import basement_class.EcoSystem;
import basement_class.Enterprise_3.WorkRequest.AccountStatusReviewRequest;
import basement_class.Enterprise_3.WorkRequest.RegistrationApprovalRequest;
import basement_class.UserAccount;

/**
 *
 * @author linyiyang
 */
public class UserManagementService {

         public void suspendUser(AccountStatusReviewRequest req) {

        UserAccount user = req.getTargetUser();

        // 1. 修改用户状态
        user.setStatus("SUSPENDED");

        // 2. 更新工单状态（工作流记录）
        req.setStatus("SUSPENDED");

        // 3. 关闭工单
        req.resolve();
    }

    /**
     * Reactivate a user that was previously suspended
     */
    public void reactivateUser(AccountStatusReviewRequest req) {

        UserAccount user = req.getTargetUser();

        // 1. 改用户回到正常状态
        user.setStatus("ACTIVE");

        // 2. 更新工单状态
        req.setStatus("REACTIVATED");

        // 3. 完成工单
        req.resolve();
    }

    /**
     * Permanently ban a user
     * @param req
     */
    public void banUser(AccountStatusReviewRequest req) {

        UserAccount user = req.getTargetUser();

        // 1. 修改用户状态
        user.setStatus("BANNED");

        // 2. 更新工单状态
        req.setStatus("BANNED");

        // 3. 完成工单
        req.resolve();
    }
    
    public void rejectUser(AccountStatusReviewRequest req) {
        UserAccount user = req.getTargetUser();

        user.setStatus("REJECTED");
        req.setStatus("REJECTED");
        req.resolve();
}

}
