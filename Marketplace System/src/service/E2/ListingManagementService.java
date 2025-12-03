/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.E2;

import basement_class.EcoSystem;
import basement_class.Enterprise_2.Listing;
import basement_class.Enterprise_2.WorkRequest.ListingSubmissionRequest;
import basement_class.Organization;
import basement_class.UserAccount;
import java.util.ArrayList;
/**
 *
 * @author 心火牧神塞勒斯
 */
public class ListingManagementService {

    private final EcoSystem system;

    public ListingManagementService(EcoSystem system) {
        this.system = system;
    }

    /**
     * Create a listing and attach it to seller's account.
     */
    public Listing createListing(UserAccount seller,
                                 String title,
                                 String description,
                                 double price,
                                 ArrayList<String> imageUrls) {

        Listing listing = new Listing(seller.getUsername(), title, description, price, imageUrls);

        seller.getUserListingDirectory().addListing(listing);
        return listing;
    }

    /**
     * Submit listing for internal review → sends WorkRequest to Enterprise2 ListingManagementOrg.
     */
    public ListingSubmissionRequest submitListingForReview(UserAccount seller, Listing listing) {

        ListingSubmissionRequest request = new ListingSubmissionRequest(listing, seller);

        // Add to seller outbox
        seller.getWorkQueue().addWorkRequest(request);

        // Send to ListingManagementOrganization work queue
        Organization listingOrg = system.getOrganizationByName("Listing Management Organization");
        if (listingOrg != null) {
            listingOrg.getWorkQueue().addWorkRequest(request);
        }

        return request;
    }
}
