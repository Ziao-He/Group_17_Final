/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_3.WorkRequest;

import basement_class.Enterprise_2.Listing;
import basement_class.UserAccount;
import basement_class.WorkRequest;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Linyiyang
 */
public class PolicyViolationRequest extends WorkRequest {

    private UserAccount reporter;        // 举报人
    private UserAccount targetUser;      // 被举报的用户
    private Listing listing;             // 如果是物品相关，可以为空
    private String violationCategory;    // account_issue / listing_issue / minor_dispute
    private String violationInfo;        // 具体描述
    private List<String> evidencePaths;  // 证据（截图路径等）
    private String rejectionReason;      // 审核拒绝理由

    /**
     * 账号问题 / 轻微纠纷等，不涉及 Listing
     */
    public PolicyViolationRequest(
            UserAccount reporter,
            UserAccount targetUser,
            String category,
            String info) {

        super();
        this.reporter = reporter;
        this.targetUser = targetUser;
        this.violationCategory = category;
        this.violationInfo = info;
        this.evidencePaths = new ArrayList<>();
        this.setStatus("Pending");
    }

    /**
     * 物品问题：涉及某个 Listing
     */
    public PolicyViolationRequest(
            UserAccount reporter,
            UserAccount targetUser,
            Listing listing,
            String category,
            String info) {

        this(reporter, targetUser, category, info);
        this.listing = listing;
    }

    public UserAccount getReporter() {
        return reporter;
    }

    public UserAccount getTargetUser() {
        return targetUser;
    }

    public Listing getListing() {
        return listing;
    }

    public String getViolationCategory() {
        return violationCategory;
    }

    public String getViolationInfo() {
        return violationInfo;
    }

    public List<String> getEvidencePaths() {
        return evidencePaths;
    }

    public void addEvidencePath(String path) {
        this.evidencePaths.add(path);
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public String getDisplayCategory() {
        return switch (violationCategory) {
            case "account_issue" -> "账号问题（封号）";
            case "listing_issue" -> "物品问题（Listing 下架 + 封号）";
            case "minor_dispute" -> "轻微纠纷（账号警告）";
            default -> violationCategory;
        };
    }

    @Override
    public String toString() {
        // 用于 JTable 第一列显示
        return "PolicyViolation #" + this.getId();
    }
}
