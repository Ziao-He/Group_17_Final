/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class;

import java.time.LocalDateTime;

/**
 *
 * @author yujie-liang
 */
public abstract class WorkRequest{
    protected String id;                    // Unique WorkRequest ID (e.g., "WR001", "WR002")
    protected UserAccount sender;           // Who created this request
    protected UserAccount receiver;         // Who should handle this request
    protected String status;                // Current status
    protected LocalDateTime requestDate;    // When request was created
    protected LocalDateTime resolveDate;    // When request was resolved
    
    protected UserAccount reviewer;
    protected String reviewAction;
    protected String reviewComment;

    public UserAccount getReviewer() {
        return reviewer;
    }

    public String getReviewAction() {
        return reviewAction;
    }

    public String getReviewComment() {
        return reviewComment;
    }
    
    public WorkRequest() {
        this.requestDate = LocalDateTime.now();
    }
    
    /**
     * Update the status of this work request
     * @param newStatus New status value
     */
    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }
    
    /**
     * Mark this request as resolved
     */
    public void resolve() {
        this.resolveDate = LocalDateTime.now();
    }
    
    /**
     * Check if this request is resolved
     * @return true if resolved, false otherwise
     */
    public boolean isResolved() {
        return resolveDate != null;
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public UserAccount getSender() {
        return sender;
    }
    
    public void setSender(UserAccount sender) {
        this.sender = sender;
    }
    
    public UserAccount getReceiver() {
        return receiver;
    }
    
    public void setReceiver(UserAccount receiver) {
        this.receiver = receiver;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public LocalDateTime getRequestDate() {
        return requestDate;
    }
    
    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }
    
    public LocalDateTime getResolveDate() {
        return resolveDate;
    }
    
    public void setResolveDate(LocalDateTime resolveDate) {
        this.resolveDate = resolveDate;
    }
    
        public void resolve(UserAccount reviewer, String action, String comment) {
        this.reviewer = reviewer;
        this.reviewAction = action;
        this.reviewComment = comment;
        this.resolveDate = LocalDateTime.now();
    }
        
        
}

