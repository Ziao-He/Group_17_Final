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
    private String action;                     // suspend, reactivate, ban
    private String requestDescription;         // 申请人填写：为什么要申请此操作
    private String reviewerDecisionReason;     // 审核者拒绝的理由

    public AccountStatusReviewRequest(UserAccount user, String action, String requestDescription) {
        super();
        this.targetUser = user;
        this.action = action;
        this.requestDescription = requestDescription;
        this.setStatus("PENDING");
    }

    public UserAccount getTargetUser() { 
        return targetUser; 
    }
    
    public String getAction() { 
        return action; 
    }

    // ========== 申请人填写的理由 ==========
    public String getRequestDescription() {
        return requestDescription;
    }

    public void setRequestDescription(String desc) {
        this.requestDescription = desc;
    }

    // ========== 审核者拒绝填写的理由 ==========
    public String getReviewerDecisionReason() {
        return reviewerDecisionReason;
    }

    public void setReviewerDecisionReason(String reason) {
        this.reviewerDecisionReason = reason;
    }


}