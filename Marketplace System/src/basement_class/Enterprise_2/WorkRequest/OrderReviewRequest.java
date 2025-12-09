/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_2.WorkRequest;

import basement_class.UserAccount;
import basement_class.WorkRequest;
import common_class.Order;

/**
 *
 * @author 心火牧神塞勒斯
 */
public class OrderReviewRequest extends WorkRequest {

    private Order order;

    // ======================
    // Operation Types
    // ======================
    public static final String OP_BUYER_PLACE = "buyer_place_order";
    public static final String OP_SELLER_ACCEPT = "seller_accept";
    public static final String OP_SELLER_REJECT = "seller_reject";
    public static final String OP_SYSTEM_CANCEL = "system_cancel";

    private String operationType;

    private String message;

    private String processingResult;

    private UserAccount processor;
    private String processReason;

    /**
     * Buyer creates a new order request
     */
    public OrderReviewRequest(
            UserAccount buyer,
            Order order,
            String message) {

        super();
        this.order = order;
        this.operationType = OP_BUYER_PLACE;
        this.message = message;
        this.setSender(buyer);
        this.setStatus("Pending");
    }

    /**
     * Used internally for follow-up operations
     */
    public OrderReviewRequest(
            Order order,
            String operationType,
            String message) {

        super();
        this.order = order;
        this.operationType = operationType;
        this.message = message;
        this.setStatus("Pending");
    }

    // ======================
    // Getters & Setters
    // ======================

    public Order getOrder() {
        return order;
    }

    public String getOperationType() {
        return operationType;
    }

    public String getMessage() {
        return message;
    }

    public UserAccount getProcessor() {
        return processor;
    }

    public void setProcessor(UserAccount processor) {
        this.processor = processor;
    }

    public String getProcessingResult() {
        return processingResult;
    }

    public void setProcessingResult(String processingResult) {
        this.processingResult = processingResult;
    }
    
    public void setProcessReason(String processReason) {
        this.processReason = processReason;
    }

    @Override
    public String toString() {
        return "OrderRequest #" + this.getId();
    }
}
