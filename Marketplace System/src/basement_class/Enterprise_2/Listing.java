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
    private UserAccount seller;     // 卖家对象（最标准做法）
    private String title;           // 商品标题
    private String description;     // 商品描述
    private String imagePath;       // 商品图片路径
    private double price;           // 商品价格（可选，但课程里常用）
    private String status;          // Pending / Approved / Rejected
    private LocalDateTime submitTime; // 提交时间戳
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

    // ===== Getter / Setter =====

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
