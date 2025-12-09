/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.DAO;

import basement_class.Enterprise_2.Listing;
import basement_class.Enterprise_2.ListingDirectory;
import java.util.List;

/**
 *
 * @author Linyiyang
 */
public class ListingService {
     private final ListingDAO dao;
    private final ListingDirectory directory;

    public ListingService(ListingDAO dao, ListingDirectory directory) {
        this.dao = dao;
        this.directory = directory;
    }

    public void loadListings() {
        List<Listing> list = dao.loadAll();
        for (Listing l : list) {
            directory.addListing(l);
        }
    }

    public void saveListings() {
        dao.saveAll(directory.getAllListings());
    }
}
