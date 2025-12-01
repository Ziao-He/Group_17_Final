/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.E3;

import basement_class.Enterprise_2.Listing;
import basement_class.Enterprise_3.WorkRequest.ListingReviewRequest;

/**
 *
 * @author Linyiyang
 * 需要成员2 的workrequest 来完成CLASS
 */
public class ContentModerationService {
    
       /** 审核通过 */
    public void approveListing(ListingReviewRequest request) {
        Listing listing = request.getListing();

        // 1. 更新 Listing 状态
        listing.setStatus("Approved");

        // 2. 更新 WorkRequest 状态
        request.setStatus("Approved");
        request.resolve();
    }

    /** 审核拒绝 */
    public void rejectListing(ListingReviewRequest request, String reason) {
        Listing listing = request.getListing();

        // 1. 更新 Listing 状态
        listing.setStatus("Rejected: " + reason);

        // 2. 更新 WorkRequest 状态
        request.setStatus("Rejected");
        request.resolve();
    }
}
