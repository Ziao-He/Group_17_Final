/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_2.WorkRequest;

import basement_class.UserAccount;
import basement_class.WorkRequest;
import basement_class.Enterprise_2.Listing;
/**
 *
 * @author 心火牧神塞勒斯
 */
public class ListingSubmissionRequest extends WorkRequest {

    private Listing listing;         // The listing submitted by seller
    private UserAccount seller;      // Seller who created the listing

    private String managerComment;   // Optional: internal review notes
    
    public ListingSubmissionRequest(Listing listing, UserAccount seller) {
        super();
        this.listing = listing;
        this.seller = seller;
        this.setStatus("Pending");
        this.managerComment = "";
    }
    
    public Listing getListing() {
        return listing;
    }

    public UserAccount getSeller() {
        return seller;
    }

    public String getManagerComment() {
        return managerComment;
    }

    public void setManagerComment(String managerComment) {
        this.managerComment = managerComment;
    }

    @Override
    public String toString() {
        return "ListingSubmissionRequest{" + 
               "listingTitle=" + listing.getTitle() + 
               ", seller=" + seller.getUsername() + 
               ", status=" + this.getStatus() +
               '}';
    }
}
