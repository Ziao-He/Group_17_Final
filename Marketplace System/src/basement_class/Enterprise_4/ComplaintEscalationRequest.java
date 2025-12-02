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

    private Complaint complaint;
    private String escalationReason;

    public ComplaintEscalationRequest(Complaint complaint,
                                      UserAccount currentHandler,
                                      String escalationReason) {
        super();
        this.complaint = complaint;
        this.sender = currentHandler; //me
        this.escalationReason = escalationReason;
        this.setStatus("Pending");
    }

    public Complaint getComplaint() {
        return complaint;
    }

    public String getEscalationReason() {
        return escalationReason;
    }
}