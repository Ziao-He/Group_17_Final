/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.DAO;

import common_class.Order;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bob-h
 */
public class OrderDao {
    
    private static final String FILE_PATH = "data/orders.csv";
    private static final String DELIMITER = ",";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    
    private List<Order> orders;
    
    /**
     * Constructor - loads orders from CSV
     */
    public OrderDao() {
        this.orders = new ArrayList<>();
        loadFromCSV();
    }
    
    /**
     * Save a new order
     * @param order Order to save
     * @return true if successful
     */
    public boolean save(Order order) {
        if (order == null || order.getOrderId() == null) {
            return false;
        }
        
        // Check for duplicate
        if (findById(order.getOrderId()) != null) {
            return false;
        }
        
        orders.add(order);
        saveToCSV();
        return true;
    }
    
    /**
     * Update an existing order
     * @param order Order to update
     * @return true if successful
     */
    public boolean update(Order order) {
        if (order == null || order.getOrderId() == null) {
            return false;
        }
        
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getOrderId().equals(order.getOrderId())) {
                order.touch(); // Update timestamp
                orders.set(i, order);
                saveToCSV();
                return true;
            }
        }
        return false;
    }
    
    /**
     * Delete an order by ID
     * @param orderId Order ID
     * @return true if successful
     */
    public boolean delete(String orderId) {
        Order order = findById(orderId);
        if (order != null) {
            orders.remove(order);
            saveToCSV();
            return true;
        }
        return false;
    }
    
    /**
     * Find order by ID
     * @param orderId Order ID
     * @return Order if found, null otherwise
     */
    public Order findById(String orderId) {
        for (Order order : orders) {
            if (order.getOrderId().equals(orderId)) {
                return order;
            }
        }
        return null;
    }
    
    /**
     * Find all orders by buyer ID
     * @param buyerId Buyer ID
     * @return List of orders
     */
    public List<Order> findByBuyerId(String buyerId) {
        List<Order> result = new ArrayList<>();
        for (Order order : orders) {
            if (order.getBuyerId().equals(buyerId)) {
                result.add(order);
            }
        }
        return result;
    }
    
    /**
     * Find all orders by seller ID
     * @param sellerId Seller ID
     * @return List of orders
     */
    public List<Order> findBySellerId(String sellerId) {
        List<Order> result = new ArrayList<>();
        for (Order order : orders) {
            if (order.getSellerId().equals(sellerId)) {
                result.add(order);
            }
        }
        return result;
    }
    
    /**
     * Find all orders by product ID
     * @param productId Product ID
     * @return List of orders
     */
    public List<Order> findByProductId(String productId) {
        List<Order> result = new ArrayList<>();
        for (Order order : orders) {
            if (order.getProductId().equals(productId)) {
                result.add(order);
            }
        }
        return result;
    }
    
    /**
     * Find all orders by status
     * @param status Order status
     * @return List of orders
     */
    public List<Order> findByStatus(String status) {
        List<Order> result = new ArrayList<>();
        for (Order order : orders) {
            if (order.getStatus().equals(status)) {
                result.add(order);
            }
        }
        return result;
    }
    
    /**
     * Get all orders
     * @return List of all orders
     */
    public List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }
    
    /**
     * Get total number of orders
     * @return Count of orders
     */
    public int count() {
        return orders.size();
    }
    
    /**
     * Load orders from CSV file
     */
    private void loadFromCSV() {
        File file = new File(FILE_PATH);
        
        // Create file if it doesn't exist
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                // Write header
                writeHeader();
            } catch (IOException e) {
                System.err.println("Error creating orders.csv: " + e.getMessage());
            }
            return;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstLine = true;
            
            while ((line = br.readLine()) != null) {
                // Skip header
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                
                Order order = parseOrder(line);
                if (order != null) {
                    orders.add(order);
                }
            }
            
            System.out.println("Loaded " + orders.size() + " orders from CSV");
            
        } catch (IOException e) {
            System.err.println("Error reading orders.csv: " + e.getMessage());
        }
    }
    
    /**
     * Save orders to CSV file
     */
    private void saveToCSV() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {
            // Write header
            pw.println(getCSVHeader());
            
            // Write data
            for (Order order : orders) {
                pw.println(toCSV(order));
            }
            
            System.out.println("Saved " + orders.size() + " orders to CSV");
            
        } catch (IOException e) {
            System.err.println("Error writing orders.csv: " + e.getMessage());
        }
    }
    
    /**
     * Write CSV header
     */
    private void writeHeader() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {
            pw.println(getCSVHeader());
        } catch (IOException e) {
            System.err.println("Error writing header: " + e.getMessage());
        }
    }
    
    /**
     * Get CSV header
     */
    private String getCSVHeader() {
        return "orderId,productId,buyerId,sellerId,totalPrice,quantity,status," +
               "deliveryMethod,meetingLocation,isReviewed,buyerRating,createdAt,updatedAt";
    }
    
    /**
     * Parse CSV line to Order object
     */
    private Order parseOrder(String line) {
        try {
            String[] parts = line.split(DELIMITER, -1); // -1 to keep empty trailing fields
            
            if (parts.length < 13) {
                return null;
            }
            
            Order order = new Order();
            order.setOrderId(parts[0]);
            order.setProductId(parts[1]);
            order.setBuyerId(parts[2]);
            order.setSellerId(parts[3]);
            order.setTotalPrice(Double.parseDouble(parts[4]));
            order.setQuantity(Integer.parseInt(parts[5]));
            order.setStatus(parts[6]);
            order.setDeliveryMethod(parts[7].isEmpty() ? null : parts[7]);
            order.setMeetingLocation(parts[8].isEmpty() ? null : parts[8]);
            order.setReviewed(Boolean.parseBoolean(parts[9]));
            order.setBuyerRating(Integer.parseInt(parts[10]));
            
            if (!parts[11].isEmpty()) {
                order.setCreatedAt(LocalDateTime.parse(parts[11], DATE_FORMATTER));
            }
            if (!parts[12].isEmpty()) {
                order.setUpdatedAt(LocalDateTime.parse(parts[12], DATE_FORMATTER));
            }
            
            return order;
            
        } catch (Exception e) {
            System.err.println("Error parsing order line: " + line);
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Convert Order object to CSV line
     */
    private String toCSV(Order order) {
        StringBuilder sb = new StringBuilder();
        
        sb.append(order.getOrderId()).append(DELIMITER);
        sb.append(order.getProductId()).append(DELIMITER);
        sb.append(order.getBuyerId()).append(DELIMITER);
        sb.append(order.getSellerId()).append(DELIMITER);
        sb.append(order.getTotalPrice()).append(DELIMITER);
        sb.append(order.getQuantity()).append(DELIMITER);
        sb.append(order.getStatus()).append(DELIMITER);
        sb.append(order.getDeliveryMethod() != null ? order.getDeliveryMethod() : "").append(DELIMITER);
        sb.append(order.getMeetingLocation() != null ? order.getMeetingLocation() : "").append(DELIMITER);
        sb.append(order.isReviewed()).append(DELIMITER);
        sb.append(order.getBuyerRating()).append(DELIMITER);
        sb.append(order.getCreatedAt() != null ? order.getCreatedAt().format(DATE_FORMATTER) : "").append(DELIMITER);
        sb.append(order.getUpdatedAt() != null ? order.getUpdatedAt().format(DATE_FORMATTER) : "");
        
        return sb.toString();
    }
    
    /**
     * Clear all orders (for testing)
     */
    public void clear() {
        orders.clear();
        saveToCSV();
    }
}
