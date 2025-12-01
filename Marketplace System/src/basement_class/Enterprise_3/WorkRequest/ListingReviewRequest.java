/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_3.WorkRequest;

import basement_class.Enterprise_2.Listing;
import basement_class.WorkRequest;

/**
 *
 * @author Linyiyang
 */
public class ListingReviewRequest extends WorkRequest{
    private Listing listing;

    public ListingReviewRequest(Listing listing) {
        super();
        this.listing = listing;
        this.setStatus("Pending");
    }

    public Listing getListing() { 
        return listing; 
    }    
}
