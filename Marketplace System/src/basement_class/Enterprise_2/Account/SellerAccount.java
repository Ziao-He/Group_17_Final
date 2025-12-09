/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_2.Account;

import basement_class.Enterprise_2.Listing;
import basement_class.UserAccount;
import common_class.Product;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author 心火牧神塞勒斯
 */
public class SellerAccount extends UserAccount {

    
    private final List<Listing> listings;  

    public SellerAccount() {
        super();
        this.listings = new ArrayList<>();
    }

    
    public void addListing(Listing listing) {
        if (listing != null && !listings.contains(listing)) {
            listings.add(listing);
        }
    }

    
    public boolean removeListing(Listing listing) {
        return listings.remove(listing);
    }

    
    public List<Listing> getListings() {
        return new ArrayList<>(listings);  
    }

    
    public Listing findListingById(String listingId) {
        for (Listing listing : listings) {
            if (listing.getId().equals(listingId)) {
                return listing;
            }
        }
        return null;
    }
}
