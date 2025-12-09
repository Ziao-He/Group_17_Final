/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.E2;

import basement_class.DAO.ListingHelperFunction;
import basement_class.DAO.OrderHelperFuction;
import basement_class.EcoSystem;
import basement_class.Enterprise;
import basement_class.Network;
import basement_class.Organization;
import basement_class.UserAccount;
import basement_class.Enterprise_2.Enterprise.MarketplaceEnterprise;
import basement_class.Enterprise_2.Organization.SellerOrganization;
import common_class.Order;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author 心火牧神塞勒斯
 */
public class OrderFulfillmentService {

    private EcoSystem system;
    private OrderHelperFuction orderDao;
    private ListingHelperFunction listingDao;
    
    public OrderFulfillmentService(EcoSystem system, OrderHelperFuction orderDao, ListingHelperFunction listingDao) {
        this.system = system;
        this.orderDao = orderDao;
        this.listingDao = listingDao;
    }
    
    /**
     * Get all pending orders for a specific seller
     * @param sellerId The seller's user ID
     * @return List of pending orders
     */
    public List<Order> getPendingOrdersForSeller(String sellerId) {
        List<Order> sellerOrders = orderDao.findBySellerId(sellerId);
        List<Order> pendingOrders = new ArrayList<>();
        
        for (Order order : sellerOrders) {
            if (order.isPending()) {
                pendingOrders.add(order);
            }
        }
        
        return pendingOrders;
    }
    
    /**
     * Get all orders for a specific seller (all statuses)
     * @param sellerId The seller's user ID
     * @return List of all orders for this seller
     */
    public List<Order> getAllOrdersForSeller(String sellerId) {
        return orderDao.findBySellerId(sellerId);
    }
    
    /**
     * Get orders for a seller filtered by status
     * @param sellerId The seller's user ID
     * @param status Order status to filter by
     * @return List of filtered orders
     */
    public List<Order> getOrdersForSellerByStatus(String sellerId, String status) {
        List<Order> sellerOrders = orderDao.findBySellerId(sellerId);
        List<Order> filteredOrders = new ArrayList<>();
        
        for (Order order : sellerOrders) {
            if (order.getStatus().equals(status)) {
                filteredOrders.add(order);
            }
        }
        
        return filteredOrders;
    }
    
    /**
     * Accept an order (seller action)
     * @param orderId The order ID to accept
     * @param seller The seller performing the action (for validation)
     * @param deliveryMethod Optional delivery method
     * @param meetingLocation Optional meeting location
     * @return true if successful, false if failed
     */
    public boolean acceptOrder(String orderId, UserAccount seller, 
                               String deliveryMethod, String meetingLocation) {
        
        Order order = orderDao.findById(orderId);
        
        // Validate: order exists, belongs to this seller, and is pending
        if (order == null) {
            System.err.println("Order not found: " + orderId);
            return false;
        }
        
        if (!order.getSellerId().equals(seller.getUserId())) {
            System.err.println("Order does not belong to seller: " + seller.getUserId());
            return false;
        }
        
        if (!order.isPending()) {
            System.err.println("Order is not in PENDING status: " + order.getStatus());
            return false;
        }
        
        // Update order
        order.accept();
        
        // Set delivery details if provided
        if (deliveryMethod != null && !deliveryMethod.trim().isEmpty()) {
            order.setDeliveryMethod(deliveryMethod);
        }
        
        if (meetingLocation != null && !meetingLocation.trim().isEmpty()) {
            order.setMeetingLocation(meetingLocation);
        }
        
        // Update associated listing status to "Reserved"
        updateListingStatus(order.getListingId(), "Reserved");
        
        // Save changes
        boolean success = orderDao.update(order);
        
        if (success) {
            System.out.println("Order accepted: " + orderId + " by seller: " + seller.getUserId());
            // TODO: Send notification to buyer (if notification system exists)
        }
        
        return success;
    }
    
    /**
     * Reject an order (seller action)
     * @param orderId The order ID to reject
     * @param seller The seller performing the action
     * @param reason Optional reason for rejection
     * @return true if successful, false if failed
     */
    public boolean rejectOrder(String orderId, UserAccount seller, String reason) {
        Order order = orderDao.findById(orderId);
        
        // Validate: order exists, belongs to this seller, and is pending
        if (order == null) {
            System.err.println("Order not found: " + orderId);
            return false;
        }
        
        if (!order.getSellerId().equals(seller.getUserId())) {
            System.err.println("Order does not belong to seller: " + seller.getUserId());
            return false;
        }
        
        if (!order.isPending()) {
            System.err.println("Order is not in PENDING status: " + order.getStatus());
            return false;
        }
        
        // Update order
        order.reject();
        
        // Save changes
        boolean success = orderDao.update(order);
        
        if (success) {
            System.out.println("Order rejected: " + orderId + " by seller: " + seller.getUserId());
            if (reason != null && !reason.trim().isEmpty()) {
                System.out.println("Rejection reason: " + reason);
                // TODO: Store rejection reason in order or separate table
            }
            // TODO: Send notification to buyer
        }
        
        return success;
    }
    
    /**
     * Mark an order as completed (seller action)
     * @param orderId The order ID to complete
     * @param seller The seller performing the action
     * @return true if successful, false if failed
     */
    public boolean completeOrder(String orderId, UserAccount seller) {
        Order order = orderDao.findById(orderId);
        
        // Validate: order exists, belongs to this seller, and is accepted
        if (order == null) {
            System.err.println("Order not found: " + orderId);
            return false;
        }
        
        if (!order.getSellerId().equals(seller.getUserId())) {
            System.err.println("Order does not belong to seller: " + seller.getUserId());
            return false;
        }
        
        if (!order.isAccepted()) {
            System.err.println("Order must be ACCEPTED before completing: " + order.getStatus());
            return false;
        }
        
        // Update order
        order.complete();
        
        // Update associated listing status to "Sold"
        updateListingStatus(order.getListingId(), "Sold");
        
        // Save changes
        boolean success = orderDao.update(order);
        
        if (success) {
            System.out.println("Order completed: " + orderId + " by seller: " + seller.getUserId());
            // TODO: Send notification to buyer
        }
        
        return success;
    }
    
    /**
     * Cancel an order (seller can cancel accepted orders)
     * @param orderId The order ID to cancel
     * @param seller The seller performing the action
     * @param reason Optional reason for cancellation
     * @return true if successful, false if failed
     */
    public boolean cancelOrder(String orderId, UserAccount seller, String reason) {
        Order order = orderDao.findById(orderId);
        
        // Validate: order exists and belongs to this seller
        if (order == null) {
            System.err.println("Order not found: " + orderId);
            return false;
        }
        
        if (!order.getSellerId().equals(seller.getUserId())) {
            System.err.println("Order does not belong to seller: " + seller.getUserId());
            return false;
        }
        
        // Can cancel pending or accepted orders
        if (!order.isPending() && !order.isAccepted()) {
            System.err.println("Order cannot be cancelled in current status: " + order.getStatus());
            return false;
        }
        
        // Update order
        order.cancel();
        
        // Update associated listing status back to "Approved" (available again)
        updateListingStatus(order.getListingId(), "Approved");
        
        // Save changes
        boolean success = orderDao.update(order);
        
        if (success) {
            System.out.println("Order cancelled: " + orderId + " by seller: " + seller.getUserId());
            // TODO: Send notification to buyer
        }
        
        return success;
    }
    
    /**
     * Batch accept multiple orders
     * @param orderIds List of order IDs to accept
     * @param seller The seller performing the action
     * @return Number of successfully accepted orders
     */
    public int batchAcceptOrders(List<String> orderIds, UserAccount seller) {
        int successCount = 0;
        
        for (String orderId : orderIds) {
            if (acceptOrder(orderId, seller, null, null)) {
                successCount++;
            }
        }
        
        System.out.println("Batch accepted " + successCount + "/" + orderIds.size() + " orders");
        return successCount;
    }
    
    /**
     * Batch reject multiple orders
     * @param orderIds List of order IDs to reject
     * @param seller The seller performing the action
     * @param reason Reason for rejection (applied to all)
     * @return Number of successfully rejected orders
     */
    public int batchRejectOrders(List<String> orderIds, UserAccount seller, String reason) {
        int successCount = 0;
        
        for (String orderId : orderIds) {
            if (rejectOrder(orderId, seller, reason)) {
                successCount++;
            }
        }
        
        System.out.println("Batch rejected " + successCount + "/" + orderIds.size() + " orders");
        return successCount;
    }
    
    /**
     * Get sales statistics for a seller
     * @param sellerId The seller's user ID
     * @return Object containing sales statistics (simplified - can be expanded)
     */
    public SalesStatistics getSalesStatistics(String sellerId) {
        List<Order> allOrders = orderDao.findBySellerId(sellerId);
        
        int totalOrders = allOrders.size();
        int pendingOrders = 0;
        int acceptedOrders = 0;
        int completedOrders = 0;
        int cancelledOrders = 0;
        double totalRevenue = 0.0;
        double avgRating = 0.0;
        int ratedOrders = 0;
        
        for (Order order : allOrders) {
            if (order.isPending()) pendingOrders++;
            if (order.isAccepted()) acceptedOrders++;
            if (order.isCompleted()) {
                completedOrders++;
                totalRevenue += order.getTotalPrice();
            }
            if (order.getStatus().equals(Order.STATUS_CANCELLED)) cancelledOrders++;
            
            if (order.isReviewed() && order.getBuyerRating() > 0) {
                avgRating += order.getBuyerRating();
                ratedOrders++;
            }
        }
        
        if (ratedOrders > 0) {
            avgRating = avgRating / ratedOrders;
        }
        
        return new SalesStatistics(
            sellerId, totalOrders, pendingOrders, acceptedOrders, 
            completedOrders, cancelledOrders, totalRevenue, avgRating
        );
    }
    
    /**
     * Helper method to update listing status
     * @param productId The listing/product ID
     * @param newStatus New status to set
     */
    private void updateListingStatus(String productId, String newStatus) {
        // Assuming productId is the same as listingId in your system
        basement_class.Enterprise_2.Listing listing = listingDao.findById(productId);
        if (listing != null) {
            listing.setStatus(newStatus);
            listingDao.update(listing);
            System.out.println("Listing " + productId + " status updated to: " + newStatus);
        }
    }
    
    /**
     * Find the SellerOrganization in the system
     * @return SellerOrganization or null if not found
     */
    private Organization findSellerOrganization() {
        for (Network n : system.getNetworks()) {
            for (Enterprise e : n.getEnterprises()) {
                if (e instanceof MarketplaceEnterprise) {
                    Organization o = e.getOrganizationByName("Seller Organization");
                    if (o != null)
                        return o;
                }
            }
        }
        return null;
    }
    
    /**
     * Inner class for sales statistics
     */
    public static class SalesStatistics {
        private final String sellerId;
        private final int totalOrders;
        private final int pendingOrders;
        private final int acceptedOrders;
        private final int completedOrders;
        private final int cancelledOrders;
        private final double totalRevenue;
        private final double averageRating;
        
        public SalesStatistics(String sellerId, int totalOrders, int pendingOrders, 
                              int acceptedOrders, int completedOrders, int cancelledOrders,
                              double totalRevenue, double averageRating) {
            this.sellerId = sellerId;
            this.totalOrders = totalOrders;
            this.pendingOrders = pendingOrders;
            this.acceptedOrders = acceptedOrders;
            this.completedOrders = completedOrders;
            this.cancelledOrders = cancelledOrders;
            this.totalRevenue = totalRevenue;
            this.averageRating = averageRating;
        }
        
        // Getters
        public String getSellerId() { return sellerId; }
        public int getTotalOrders() { return totalOrders; }
        public int getPendingOrders() { return pendingOrders; }
        public int getAcceptedOrders() { return acceptedOrders; }
        public int getCompletedOrders() { return completedOrders; }
        public int getCancelledOrders() { return cancelledOrders; }
        public double getTotalRevenue() { return totalRevenue; }
        public double getAverageRating() { return averageRating; }
        
        public double getCompletionRate() {
            return totalOrders > 0 ? (double) completedOrders / totalOrders * 100 : 0;
        }
        
        @Override
        public String toString() {
            return String.format(
                "Sales Statistics for Seller %s: %d total orders, %d completed, $%.2f revenue, %.1f avg rating",
                sellerId, totalOrders, completedOrders, totalRevenue, averageRating
            );
        }
    }
}
