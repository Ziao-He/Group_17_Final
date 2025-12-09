/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_3.WorkRequest;

import basement_class.Enterprise_2.Listing;
import basement_class.WorkRequest;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Linyiyang
 */
public class ListingReviewRequest extends WorkRequest{
    private static int counter=1;
    private Listing listing;
    private String operationType;   // "seller_request_up", "seller_request_down", "policy_force_down"
    private String reason;          
    private List<String> evidence;  
    private String rejectionReason; 

    public ListingReviewRequest(Listing listing, String operationType, String reason) {
        super();
        this.listing = listing;
        this.operationType = operationType;
        this.reason = reason;
        this.evidence = new ArrayList<>();
        this.setStatus("Pending");
        this.setId("LRRQ"+counter++);
    }

    public Listing getListing() { return listing; }
    public String getOperationType() { return operationType; }
    public String getReason() { return reason; }

    public void addEvidence(String path){
        evidence.add(path);
    }

    public List<String> getEvidence(){
        return evidence;
    }
    
    public void setRejectionReason(String r){ 
        this.rejectionReason = r;
    }
    
    public String getRejectionReason(){ 
        return rejectionReason; 
    }
}

