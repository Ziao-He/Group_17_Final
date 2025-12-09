/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_2;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author 心火牧神塞勒斯
 */
public class ListingDirectory {

    private ArrayList<Listing> listingList;

    public ListingDirectory() {
        this.listingList = new ArrayList<>();
    }

    // Add new listing
    public void addListing(Listing listing) {
        if (listing != null && !listingList.contains(listing)) {
            listingList.add(listing);
        }
    }

    // Remove listing
    public boolean removeListing(Listing listing) {
        return listingList.remove(listing);
    }

    // Find listing by ID
    public Listing findById(String id) {
        for (Listing listing : listingList) {
            if (listing.getId().equals(id)) {
                return listing;
            }
        }
        return null;
    }

    // Get all listings
    public ArrayList<Listing> getListingList() {
        return listingList;
    }

    // Set all listings
    public void setListingList(ArrayList<Listing> listingList) {
        this.listingList = listingList;
    }

    // Find listings by seller ID
    public List<Listing> findBySellerId(String sellerId) {
        List<Listing> result = new ArrayList<>();
        for (Listing listing : listingList) {
            if (listing.getSellerId().equals(sellerId)) {
                result.add(listing);
            }
        }
        return result;
    }

    // Find listings by status (Pending / Approved / Rejected)
    public List<Listing> findByStatus(String status) {
        List<Listing> result = new ArrayList<>();
        for (Listing listing : listingList) {
            if (listing.getStatus().equalsIgnoreCase(status)) {
                result.add(listing);
            }
        }
        return result;
    }

    // Update listing (replace by ID)
    public boolean updateListing(Listing updatedListing) {
        for (int i = 0; i < listingList.size(); i++) {
            if (listingList.get(i).getId().equals(updatedListing.getId())) {
                listingList.set(i, updatedListing);
                return true;
            }
        }
        return false;
    }

    // Size
    public int size() {
        return listingList.size();
    }

    public ArrayList<Listing> getAllListings() {
        return new ArrayList<>(listingList);   // 返回副本更安全
    }
}