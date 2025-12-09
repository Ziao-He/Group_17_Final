/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_3.WorkRequest;

import basement_class.Enterprise_2.Listing;
import basement_class.UserAccount;
import basement_class.WorkRequest;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Linyiyang
 */
public class PolicyViolationRequest extends WorkRequest {
    private static int counter=1;
    private UserAccount reporter;        
    private UserAccount targetUser;     
    private Listing listing;             
    private String violationCategory;    // account_issue / listing_issue / minor_dispute
    private String violationInfo;        
    private List<String> evidencePaths;  
    private String rejectionReason;     
    private String decisionLevel;   // SINGLE_ONLY / ALL_LISTINGS / ALL_AND_BAN
    private String decisionReason;

    public String getDecisionReason() {
        return decisionReason;
    }

    public void setDecisionReason(String decisionReason) {
        this.decisionReason = decisionReason;
    }

    public String getFinalDecision() {
        return finalDecision;
    }

    public void setFinalDecision(String finalDecision) {
        this.finalDecision = finalDecision;
    }
    private String finalDecision;

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        PolicyViolationRequest.counter = counter;
    }

    public String getDecisionLevel() {
        return decisionLevel;
    }

    public void setDecisionLevel(String decisionLevel) {
        this.decisionLevel = decisionLevel;
    }

    /**
     * 
     */
    public PolicyViolationRequest(
            UserAccount reporter,
            UserAccount targetUser,
            String category,
            String info) {

        super();
        this.reporter = reporter;
        this.targetUser = targetUser;
        this.violationCategory = category;
        this.violationInfo = info;
        this.evidencePaths = new ArrayList<>();
        this.setStatus("Pending");
        this.setId("PVRQ"+counter++);
    }

    /**
     *
     */
    public PolicyViolationRequest(
            UserAccount reporter,
            UserAccount targetUser,
            Listing listing,
            String category,
            String info) {

        this(reporter, targetUser, category, info);
        this.listing = listing;
    }

    public UserAccount getReporter() {
        return reporter;
    }

    public UserAccount getTargetUser() {
        return targetUser;
    }

    public Listing getListing() {
        return listing;
    }

    public String getViolationCategory() {
        return violationCategory;
    }

    public String getViolationInfo() {
        return violationInfo;
    }

    public List<String> getEvidencePaths() {
        return evidencePaths;
    }

    public void addEvidencePath(String path) {
        this.evidencePaths.add(path);
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public String getDisplayCategory() {
        return switch (violationCategory) {
            case "account_issue" -> "Banned account";
            case "listing_issue" -> "Item issues";
            case "minor_dispute" -> "Account Warning";
            default -> violationCategory;
        };
    }

    @Override
    public String toString() {
    
        return "PolicyViolation #" + this.getId();
    }
}
