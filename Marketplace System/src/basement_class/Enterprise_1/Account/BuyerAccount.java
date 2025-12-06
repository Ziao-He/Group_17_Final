/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_1.Account;

import basement_class.UserAccount;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author bob-h
 */
public class BuyerAccount extends UserAccount {
    
    // Link to profile
    private BuyerProfile profile;
    
    // Transaction/Business data
    private int totalPurchases;
    private int completedPurchases;
    private int points;
    
    // References
    private List<String> favoriteProductIds;
    private List<String> orderIds;
    
    /**
     * Constructor
     */
    public BuyerAccount() {
        super();
        this.profile = new BuyerProfile();
        this.totalPurchases = 0;
        this.completedPurchases = 0;
        this.points = 0;
        this.favoriteProductIds = new ArrayList<>();
        this.orderIds = new ArrayList<>();
    }
    
    /**
     * Add product to favorites
     */
    public void addToFavorites(String productId) {
        if (!favoriteProductIds.contains(productId)) {
            favoriteProductIds.add(productId);
        }
    }
    
    /**
     * Remove from favorites
     */
    public void removeFromFavorites(String productId) {
        favoriteProductIds.remove(productId);
    }
    
    /**
     * Add order
     */
    public void addOrder(String orderId) {
        if (!orderIds.contains(orderId)) {
            orderIds.add(orderId);
            totalPurchases++;
        }
    }
    
    /**
     * Increment completed purchases
     */
    public void incrementCompletedPurchases() {
        this.completedPurchases++;
    }
    
    /**
     * Add reward points
     */
    public void addPoints(int points) {
        this.points += points;
    }
    
    // Getters and Setters
    
    public BuyerProfile getProfile() {
        return profile;
    }
    
    public void setProfile(BuyerProfile profile) {
        this.profile = profile;
    }
    
    public int getTotalPurchases() {
        return totalPurchases;
    }
    
    public void setTotalPurchases(int totalPurchases) {
        this.totalPurchases = totalPurchases;
    }
    
    public int getCompletedPurchases() {
        return completedPurchases;
    }
    
    public void setCompletedPurchases(int completedPurchases) {
        this.completedPurchases = completedPurchases;
    }
    
    public int getPoints() {
        return points;
    }
    
    public void setPoints(int points) {
        this.points = points;
    }
    
    public List<String> getFavoriteProductIds() {
        return favoriteProductIds;
    }
    
    public void setFavoriteProductIds(List<String> favoriteProductIds) {
        this.favoriteProductIds = favoriteProductIds;
    }
    
    public List<String> getOrderIds() {
        return orderIds;
    }
    
    public void setOrderIds(List<String> orderIds) {
        this.orderIds = orderIds;
    }
}