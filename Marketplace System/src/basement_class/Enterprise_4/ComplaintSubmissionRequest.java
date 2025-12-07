/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_4;

import basement_class.WorkRequest;

/**
 *
 * @author yujie-liang
 */
public class ComplaintSubmissionRequest extends WorkRequest {
    
    private static int counter = 1;
    private static final String PREFIX = "CSR"; 
    private Complaint complaint;

    public ComplaintSubmissionRequest(Complaint complaint) {
        super();
        this.id = generateId();
        this.complaint = complaint;
        this.sender = complaint.getComplainant();
        this.setStatus("Pending");
    }
    
    private static synchronized String generateId() {
        return String.format("%s%03d", PREFIX, counter++);
    }

    public Complaint getComplaint() {
        return complaint;
    }
}