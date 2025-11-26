/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class;

import java.time.LocalDateTime;

/**
 *
 * @author yujie-liang
 * Base class for all entities.
 * Generates IDs sequentially (1, 2, 3...) using a static counter.
 */
public class BaseEntity {
    protected String id;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
    
    // Static counter shared by all classes extending BaseEntity
    // This ensures every new object gets a unique number: 1, 2, 3...
    private static int count = 0;

    public BaseEntity() {
        count++; // Increment counter
        this.id = String.valueOf(count); // Convert number to String
        
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }
    
    // Allow setting ID manually (useful when loading data from CSV later)
    public void setId(String id) {
        this.id = id;
        // Optional: Update counter if the loaded ID is larger, to prevent duplicates
        try {
            int currentId = Integer.parseInt(id);
            if (currentId > count) {
                count = currentId;
            }
        } catch (NumberFormatException e) {
            // Ignore if ID is not a number
        }
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}