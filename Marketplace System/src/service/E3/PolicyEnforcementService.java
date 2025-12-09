/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.E3;

import basement_class.DAO.ListingFileDAO;
import basement_class.DAO.UserAccountFileDAO;
import basement_class.DAO.WorkRequestFileDAO;
import basement_class.EcoSystem;
import basement_class.Enterprise;
import basement_class.Enterprise_2.Listing;
import basement_class.Enterprise_2.ListingDirectory;
import basement_class.Enterprise_3.WorkRequest.ListingReviewRequest;
import basement_class.Enterprise_3.WorkRequest.PolicyViolationRequest;
import basement_class.Network;
import basement_class.Organization;
import basement_class.UserAccount;
import java.util.List;



/**
 *
 * @author Linyiyang
 */
public class PolicyEnforcementService {
    
     private UserManagementService userService = new UserManagementService();

    /**
     * 
     */
    public void approveViolation(EcoSystem system,
                                 PolicyViolationRequest req,
                                 UserAccount admin) {

        String category = req.getViolationCategory();
        UserAccount target = req.getTargetUser();

        switch (category) {

            case "account_issue" -> {

                // ✅ 1️⃣ 内存中封禁账号
                target.setStatus("BANNED");

                // ✅ 2️⃣ 内存中下架该账号的全部商品
                ListingDirectory listingDir = system.getListingDirectory(); 
                for (Listing l : listingDir.getAllListings()) {
                    if (l.getSellerId().equals(target.getUserId())) {
                        l.setStatus("REMOVED");
                    }
                }

                // ✅✅✅ 3️⃣ 直接把【全局内存用户表】存回 CSV（不要再 loadAll 了）
                UserAccountFileDAO userDAO = new UserAccountFileDAO();
                userDAO.saveAll(system.getUserAccountDirectory().getUserAccounts());

                // ✅✅✅ 4️⃣ 直接把【全局内存商品表】存回 CSV
                ListingFileDAO listingDAO = new ListingFileDAO();
                listingDAO.saveAll(listingDir.getAllListings());

                // ✅ 5️⃣ 更新请求状态
                req.setStatus("ACCOUNT_BANNED_AND_ALL_LISTINGS_REMOVED");

                req.resolve(
                    admin,
                    "ACCOUNT_BANNED + ALL_LISTINGS_REMOVED",
                    "Account violation confirmed, account banned and all listings removed"
                );

                // ✅ 6️⃣ WorkRequest 落盘
                new WorkRequestFileDAO().append(req);

                EcoSystem.getInstance()
                    .getWorkRequestDirectory()
                    .addWorkRequest(req);

                WorkRequestRouter.routeToEnterprise3(req);
            }

           case "listing_issue" -> {

    target.setStatus("BANNED");

    Listing listing = req.getListing();
    if (listing != null) {
        listing.setStatus("REMOVED");   // ✅ 真正下架
    }


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


    req.setStatus("LISTING_FORCE_DOWN_AND_BANNED");

    req.resolve(admin,
            "LISTING_FORCE_DOWN + ACCOUNT_BANNED",
            "Listing violation confirmed, forced down & account banned");

    new WorkRequestFileDAO().append(req);
    EcoSystem.getInstance()
        .getWorkRequestDirectory()
        .addWorkRequest(req);

    WorkRequestRouter.routeToEnterprise3(req);
}
            case "minor_dispute" -> {
          
                userService.issueWarningByRequest(req, admin);

                req.setStatus("WARNING_ISSUED");

        
                req.resolve(admin, "ISSUE_WARNING",
                        "Minor dispute, warning issued");
                new WorkRequestFileDAO().append(req);
                EcoSystem.getInstance()
                .getWorkRequestDirectory()
                .addWorkRequest(req);                       
                WorkRequestRouter.routeToEnterprise3(req); 
            }

            default -> {
                req.setStatus("UNKNOWN_CATEGORY");

         
                req.resolve(admin, "UNKNOWN_CATEGORY",
                        "System cannot identify violation type");
                new WorkRequestFileDAO().append(req);
                EcoSystem.getInstance()
                .getWorkRequestDirectory()
                .addWorkRequest(req);                       
                WorkRequestRouter.routeToEnterprise3(req); 
            }
        }
    }

    /**
     *
     */
    public void rejectViolation(PolicyViolationRequest req,
                                UserAccount admin,
                                String reason) {

        req.setRejectionReason(reason);
        req.setStatus("REJECTED");


        req.resolve(admin, "REJECT_VIOLATION", reason);
        new WorkRequestFileDAO().append(req);
        EcoSystem.getInstance()
        .getWorkRequestDirectory()
        .addWorkRequest(req);                       
        WorkRequestRouter.routeToEnterprise3(req); 
    }

    /**
     *
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