/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.E3;

import basement_class.EcoSystem;
import basement_class.Enterprise;
import basement_class.Enterprise_2.Listing;
import basement_class.Enterprise_3.WorkRequest.ListingReviewRequest;
import basement_class.Enterprise_3.WorkRequest.PolicyViolationRequest;
import basement_class.Network;
import basement_class.Organization;
import basement_class.UserAccount;



/**
 *
 * @author Linyiyang
 */
public class PolicyEnforcementService {
    
 private UserManagementService userService = new UserManagementService();

    /**
     * 审核通过（Approve）
     * @param system 整个 EcoSystem，用来找到 Listing 审核组织
     * @param req    违规工单
     */
    public void approveViolation(EcoSystem system, PolicyViolationRequest req) {

        String category = req.getViolationCategory();
        UserAccount target = req.getTargetUser();

        switch (category) {

            case "account_issue" -> {
                // 账号问题 → 直接封号
                target.setStatus("BANNED");
                req.setStatus("ACCOUNT_BANNED");
                req.resolve();
            }

            case "listing_issue" -> {
                // 物品问题 → 封号 + 发 ListingReviewRequest 到卖家侧进行物品下架审核

                target.setStatus("BANNED");
                req.setStatus("LISTING_ISSUE_SUBMITTED");

                Listing listing = req.getListing();
                if (listing != null) {
                    ListingReviewRequest listingReq =
                            new ListingReviewRequest(
                                    listing,
                                    "policy_force_down",
                                    req.getViolationInfo()
                            );

                    // 把证据也附加到 ListingReviewRequest 里（如果你在那边有 evidence 字段）
                    for (String path : req.getEvidencePaths()) {
                        listingReq.addEvidence(path);
                    }

                    sendToListingModeration(system, listingReq);
                }

                req.resolve();
            }

            case "minor_dispute" -> {
                // 轻微纠纷 → 警告账号
                userService.issueWarning(target);
                req.setStatus("WARNING_ISSUED");
                req.resolve();
            }

            default -> {
                req.setStatus("UNKNOWN_CATEGORY");
                req.resolve();
            }
        }
    }

    /**
     * 审核拒绝（Reject）——什么都不做，只记录理由
     */
    public void rejectViolation(PolicyViolationRequest req, String reason) {
        req.setRejectionReason(reason);
        req.setStatus("REJECTED");
        req.resolve();
    }

    /**
     * 把 Listing 审核请求塞到 负责 Listing 的组织工作队列中
     * 这里用了一个“按名称查找组织”的通用写法，你可以根据自己的 Enterprise 命名再微调
     */
    private void sendToListingModeration(EcoSystem system, ListingReviewRequest req) {

        for (Network n : system.getNetworks()) {
            for (Enterprise e : n.getEnterprises()) {
                for (Organization org : e.getOrganizations()) {
                    if ("Listing Moderation".equals(org.getName())) {
                        org.getWorkRequestDirectory().getRequestList().add(req);
                        return;
                    }
                }
            }
        }

        // 如果没找到，可以打印一下日志，方便 debug
        System.out.println("[PolicyEnforcementService] WARNING: Listing Moderation organization not found.");
    }   

}
