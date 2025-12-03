/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.E1;

import common_class.Order;
import common_class.Product;
import basement_class.DAO.OrderDao;
import basement_class.DAO.ProductDao;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bob-h
 */
public class OrderService {
    
    private OrderDao orderDao;
    private ProductDao productDao;
    
    /**
     * Constructor
     */
    public OrderService() {
        this.orderDao = new OrderDao();
        this.productDao = new ProductDao();
    }
    
    /**
     * Constructor with DAO injection (for testing)
     */
    public OrderService(OrderDao orderDao, ProductDao productDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
    }
    
    // ==================== CREATE OPERATIONS ====================
    
    /**
     * Create a new order (buyer submits purchase request)
     * 
     * @param orderId Unique order ID
     * @param productId Product to purchase
     * @param buyerId Buyer user ID
     * @param sellerId Seller user ID
     * @return Created order, or null if failed
     * @throws IllegalStateException if product is not available
     */
    public Order createOrder(String orderId, String productId, String buyerId, String sellerId) {
        // 1. Validate product exists and is available
        Product product = productDao.findById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product not found: " + productId);
        }
        
        if (!"Available".equals(product.getStatus())) {
            throw new IllegalStateException("Product is not available for purchase");
        }
        
        // 2. Create order
        Order order = new Order(orderId, productId, buyerId, sellerId, product.getPrice());
        order.setStatus(Order.STATUS_PENDING);
        order.setQuantity(1);
        
        // 3. Save order
        boolean saved = orderDao.save(order);
        if (!saved) {
            return null;
        }
        
        // 4. Update product status to Reserved
        product.setStatus("Reserved");
        productDao.update(product);
        
        return order;
    }
    
    /**
     * Create order with additional details
     */
    public Order createOrder(String orderId, String productId, String buyerId, 
                           String sellerId, String deliveryMethod, String meetingLocation) {
        Order order = createOrder(orderId, productId, buyerId, sellerId);
        if (order != null) {
            order.setDeliveryMethod(deliveryMethod);
            order.setMeetingLocation(meetingLocation);
            orderDao.update(order);
        }
        return order;
    }
    
    // ==================== UPDATE OPERATIONS ====================
    
    /**
     * Accept an order (seller action)
     * 
     * @param orderId Order ID
     * @param sellerId Seller ID (for authorization)
     * @return true if successful
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
     * 
     * @param orderId Order ID
     * @param sellerId Seller ID (for authorization)
     * @return true if successful
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
        
        // Restore product to Available status
        Product product = productDao.findById(order.getProductId());
        if (product != null) {
            product.setStatus("Available");
            productDao.update(product);
        }
        
        return true;
    }
    
    /**
     * Complete an order (seller marks as completed after transaction)
     * 
     * @param orderId Order ID
     * @param sellerId Seller ID (for authorization)
     * @return true if successful
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
        
        // Update product to Sold status
        Product product = productDao.findById(order.getProductId());
        if (product != null) {
            product.setStatus("Sold");
            productDao.update(product);
        }
        
        return true;
    }
    
    /**
     * Cancel an order (buyer or seller action)
     * 
     * @param orderId Order ID
     * @param userId User ID (buyer or seller)
     * @return true if successful
     */
    public boolean cancelOrder(String orderId, String userId) {
        Order order = orderDao.findById(orderId);
        
        if (order == null) {
            return false;
        }
        
        // Verify authorization (must be buyer or seller)
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
        
        // Restore product to Available status
        Product product = productDao.findById(order.getProductId());
        if (product != null) {
            product.setStatus("Available");
            productDao.update(product);
        }
        
        return true;
    }
    
    /**
     * Update order delivery details
     */
    public boolean updateDeliveryDetails(String orderId, String userId, 
                                        String deliveryMethod, String meetingLocation) {
        Order order = orderDao.findById(orderId);
        
        if (order == null) {
            return false;
        }
        
        // Verify authorization
        if (!order.getBuyerId().equals(userId) && !order.getSellerId().equals(userId)) {
            return false;
        }
        
        order.setDeliveryMethod(deliveryMethod);
        order.setMeetingLocation(meetingLocation);
        
        return orderDao.update(order);
    }
    
    /**
     * Add review to completed order (buyer action)
     * 
     * @param orderId Order ID
     * @param buyerId Buyer ID (for authorization)
     * @param rating Rating (1-5)
     * @return true if successful
     */
    public boolean addReview(String orderId, String buyerId, int rating) {
        Order order = orderDao.findById(orderId);
        
        if (order == null) {
            return false;
        }
        
        // Verify buyer authorization
        if (!order.getBuyerId().equals(buyerId)) {
            return false;
        }
        
        // Can only review completed orders
        if (!Order.STATUS_COMPLETED.equals(order.getStatus())) {
            return false;
        }
        
        // Validate rating
        if (rating < 1 || rating > 5) {
            return false;
        }
        
        // Add review
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
     * Get all orders for a seller
     */
    public List<Order> getSellerOrders(String sellerId) {
        return orderDao.findBySellerId(sellerId);
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
     * Get buyer's cancelled orders
     */
    public List<Order> getBuyerCancelledOrders(String buyerId) {
        List<Order> allOrders = orderDao.findByBuyerId(buyerId);
        return filterByStatus(allOrders, Order.STATUS_CANCELLED);
    }
    
    /**
     * Get seller's pending orders (orders waiting for seller's response)
     */
    public List<Order> getSellerPendingOrders(String sellerId) {
        return orderDao.getPendingOrdersForSeller(sellerId);
    }
    
    /**
     * Get seller's accepted orders
     */
    public List<Order> getSellerAcceptedOrders(String sellerId) {
        List<Order> allOrders = orderDao.findBySellerId(sellerId);
        return filterByStatus(allOrders, Order.STATUS_ACCEPTED);
    }
    
    /**
     * Get seller's completed orders
     */
    public List<Order> getSellerCompletedOrders(String sellerId) {
        List<Order> allOrders = orderDao.findBySellerId(sellerId);
        return filterByStatus(allOrders, Order.STATUS_COMPLETED);
    }
    
    /**
     * Get all orders for a specific product
     */
    public List<Order> getProductOrders(String productId) {
        return orderDao.findByProductId(productId);
    }
    
    /**
     * Get all orders by status
     */
    public List<Order> getOrdersByStatus(String status) {
        return orderDao.findByStatus(status);
    }
    
    // ==================== STATISTICS & ANALYTICS ====================
    
    /**
     * Get buyer's total number of orders
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
    
    /**
     * Get seller's total number of orders
     */
    public int getSellerOrderCount(String sellerId) {
        return orderDao.findBySellerId(sellerId).size();
    }
    
    /**
     * Get seller's completed order count
     */
    public int getSellerCompletedOrderCount(String sellerId) {
        return getSellerCompletedOrders(sellerId).size();
    }
    
    /**
     * Calculate buyer's total spending (completed orders)
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
     * Calculate seller's total revenue (completed orders)
     */
    public double getSellerTotalRevenue(String sellerId) {
        List<Order> completedOrders = getSellerCompletedOrders(sellerId);
        double total = 0.0;
        for (Order order : completedOrders) {
            total += order.getTotalPrice();
        }
        return total;
    }
    
    /**
     * Get buyer's average order value
     */
    public double getBuyerAverageOrderValue(String buyerId) {
        List<Order> completedOrders = getBuyerCompletedOrders(buyerId);
        if (completedOrders.isEmpty()) {
            return 0.0;
        }
        return getBuyerTotalSpending(buyerId) / completedOrders.size();
    }
    
    /**
     * Calculate order completion rate for buyer
     */
    public double getBuyerCompletionRate(String buyerId) {
        List<Order> allOrders = orderDao.findByBuyerId(buyerId);
        if (allOrders.isEmpty()) {
            return 0.0;
        }
        int completed = getBuyerCompletedOrderCount(buyerId);
        return (double) completed / allOrders.size() * 100;
    }
    
    /**
     * Calculate order acceptance rate for seller
     */
    public double getSellerAcceptanceRate(String sellerId) {
        List<Order> allOrders = orderDao.findBySellerId(sellerId);
        if (allOrders.isEmpty()) {
            return 0.0;
        }
        
        int accepted = 0;
        for (Order order : allOrders) {
            if (Order.STATUS_ACCEPTED.equals(order.getStatus()) || 
                Order.STATUS_COMPLETED.equals(order.getStatus())) {
                accepted++;
            }
        }
        
        return (double) accepted / allOrders.size() * 100;
    }
    
    /**
     * Check if buyer has any pending orders for a specific product
     */
    public boolean hasPendingOrderForProduct(String buyerId, String productId) {
        List<Order> orders = orderDao.findByBuyerId(buyerId);
        for (Order order : orders) {
            if (order.getProductId().equals(productId) && 
                Order.STATUS_PENDING.equals(order.getStatus())) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Check if product has any active orders (pending or accepted)
     */
    public boolean hasActiveOrders(String productId) {
        List<Order> orders = orderDao.findByProductId(productId);
        for (Order order : orders) {
            if (Order.STATUS_PENDING.equals(order.getStatus()) || 
                Order.STATUS_ACCEPTED.equals(order.getStatus())) {
                return true;
            }
        }
        return false;
    }
    
    // ==================== VALIDATION ====================
    
    /**
     * Validate if buyer can create order for product
     */
    public boolean canCreateOrder(String buyerId, String productId) {
        // Check if product exists
        Product product = productDao.findById(productId);
        if (product == null) {
            return false;
        }
        
        // Check if product is available
        if (!"Available".equals(product.getStatus())) {
            return false;
        }
        
        // Check if buyer already has pending order for this product
        if (hasPendingOrderForProduct(buyerId, productId)) {
            return false;
        }
        
        // Check if buyer is not the seller (can't buy own product)
        if (product.getSellerId().equals(buyerId)) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Validate order status transition
     */
    public boolean isValidStatusTransition(String currentStatus, String newStatus) {
        // PENDING → ACCEPTED, REJECTED, CANCELLED
        if (Order.STATUS_PENDING.equals(currentStatus)) {
            return Order.STATUS_ACCEPTED.equals(newStatus) || 
                   Order.STATUS_REJECTED.equals(newStatus) ||
                   Order.STATUS_CANCELLED.equals(newStatus);
        }
        
        // ACCEPTED → COMPLETED, CANCELLED
        if (Order.STATUS_ACCEPTED.equals(currentStatus)) {
            return Order.STATUS_COMPLETED.equals(newStatus) ||
                   Order.STATUS_CANCELLED.equals(newStatus);
        }
        
        // Terminal states cannot transition
        return false;
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
    
    /**
     * Get all orders (for admin/reporting)
     */
    public List<Order> getAllOrders() {
        return orderDao.getAllOrders();
    }
    
    /**
     * Get total order count
     */
    public int getTotalOrderCount() {
        return orderDao.count();
    }
    
    /**
     * Delete order (admin only, for testing)
     */
    public boolean deleteOrder(String orderId) {
        return orderDao.delete(orderId);
    }
}
