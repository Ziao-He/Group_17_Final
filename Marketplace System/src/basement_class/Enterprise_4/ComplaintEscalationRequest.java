/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_4;

import basement_class.UserAccount;
import basement_class.WorkRequest;

/**
 *
 * @author yujie-liang
 */
public class ComplaintEscalationRequest extends WorkRequest {

    private static int counter = 1;
    private static final String PREFIX = "CER"; 
    private Complaint complaint;
    private String escalationReason;

    public ComplaintEscalationRequest(Complaint complaint,
                                      UserAccount currentHandler,
                                      String escalationReason) {
        super();
        this.id = generateId();
        this.complaint = complaint;
        this.sender = currentHandler; //me
        this.escalationReason = escalationReason;
        this.status = this.complaint.getStatus();
    }
    
    private static synchronized String generateId() {
        return String.format("%s%03d", PREFIX, counter++);
    }

    public Complaint getComplaint() {
        return complaint;
    }

    public String getEscalationReason() {
        return escalationReason;
    }
}