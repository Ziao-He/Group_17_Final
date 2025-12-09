/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_2;

import basement_class.BaseEntity;
import basement_class.UserAccount;
import java.time.LocalDateTime;

/**
 *
 * @author Linyiyang
 */
public class Listing extends BaseEntity {
    private UserAccount seller;    
    private String title;           
    private String description;     
    private String imagePath;       
    private double price;           
    private String status;          // Pending / Approved / Rejected
    private LocalDateTime submitTime; 
    private int quantity = 1;
    public Listing(String id, UserAccount seller, String title, String description, String imagePath, double price) {
        this.id = id;
        this.seller = seller;
        this.title = title;
        this.description = description;
        this.imagePath = imagePath;
        this.price = price;
        this.status = "Pending";
        this.quantity = 1;
        this.submitTime = LocalDateTime.now();
    }


    public UserAccount getSeller() {
        return seller;
    }

    public void setSeller(UserAccount seller) {
        this.seller = seller;
    }

    public String getTitle() { 
        return title; 
    }
    
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTitle(String title) { 
        this.title = title; 
    }

    public String getDescription() { 
        return description; 
    }

    public void setDescription(String description) { 
        this.description = description; 
    }

    public String getImagePath() { 
        return imagePath; 
    }

    public void setImagePath(String imagePath) { 
        this.imagePath = imagePath; 
    }

    public double getPrice() { 
        return price; 
    }

    public void setPrice(double price) { 
        this.price = price; 
    }

    public String getStatus() { 
        return status; 
    }

    public void setStatus(String status) { 
        this.status = status; 
    }

    public LocalDateTime getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(LocalDateTime submitTime) {
        this.submitTime = submitTime;
    }
    
    public String getSellerId(){
        return this.getSeller().getUserId();
    }

}
