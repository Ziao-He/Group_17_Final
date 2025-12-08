/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_4;

import basement_class.UserAccount;
import basement_class.WorkRequest;
import common_class.Order;

/**
 *
 * @author yujie-liang
 */
public class OrderReportRequest extends WorkRequest {
    
    private static int counter = 1;
    private static final String PREFIX = "ORR"; 
    private Order order;
    private UserAccount reporter;             
    private UserAccount targetUser;           
    private String violationInfo;             
    private String reason;                    

    public OrderReportRequest(Order order, UserAccount reporter,
                              UserAccount targetUser,
                              String violationInfo) {
        super();
        this.id = generateId();
        this.order = order;
        this.reporter = reporter;
        this.targetUser = targetUser;
        this.violationInfo = violationInfo;
        this.reason = reason;
        this.sender = reporter;        
        this.status = "Pending";
    }

    private static synchronized String generateId() {
        return String.format("%s%03d", PREFIX, counter++);
    }
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
    

    public UserAccount getReporter() {
        return reporter;
    }

    public UserAccount getTargetUser() {
        return targetUser;
    }

    public String getViolationInfo() {
        return violationInfo;
    }

    public void setViolationInfo(String violationInfo) {
        this.violationInfo = violationInfo;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}