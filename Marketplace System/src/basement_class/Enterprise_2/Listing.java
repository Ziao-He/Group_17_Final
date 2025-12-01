/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_2;

import basement_class.BaseEntity;

/**
 *
 * @author Linyiyang
 */
public class Listing extends BaseEntity {
    private String sellerId;
    private String title;
    private String status;   // Pending / Approved / Rejected

    public Listing(String id, String sellerId, String title) {
        this.id = id;
        this.sellerId = sellerId;
        this.title = title;
        this.status = "Pending";
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
    }

    public String getStatus() { 
        return status; 
    }
    
    public void setStatus(String status) {
        this.status = status; 
    }    
    
}
