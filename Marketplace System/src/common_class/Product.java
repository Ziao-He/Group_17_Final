/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package common_class;

import basement_class.BaseEntity;
import java.util.ArrayList;
/**
 *
 * @author 心火牧神塞勒斯
 */
public class Product extends BaseEntity {

    private String productId;         // Optional: duplicate of BaseEntity.id for external referencing
    private String sellerId;          // UserAccount.userId of the seller
    
    private String title;
    private String description;
    private String category;          // "Electronics", "Furniture", "Books", etc.
    private String condition;         // New, Like New, Used, Acceptable
    private double price;
    
    private String status;            // Available, Reserved, Sold, Removed
    private boolean isNegotiable;

    private ArrayList<String> imagePaths;

    private int viewCount;
    private int favoriteCount;

    /**
     * Constructor with basic required fields.
     */
    public Product(String id, String sellerId, String title, double price) {
        this.id = id;           // BaseEntity id
        this.productId = id;    // mirror id for Order reference

        this.sellerId = sellerId;
        this.title = title;
        this.price = price;

        this.status = "Available";
        this.condition = "Used";   // Default condition
        this.isNegotiable = false;

        this.imagePaths = new ArrayList<>();
        this.viewCount = 0;
        this.favoriteCount = 0;
    }


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.touch();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.touch();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
        this.touch();
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
        this.touch();
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
        this.touch();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        this.touch();
    }

    public boolean isNegotiable() {
        return isNegotiable;
    }

    public void setNegotiable(boolean negotiable) {
        isNegotiable = negotiable;
        this.touch();
    }

    public ArrayList<String> getImagePaths() {
        return imagePaths;
    }

    public void addImage(String imagePath) {
        this.imagePaths.add(imagePath);
        this.touch();
    }

    public int getViewCount() {
        return viewCount;
    }

    public void increaseViewCount() {
        this.viewCount++;
        this.touch();
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void increaseFavoriteCount() {
        this.favoriteCount++;
        this.touch();
    }

    @Override
    public String toString() {
        return "Product{id='" + productId + "', title='" + title + "', price=" + price + "}";
    }
}
