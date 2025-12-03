/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.DAO;

import common_class.Product;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bob-h
 */
public class ProductDao {
    
    private static final String FILE_PATH = "data/products.csv";
    private static final String DELIMITER = ",";
    private static final String IMAGE_DELIMITER = ";";  // For multiple image paths
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    
    private List<Product> products;
    
    /**
     * Constructor - loads products from CSV
     */
    public ProductDao() {
        this.products = new ArrayList<>();
        loadFromCSV();
    }
    
    /**
     * Save a new product
     * @param product Product to save
     * @return true if successful
     */
    public boolean save(Product product) {
        if (product == null || product.getProductId() == null) {
            return false;
        }
        
        // Check for duplicate
        if (findById(product.getProductId()) != null) {
            return false;
        }
        
        products.add(product);
        saveToCSV();
        return true;
    }
    
    /**
     * Update an existing product
     * @param product Product to update
     * @return true if successful
     */
    public boolean update(Product product) {
        if (product == null || product.getProductId() == null) {
            return false;
        }
        
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductId().equals(product.getProductId())) {
                product.touch(); // Update timestamp
                products.set(i, product);
                saveToCSV();
                return true;
            }
        }
        return false;
    }
    
    /**
     * Delete a product by ID
     * @param productId Product ID
     * @return true if successful
     */
    public boolean delete(String productId) {
        Product product = findById(productId);
        if (product != null) {
            products.remove(product);
            saveToCSV();
            return true;
        }
        return false;
    }
    
    /**
     * Find product by ID
     * @param productId Product ID
     * @return Product if found, null otherwise
     */
    public Product findById(String productId) {
        for (Product product : products) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }
    
    /**
     * Find all products by seller ID
     * @param sellerId Seller ID
     * @return List of products
     */
    public List<Product> findBySellerId(String sellerId) {
        List<Product> result = new ArrayList<>();
        for (Product product : products) {
            if (product.getSellerId().equals(sellerId)) {
                result.add(product);
            }
        }
        return result;
    }
    
    /**
     * Find all products by category
     * @param category Category name
     * @return List of products
     */
    public List<Product> findByCategory(String category) {
        List<Product> result = new ArrayList<>();
        for (Product product : products) {
            if (category.equals(product.getCategory())) {
                result.add(product);
            }
        }
        return result;
    }
    
    /**
     * Find all products by status
     * @param status Product status
     * @return List of products
     */
    public List<Product> findByStatus(String status) {
        List<Product> result = new ArrayList<>();
        for (Product product : products) {
            if (status.equals(product.getStatus())) {
                result.add(product);
            }
        }
        return result;
    }
    
    /**
     * Find all available products
     * @return List of available products
     */
    public List<Product> findAvailableProducts() {
        return findByStatus("Available");
    }
    
    /**
     * Find products by condition
     * @param condition Product condition
     * @return List of products
     */
    public List<Product> findByCondition(String condition) {
        List<Product> result = new ArrayList<>();
        for (Product product : products) {
            if (condition.equals(product.getCondition())) {
                result.add(product);
            }
        }
        return result;
    }
    
    /**
     * Find products by price range
     * @param minPrice Minimum price (null for no minimum)
     * @param maxPrice Maximum price (null for no maximum)
     * @return List of products
     */
    public List<Product> findByPriceRange(Double minPrice, Double maxPrice) {
        List<Product> result = new ArrayList<>();
        for (Product product : products) {
            boolean inRange = true;
            
            if (minPrice != null && product.getPrice() < minPrice) {
                inRange = false;
            }
            if (maxPrice != null && product.getPrice() > maxPrice) {
                inRange = false;
            }
            
            if (inRange) {
                result.add(product);
            }
        }
        return result;
    }
    
    /**
     * Search products by keyword (searches in title and description)
     * @param keyword Search keyword
     * @return List of matching products
     */
    public List<Product> searchByKeyword(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        List<Product> result = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();
        
        for (Product product : products) {
            boolean matches = false;
            
            // Search in title
            if (product.getTitle() != null && 
                product.getTitle().toLowerCase().contains(lowerKeyword)) {
                matches = true;
            }
            
            // Search in description
            if (product.getDescription() != null && 
                product.getDescription().toLowerCase().contains(lowerKeyword)) {
                matches = true;
            }
            
            if (matches) {
                result.add(product);
            }
        }
        
        return result;
    }
    
    /**
     * Get all products
     * @return List of all products
     */
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }
    
    /**
     * Get total number of products
     * @return Count of products
     */
    public int count() {
        return products.size();
    }
    
    /**
     * Get products sorted by view count (most viewed first)
     * @param limit Maximum number of products to return
     * @return List of most viewed products
     */
    public List<Product> getMostViewedProducts(int limit) {
        List<Product> sorted = new ArrayList<>(products);
        sorted.sort((p1, p2) -> Integer.compare(p2.getViewCount(), p1.getViewCount()));
        return sorted.subList(0, Math.min(limit, sorted.size()));
    }
    
    /**
     * Get products sorted by favorite count (most favorited first)
     * @param limit Maximum number of products to return
     * @return List of most favorited products
     */
    public List<Product> getMostFavoritedProducts(int limit) {
        List<Product> sorted = new ArrayList<>(products);
        sorted.sort((p1, p2) -> Integer.compare(p2.getFavoriteCount(), p1.getFavoriteCount()));
        return sorted.subList(0, Math.min(limit, sorted.size()));
    }
    
    /**
     * Get negotiable products
     * @return List of negotiable products
     */
    public List<Product> getNegotiableProducts() {
        List<Product> result = new ArrayList<>();
        for (Product product : products) {
            if (product.isNegotiable()) {
                result.add(product);
            }
        }
        return result;
    }
    
    /**
     * Load products from CSV file
     */
    private void loadFromCSV() {
        File file = new File(FILE_PATH);
        
        // Create file if it doesn't exist
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                writeHeader();
            } catch (IOException e) {
                System.err.println("Error creating products.csv: " + e.getMessage());
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
                
                Product product = parseProduct(line);
                if (product != null) {
                    products.add(product);
                }
            }
            
            System.out.println("Loaded " + products.size() + " products from CSV");
            
        } catch (IOException e) {
            System.err.println("Error reading products.csv: " + e.getMessage());
        }
    }
    
    /**
     * Save products to CSV file
     */
    private void saveToCSV() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {
            // Write header
            pw.println(getCSVHeader());
            
            // Write data
            for (Product product : products) {
                pw.println(toCSV(product));
            }
            
            System.out.println("Saved " + products.size() + " products to CSV");
            
        } catch (IOException e) {
            System.err.println("Error writing products.csv: " + e.getMessage());
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
        return "productId,sellerId,title,description,category,condition,price," +
               "status,isNegotiable,imagePaths,viewCount,favoriteCount,createdAt,updatedAt";
    }
    
    /**
     * Parse CSV line to Product object
     */
    private Product parseProduct(String line) {
        try {
            String[] parts = line.split(DELIMITER, -1);
            
            if (parts.length < 14) {
                return null;
            }
            
            // Create product with required fields
            Product product = new Product(
                parts[0],  // productId
                parts[1],  // sellerId
                parts[2],  // title
                Double.parseDouble(parts[6])  // price
            );
            
            // Set optional fields
            product.setDescription(parts[3].isEmpty() ? null : parts[3]);
            product.setCategory(parts[4].isEmpty() ? null : parts[4]);
            product.setCondition(parts[5].isEmpty() ? "Used" : parts[5]);
            product.setStatus(parts[7].isEmpty() ? "Available" : parts[7]);
            product.setNegotiable(Boolean.parseBoolean(parts[8]));
            
            // Parse image paths
            if (!parts[9].isEmpty()) {
                String[] images = parts[9].split(IMAGE_DELIMITER);
                for (String imagePath : images) {
                    if (!imagePath.trim().isEmpty()) {
                        product.addImage(imagePath.trim());
                    }
                }
            }
            
            // Parse counts
            try {
                int viewCount = Integer.parseInt(parts[10]);
                for (int i = 0; i < viewCount; i++) {
                    product.increaseViewCount();
                }
            } catch (NumberFormatException e) {
                // Keep default 0
            }
            
            try {
                int favoriteCount = Integer.parseInt(parts[11]);
                for (int i = 0; i < favoriteCount; i++) {
                    product.increaseFavoriteCount();
                }
            } catch (NumberFormatException e) {
                // Keep default 0
            }
            
            // Parse timestamps
            if (!parts[12].isEmpty()) {
                product.setCreatedAt(LocalDateTime.parse(parts[12], DATE_FORMATTER));
            }
            if (!parts[13].isEmpty()) {
                product.setUpdatedAt(LocalDateTime.parse(parts[13], DATE_FORMATTER));
            }
            
            return product;
            
        } catch (Exception e) {
            System.err.println("Error parsing product line: " + line);
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Convert Product object to CSV line
     */
    private String toCSV(Product product) {
        StringBuilder sb = new StringBuilder();
        
        sb.append(product.getProductId()).append(DELIMITER);
        sb.append(product.getSellerId()).append(DELIMITER);
        sb.append(escapeCsv(product.getTitle())).append(DELIMITER);
        sb.append(escapeCsv(product.getDescription())).append(DELIMITER);
        sb.append(product.getCategory() != null ? product.getCategory() : "").append(DELIMITER);
        sb.append(product.getCondition()).append(DELIMITER);
        sb.append(product.getPrice()).append(DELIMITER);
        sb.append(product.getStatus()).append(DELIMITER);
        sb.append(product.isNegotiable()).append(DELIMITER);
        
        // Join image paths with semicolon
        if (product.getImagePaths() != null && !product.getImagePaths().isEmpty()) {
            sb.append(String.join(IMAGE_DELIMITER, product.getImagePaths()));
        }
        sb.append(DELIMITER);
        
        sb.append(product.getViewCount()).append(DELIMITER);
        sb.append(product.getFavoriteCount()).append(DELIMITER);
        sb.append(product.getCreatedAt() != null ? product.getCreatedAt().format(DATE_FORMATTER) : "").append(DELIMITER);
        sb.append(product.getUpdatedAt() != null ? product.getUpdatedAt().format(DATE_FORMATTER) : "");
        
        return sb.toString();
    }
    
    /**
     * Escape special characters in CSV fields
     */
    private String escapeCsv(String value) {
        if (value == null) {
            return "";
        }
        
        // If value contains comma, newline, or quotes, wrap in quotes and escape quotes
        if (value.contains(",") || value.contains("\n") || value.contains("\"")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        
        return value;
    }
    
    /**
     * Clear all products (for testing)
     */
    public void clear() {
        products.clear();
        saveToCSV();
    }
}
