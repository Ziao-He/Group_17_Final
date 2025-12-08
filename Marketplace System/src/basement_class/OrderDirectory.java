/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class;


import common_class.Order;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 *
 * @author 心火牧神塞勒斯
 */
public class OrderDirectory {

    private final List<Order> orderList;

    public OrderDirectory() {
        this.orderList = new ArrayList<>();
    }

    // =====================================================
    // ✅ Basic CRUD
    // =====================================================

    public void addOrder(Order order) {
        if (order != null && !orderList.contains(order)) {
            orderList.add(order);
        }
    }
    public void updateOrder(Order order) {
        
    }

    public boolean removeOrder(Order order) {
        return orderList.remove(order);
    }

    public Order findByOrderId(String orderId) {
        for (Order o : orderList) {
            if (o.getOrderId().equals(orderId)) {
                return o;
            }
        }
        return null;
    }

    public List<Order> getAllOrders() {
        return new ArrayList<>(orderList);
    }

    public int size() {
        return orderList.size();
    }

    // =====================================================
    // ✅ Query helpers（业务高频）
    // =====================================================

    /** All orders created by a buyer */
    public List<Order> findByBuyerId(String buyerId) {
        return orderList.stream()
                .filter(o -> buyerId.equals(o.getBuyerId()))
                .collect(Collectors.toList());
    }

    /** All orders received by a seller */
    public List<Order> findBySellerId(String sellerId) {
        return orderList.stream()
                .filter(o -> sellerId.equals(o.getSellerId()))
                .collect(Collectors.toList());
    }

    /** Orders related to a specific listing */
    public List<Order> findByListingId(String listingId) {
        return orderList.stream()
                .filter(o -> listingId.equals(o.getListingId()))
                .collect(Collectors.toList());
    }

    /** Orders by status (PENDING / ACCEPTED / COMPLETED / ...) */
    public List<Order> findByStatus(String status) {
        return orderList.stream()
                .filter(o -> status.equalsIgnoreCase(o.getStatus()))
                .collect(Collectors.toList());
    }

    /** Pending orders for seller (high-frequency use case) */
    public List<Order> findPendingOrdersForSeller(String sellerId) {
        return orderList.stream()
                .filter(o -> sellerId.equals(o.getSellerId()))
                .filter(Order::isPending)
                .collect(Collectors.toList());
    }

    /** Accepted but not completed orders */
    public List<Order> findActiveOrdersForSeller(String sellerId) {
        return orderList.stream()
                .filter(o -> sellerId.equals(o.getSellerId()))
                .filter(o -> Order.STATUS_ACCEPTED.equals(o.getStatus()))
                .collect(Collectors.toList());
    }
    
    /**
     * Find order by order ID
     */
    public Order findById(String orderId) {
        if (orderId == null || orderList == null) {
           return null;
        }

        for (Order order : orderList) {
            if (order.getOrderId().equals(orderId)) {
                return order;
            }
        }

        return null;
    }

    // =====================================================
    // ✅ Utility
    // =====================================================

    public boolean containsOrder(String orderId) {
        return findByOrderId(orderId) != null;
    }

    public void clear() {
        orderList.clear();
    }
}