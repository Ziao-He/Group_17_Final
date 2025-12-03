/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.E2;

import basement_class.EcoSystem;
import basement_class.Enterprise;
import basement_class.Enterprise_2.Listing;
import basement_class.Enterprise_2.WorkRequest.ListingSubmissionRequest;
import basement_class.Network;
import basement_class.Organization;
import basement_class.UserAccount;
import java.util.ArrayList;
/**
 *
 * @author 心火牧神塞勒斯
 */
public class ListingManagementService {

    private EcoSystem system;
    private ArrayList<Listing> allListings;   // local storage for listings

    public ListingManagementService(EcoSystem system) {
        this.system = system;
        this.allListings = new ArrayList<>();
    }

    /**
     * Create a new listing (using existing 3-field constructor).
     * @param listingId
     * @param seller
     * @param title
     * @return 
     */
    public Listing createListing(String listingId, UserAccount seller, String title) {

        Listing listing = new Listing(listingId, seller.getUserId(), title);

        allListings.add(listing);  // We store listing internally
        return listing;
    }

    /**
     * Submit listing → send to ListingManagementOrganization
     * @param seller
     * @param listing
     * @return 
     */
    public ListingSubmissionRequest submitListing(UserAccount seller, Listing listing) {

        ListingSubmissionRequest req = new ListingSubmissionRequest(listing, seller);
        req.setSender(seller);

        // Find Enterprise2 ListingManagementOrganization
        Organization listingOrg = findListingManagementOrg();
        if (listingOrg != null) {
            listingOrg.getWorkRequestDirectory().addWorkRequest(req);
        }

        return req;
    }

    /**
     * Searches through the ecosystem to find Enterprise2's "Listing Management" organization.
     */
    private Organization findListingManagementOrg() {

        for (Network n : system.getNetworks()) {
            for (Enterprise e : n.getEnterprises()) {
                // You will later replace this with: e instanceof MarketplaceEnterprise
                Organization o = e.getOrganizationByName("Listing Management");
                if (o != null) return o;
            }
        }
        return null;
    }

    public ArrayList<Listing> getAllListings() {
        return allListings;
    }
}
