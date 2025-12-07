/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.E3;

import basement_class.Enterprise_2.Listing;
import basement_class.Enterprise_3.WorkRequest.ListingReviewRequest;
import basement_class.UserAccount;

/**
 *
 * @author Linyiyang
 * 需要成员2 的workrequest 来完成CLASS
 */
public class ContentModerationService {
    
       /** 审核通过 */
        public void approveListing(ListingReviewRequest req,  UserAccount admin) {
            req.getListing().setStatus("Approved");
            req.setStatus("Approved");
            req.resolve(admin, "APPROVE_LISTING", "Listing approved");
}

        public void rejectListing(ListingReviewRequest req, UserAccount admin, String reason) {
            req.getListing().setStatus("Rejected");
            req.setStatus("Rejected");
            req.resolve(admin, "REJECT_LISTING", reason);
}
}
