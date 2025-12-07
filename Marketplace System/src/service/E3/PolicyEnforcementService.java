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
     * ✅ 审核通过（Approve）——带【审批人 + 审计记录】
     */
    public void approveViolation(EcoSystem system,
                                 PolicyViolationRequest req,
                                 UserAccount admin) {

        String category = req.getViolationCategory();
        UserAccount target = req.getTargetUser();

        switch (category) {

            case "account_issue" -> {
                // ✅ 账号问题 → 直接封号
                target.setStatus("BANNED");
                req.setStatus("ACCOUNT_BANNED");

                // ✅ 关键：记录是谁审批的
                req.resolve(admin, "ACCOUNT_BANNED",
                        "Account violation confirmed and banned");
            }

            case "listing_issue" -> {
                // ✅ 物品问题 → 封号 + 下发 Listing 审核

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

                    for (String path : req.getEvidencePaths()) {
                        listingReq.addEvidence(path);
                    }

                    sendToListingModeration(system, listingReq);
                }

                // ✅ 关键：记录是谁审批的
                req.resolve(admin, "LISTING_FORCE_DOWN + ACCOUNT_BANNED",
                        "Listing violation confirmed, forced down & account banned");
            }

            case "minor_dispute" -> {
                // ✅ 轻微纠纷 → 警告（现在也走审计）

                userService.issueWarningByRequest(req, admin);

                req.setStatus("WARNING_ISSUED");

                // ✅ 关键：记录是谁审批的
                req.resolve(admin, "ISSUE_WARNING",
                        "Minor dispute, warning issued");
            }

            default -> {
                req.setStatus("UNKNOWN_CATEGORY");

                // ✅ 关键：记录是谁审批的
                req.resolve(admin, "UNKNOWN_CATEGORY",
                        "System cannot identify violation type");
            }
        }
    }

    /**
     * ✅ 审核拒绝（Reject）——带【审批人 + 拒绝理由】
     */
    public void rejectViolation(PolicyViolationRequest req,
                                UserAccount admin,
                                String reason) {

        req.setRejectionReason(reason);
        req.setStatus("REJECTED");

        // ✅ 关键：记录是谁拒绝的 + 为什么拒绝
        req.resolve(admin, "REJECT_VIOLATION", reason);
    }

    /**
     * ✅ 把 Listing 审核请求塞到 负责 Listing 的组织
     */
    private void sendToListingModeration(EcoSystem system,
                                         ListingReviewRequest req) {

        for (Network n : system.getNetworks()) {
            for (Enterprise e : n.getEnterprises()) {
                for (Organization org : e.getOrganizations()) {
                    if ("Listing Moderation".equals(org.getName())) {
                        org.getWorkRequestDirectory()
                           .getRequestList()
                           .add(req);
                        return;
                    }
                }
            }
        }

        System.out.println("[PolicyEnforcementService] WARNING: Listing Moderation organization not found.");
    }
}