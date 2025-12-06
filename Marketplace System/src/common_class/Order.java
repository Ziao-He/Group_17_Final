/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package common_class;

import basement_class.BaseEntity;

/**
 *
 * @author bob-h
 */
public class Order extends BaseEntity {
    
     // Order identification
    private String orderId;
    
    // Related entities (references using IDs)
    private String listingId;          // Reference to Listing (NOT productId)
    private String buyerId;            // Reference to BuyerAccount
    private String sellerId;           // Reference to SellerAccount
    
    // Order details
    private double totalPrice;
    private int quantity;
    private String status;             // PENDING, ACCEPTED, REJECTED, COMPLETED, CANCELLED
    
    // Additional information
    private String deliveryMethod;     // How buyer will get the item
    private String meetingLocation;    // Where to meet for exchange
    
    // Order statistics
    private boolean isReviewed;        // Has buyer left a review
    private int buyerRating;           // Rating given by buyer (1-5)
    
    // Status constants
    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_ACCEPTED = "ACCEPTED";
    public static final String STATUS_REJECTED = "REJECTED";
    public static final String STATUS_COMPLETED = "COMPLETED";
    public static final String STATUS_CANCELLED = "CANCELLED";
    
    /**
     * Default constructor
     */
    public Order() {
        super();
        this.status = STATUS_PENDING;
        this.quantity = 1;
        this.isReviewed = false;
        this.buyerRating = 0;
    }
    
    /**
     * Constructor with essential fields
     */
    public Order(String orderId, String listingId, String buyerId, String sellerId, double totalPrice) {
        this();
        this.orderId = orderId;
        this.listingId = listingId;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.totalPrice = totalPrice;
    }
    
    /**
     * Check if order is pending
     */
    public boolean isPending() {
        return STATUS_PENDING.equals(this.status);
    }
    
    /**
     * Check if order is accepted
     */
    public boolean isAccepted() {
        return STATUS_ACCEPTED.equals(this.status);
    }
    
    /**
     * Check if order is completed
     */
    public boolean isCompleted() {
        return STATUS_COMPLETED.equals(this.status);
    }
    
    /**
     * Accept this order (seller action)
     */
    public void accept() {
        this.status = STATUS_ACCEPTED;
        this.touch();
    }
    
    /**
     * Reject this order (seller action)
     */
    public void reject() {
        this.status = STATUS_REJECTED;
        this.touch();
    }
    
    /**
     * Complete this order (transaction finished)
     */
    public void complete() {
        this.status = STATUS_COMPLETED;
        this.touch();
    }
    
    /**
     * Cancel this order (buyer or seller action)
     */
    public void cancel() {
        this.status = STATUS_CANCELLED;
        this.touch();
    }
    
    /**
     * Add buyer review and rating
     */
    public void addReview(int rating) {
        if (rating >= 1 && rating <= 5) {
            this.buyerRating = rating;
            this.isReviewed = true;
            this.touch();
        }
    }
    
    // Getters and Setters
    
    public String getOrderId() {
        return orderId;
    }
    
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    
    public String getListingId() {
        return listingId;
    }
    
    public void setListingId(String listingId) {
        this.listingId = listingId;
    }
    
    public String getBuyerId() {
        return buyerId;
    }
    
    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }
    
    public String getSellerId() {
        return sellerId;
    }
    
    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
    
    public double getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getDeliveryMethod() {
        return deliveryMethod;
    }
    
    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }
    
    public String getMeetingLocation() {
        return meetingLocation;
    }
    
    public void setMeetingLocation(String meetingLocation) {
        this.meetingLocation = meetingLocation;
    }
    
    public boolean isReviewed() {
        return isReviewed;
    }
    
    public void setReviewed(boolean reviewed) {
        isReviewed = reviewed;
    }
    
    public int getBuyerRating() {
        return buyerRating;
    }
    
    public void setBuyerRating(int buyerRating) {
        this.buyerRating = buyerRating;
    }
    
    @Override
    public String toString() {
        return String.format("Order{id='%s', listingId='%s', status='%s', price=%.2f}", 
            orderId, listingId, status, totalPrice);
    }
}
