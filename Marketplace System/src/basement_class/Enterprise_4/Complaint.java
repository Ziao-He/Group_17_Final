/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_4;

import basement_class.BaseEntity;
import basement_class.UserAccount;

/**
 *
 * @author yujie-liang
 */

public class Complaint extends BaseEntity{
    private UserAccount complainant;
    private UserAccount targetUser;
    private String description;
    private String category;
    private boolean IsSeverity;
    
    //private String product;
    //private String order;
    private String status;
    private String resolutionNote;

    public Complaint(UserAccount complainant, UserAccount targetUser, String description, String category, boolean isSeverity) {
        this.complainant = complainant;
        this.targetUser = targetUser;
        this.description = description;
        this.category = category;
        this.IsSeverity = isSeverity;
        this.status = "Pending";
    }
    
        public void markInProgress() {
        this.status = "In Progress";
        this.touch();
    }

    public void markResolved(String note) {
        this.status = "Resolved";
        this.resolutionNote = note;
        this.touch();
    }

    public void markRejected(String note) {
        this.status = "Rejected";
        this.resolutionNote = note;
        this.touch();
    }

    public void escalate(String note) {
        this.status = "Escalated";
        this.resolutionNote = note;
        this.touch();
    }

    public UserAccount getComplainant() {
        return complainant;
    }

    public void setComplainant(UserAccount complainant) {
        this.complainant = complainant;
    }

    public UserAccount getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(UserAccount targetUser) {
        this.targetUser = targetUser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isIsSeverity() {
        return IsSeverity;
    }

    public void setIsSeverity(boolean IsSeverity) {
        this.IsSeverity = IsSeverity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResolutionNote() {
        return resolutionNote;
    }

    public void setResolutionNote(String resolutionNote) {
        this.resolutionNote = resolutionNote;
    }
    
    
}
