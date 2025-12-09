/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.E2;

import basement_class.DAO.ListingHelperFunction;
import basement_class.EcoSystem;
import basement_class.Enterprise;
import basement_class.Network;
import basement_class.Organization;
import basement_class.UserAccount;
import basement_class.Enterprise_2.Listing;
import basement_class.Enterprise_2.WorkRequest.ListingSubmissionRequest;
import basement_class.Enterprise_2.Enterprise.MarketplaceEnterprise;
import basement_class.Enterprise_2.ListingDirectory;
import java.util.ArrayList;

/**
 *
 * @author 心火牧神塞勒斯
 */
public class ListingManagementService {

    private EcoSystem system;
    private ListingHelperFunction listingDao;        
    private ListingDirectory listingDirectory;  

    public ListingManagementService(EcoSystem system, ListingHelperFunction listingDao, ListingDirectory directory) {
        this.system = system;
        this.listingDao = listingDao;
        this.listingDirectory = directory;
    }

    /**
     * Create a new listing with full information
     */
    public Listing createListing(
            String listingId,
            UserAccount seller,
            String title,
            String description,
            String imagePath,
            double price
    ) {
        Listing listing = new Listing(listingId, seller, title, description, imagePath, price);

        // Save to directory
        listingDirectory.addListing(listing);

        // Save to CSV
        listingDao.save(listing);

        return listing;
    }


    /**
     * Submit listing → send WorkRequest to ListingManagementOrganization
     */
    public ListingSubmissionRequest submitListing(UserAccount seller, Listing listing) {

        ListingSubmissionRequest req = new ListingSubmissionRequest(listing, seller);
        req.setSender(seller);

        // Find Listing Management Organization in MarketplaceEnterprise
        Organization listingOrg = findListingManagementOrg();

        if (listingOrg != null) {
            listingOrg.getWorkRequestDirectory().addWorkRequest(req);
        }

        return req;
    }

    /**
     * Find the ListingManagementOrganization (inside MarketplaceEnterprise ONLY)
     */
    private Organization findListingManagementOrg() {

        for (Network n : system.getNetworks()) {
            for (Enterprise e : n.getEnterprises()) {

                // Strict match MarketplaceEnterprise 
                if (e instanceof MarketplaceEnterprise) {
                    Organization o = e.getOrganizationByName("Listing Management");
                    if (o != null)
                        return o;
                }
            }
        }
        return null;
    }

    public ArrayList<Listing> getAllListings() {
        return listingDirectory.getAllListings();
    }
}
