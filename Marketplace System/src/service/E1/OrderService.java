/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.E1;

import common_class.Order;
import common_class.Product;
import basement_class.DAO.OrderDao;
import basement_class.DAO.ProductDao;
import basement_class.Enterprise_2.Listing;
import basement_class.Enterprise_2.ListingDirectory;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bob-h
 */
public class OrderService {
    
    private OrderDao orderDao;
    private ListingDirectory listingDirectory;
    
    /**
     * Constructor
     */
    public OrderService(ListingDirectory listingDirectory) {
        this.orderDao = new OrderDao();
        this.listingDirectory = listingDirectory;
    }
    
    // ==================== CREATE OPERATIONS ====================
    
    /**
     * Create a new order (buyer submits purchase request)
     * 
     * @param orderId Unique order ID
     * @param listingId Listing to purchase
     * @param buyerId Buyer user ID
     * @param sellerId Seller user ID
     * @return Created order, or null if failed
     */
    public Order createOrder(String orderId, String listingId, String buyerId, String sellerId) {
        // Validate listing exists and is approved
        Listing listing = listingDirectory.findById(listingId);
        if (listing == null) {
            throw new IllegalArgumentException("Listing not found: " + listingId);
        }
        
        if (!"Approved".equals(listing.getStatus())) {
            throw new IllegalStateException("Listing is not approved for purchase");
        }
        
        // Create order
        Order order = new Order(orderId, listingId, buyerId, sellerId, listing.getPrice());
        order.setStatus(Order.STATUS_PENDING);
        order.setQuantity(1);
        
        // Save order
        boolean saved = orderDao.save(order);
        if (!saved) {
            return null;
        }
        
        // Update listing status to Reserved
        listing.setStatus("Reserved");
        listingDirectory.updateListing(listing);
        
        return order;
    }
    
    // ==================== UPDATE OPERATIONS ====================
    
    /**
     * Accept an order (seller action)
     */
    public boolean acceptOrder(String orderId, String sellerId) {
        Order order = orderDao.findById(orderId);
        
        if (order == null) {
            return false;
        }
        
        // Verify seller authorization
        if (!order.getSellerId().equals(sellerId)) {
            return false;
        }
        
        // Can only accept pending orders
        if (!Order.STATUS_PENDING.equals(order.getStatus())) {
            return false;
        }
        
        // Accept order
        order.accept();
        return orderDao.update(order);
    }
    
    /**
     * Reject an order (seller action)
     */
    public boolean rejectOrder(String orderId, String sellerId) {
        Order order = orderDao.findById(orderId);
        
        if (order == null) {
            return false;
        }
        
        // Verify seller authorization
        if (!order.getSellerId().equals(sellerId)) {
            return false;
        }
        
        // Can only reject pending orders
        if (!Order.STATUS_PENDING.equals(order.getStatus())) {
            return false;
        }
        
        // Reject order
        order.reject();
        orderDao.update(order);
        
        // Restore listing to Approved status
        Listing listing = listingDirectory.findById(order.getListingId());
        if (listing != null) {
            listing.setStatus("Approved");
            listingDirectory.updateListing(listing);
        }
        
        return true;
    }
    
    /**
     * Complete an order (seller marks as completed)
     */
    public boolean completeOrder(String orderId, String sellerId) {
        Order order = orderDao.findById(orderId);
        
        if (order == null) {
            return false;
        }
        
        // Verify seller authorization
        if (!order.getSellerId().equals(sellerId)) {
            return false;
        }
        
        // Can only complete accepted orders
        if (!Order.STATUS_ACCEPTED.equals(order.getStatus())) {
            return false;
        }
        
        // Complete order
        order.complete();
        orderDao.update(order);
        
        // Update listing to Sold status
        Listing listing = listingDirectory.findById(order.getListingId());
        if (listing != null) {
            listing.setStatus("Sold");
            listingDirectory.updateListing(listing);
        }
        
        return true;
    }
    
    /**
     * Cancel an order (buyer or seller action)
     */
    public boolean cancelOrder(String orderId, String userId) {
        Order order = orderDao.findById(orderId);
        
        if (order == null) {
            return false;
        }
        
        // Verify authorization
        if (!order.getBuyerId().equals(userId) && !order.getSellerId().equals(userId)) {
            return false;
        }
        
        // Can only cancel pending or accepted orders
        if (!Order.STATUS_PENDING.equals(order.getStatus()) && 
            !Order.STATUS_ACCEPTED.equals(order.getStatus())) {
            return false;
        }
        
        // Cancel order
        order.cancel();
        orderDao.update(order);
        
        // Restore listing to Approved status
        Listing listing = listingDirectory.findById(order.getListingId());
        if (listing != null) {
            listing.setStatus("Approved");
            listingDirectory.updateListing(listing);
        }
        
        return true;
    }
    
    /**
     * Add review to completed order
     */
    public boolean addReview(String orderId, String buyerId, int rating) {
        Order order = orderDao.findById(orderId);
        
        if (order == null || !order.getBuyerId().equals(buyerId)) {
            return false;
        }
        
        if (!Order.STATUS_COMPLETED.equals(order.getStatus())) {
            return false;
        }
        
        if (rating < 1 || rating > 5) {
            return false;
        }
        
        order.addReview(rating);
        return orderDao.update(order);
    }
    
    // ==================== QUERY OPERATIONS ====================
    
    /**
     * Get order by ID
     */
    public Order getOrderById(String orderId) {
        return orderDao.findById(orderId);
    }
    
    /**
     * Get all orders for a buyer
     */
    public List<Order> getBuyerOrders(String buyerId) {
        return orderDao.findByBuyerId(buyerId);
    }
    
    /**
     * Get buyer's pending orders
     */
    public List<Order> getBuyerPendingOrders(String buyerId) {
        List<Order> allOrders = orderDao.findByBuyerId(buyerId);
        return filterByStatus(allOrders, Order.STATUS_PENDING);
    }
    
    /**
     * Get buyer's accepted orders
     */
    public List<Order> getBuyerAcceptedOrders(String buyerId) {
        List<Order> allOrders = orderDao.findByBuyerId(buyerId);
        return filterByStatus(allOrders, Order.STATUS_ACCEPTED);
    }
    
    /**
     * Get buyer's completed orders
     */
    public List<Order> getBuyerCompletedOrders(String buyerId) {
        List<Order> allOrders = orderDao.findByBuyerId(buyerId);
        return filterByStatus(allOrders, Order.STATUS_COMPLETED);
    }
    
    /**
     * Get seller's pending orders
     */
    public List<Order> getSellerPendingOrders(String sellerId) {
        return orderDao.getPendingOrdersForSeller(sellerId);
    }
    
    /**
     * Get all orders for a listing
     */
    public List<Order> getListingOrders(String listingId) {
        List<Order> allOrders = orderDao.getAllOrders();
        List<Order> result = new ArrayList<>();
        for (Order order : allOrders) {
            if (order.getListingId().equals(listingId)) {
                result.add(order);
            }
        }
        return result;
    }
    
    // ==================== STATISTICS ====================
    
    /**
     * Get buyer's total spending
     */
    public double getBuyerTotalSpending(String buyerId) {
        List<Order> completedOrders = getBuyerCompletedOrders(buyerId);
        double total = 0.0;
        for (Order order : completedOrders) {
            total += order.getTotalPrice();
        }
        return total;
    }
    
    /**
     * Get buyer's order count
     */
    public int getBuyerOrderCount(String buyerId) {
        return orderDao.findByBuyerId(buyerId).size();
    }
    
    /**
     * Get buyer's completed order count
     */
    public int getBuyerCompletedOrderCount(String buyerId) {
        return getBuyerCompletedOrders(buyerId).size();
    }
    
    // ==================== VALIDATION ====================
    
    /**
     * Check if buyer can create order for listing
     */
    public boolean canCreateOrder(String buyerId, String listingId) {
        // Check if listing exists
        Listing listing = listingDirectory.findById(listingId);
        if (listing == null) {
            return false;
        }
        
        // Check if listing is approved
        if (!"Approved".equals(listing.getStatus())) {
            return false;
        }
        
        // Check if buyer is not the seller
        if (listing.getSellerId().equals(buyerId)) {
            return false;
        }
        
        // Check if buyer already has pending order for this listing
        List<Order> buyerOrders = orderDao.findByBuyerId(buyerId);
        for (Order order : buyerOrders) {
            if (order.getListingId().equals(listingId) && 
                Order.STATUS_PENDING.equals(order.getStatus())) {
                return false;
            }
        }
        
        return true;
    }
    
    // ==================== HELPER METHODS ====================
    
    /**
     * Filter orders by status
     */
    private List<Order> filterByStatus(List<Order> orders, String status) {
        List<Order> result = new ArrayList<>();
        for (Order order : orders) {
            if (status.equals(order.getStatus())) {
                result.add(order);
            }
        }
        return result;
    }
}