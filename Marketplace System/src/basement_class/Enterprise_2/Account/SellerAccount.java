/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_2.Account;

import basement_class.UserAccount;
import common_class.Product;
import java.util.ArrayList;
/**
 *
 * @author 心火牧神塞勒斯
 */
public class SellerAccount extends UserAccount {

    private ArrayList<Product> products;       // Seller's product list
    private ArrayList<String> listingIds;      // Listings created by this Seller

    public SellerAccount() {
        super();
        this.products = new ArrayList<>();
        this.listingIds = new ArrayList<>();
    }

    // Product management
    public void addProduct(Product product) {
        if (!products.contains(product)) {
            products.add(product);
        }
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    // Listing management
    public void addListingId(String listingId) {
        if (!listingIds.contains(listingId)) {
            listingIds.add(listingId);
        }
    }

    public ArrayList<String> getListingIds() {
        return listingIds;
    }
}
