/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_1.WorkRequest;

import basement_class.UserAccount;
import basement_class.WorkRequest;
import common_class.Order;

/**
 *
 * @author bob-h
 */
public class OrderProcessingResultRequest extends WorkRequest {

    private Order order;

    // ======================
    // Operation Types
    // ======================
    public static final String OP_ACCEPT = "seller_accept";
    public static final String OP_REJECT = "seller_reject";

    // Operation type: seller_accept / seller_reject
    private String operationType;

    // Message from buyer when creating order
    private String buyerMessage;

    // Seller's processing result explanation
    private String processingResult;

    // Who processed this order (OrderProcessor)
    private UserAccount processor;

    // Processing reason (why accepted/rejected)
    private String processReason;

    /**
     * Constructor: Seller creates this request after processing an order
     * 
     * @param processor The seller who processed the order
     * @param order The order that was processed
     * @param operationType "seller_accept" or "seller_reject"
     * @param processReason Reason for accept/reject
     */
    public OrderProcessingResultRequest(
            UserAccount processor,
            Order order,
            String operationType,
            String processReason) {

        super();
        this.order = order;
        this.operationType = operationType;
        this.processReason = processReason;
        this.processor = processor;
        
        // Seller is the sender
        this.setSender(processor);
        
        // Initial status is Pending (buyer hasn't viewed yet)
        this.setStatus("Pending");
        
        // Generate processing result message
        if (OP_ACCEPT.equals(operationType)) {
            this.processingResult = "Order accepted: " + processReason;
        } else if (OP_REJECT.equals(operationType)) {
            this.processingResult = "Order rejected: " + processReason;
        }
    }

    /**
     * Alternative constructor with custom result message
     */
    public OrderProcessingResultRequest(
            UserAccount processor,
            Order order,
            String operationType,
            String processReason,
            String customResultMessage) {

        super();
        this.order = order;
        this.operationType = operationType;
        this.processReason = processReason;
        this.processor = processor;
        this.processingResult = customResultMessage;
        
        this.setSender(processor);
        this.setStatus("Pending");
    }

    // ======================
    // Getters & Setters
    // ======================

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getBuyerMessage() {
        return buyerMessage;
    }

    public void setBuyerMessage(String buyerMessage) {
        this.buyerMessage = buyerMessage;
    }

    public String getProcessingResult() {
        return processingResult;
    }

    public void setProcessingResult(String processingResult) {
        this.processingResult = processingResult;
    }

    public UserAccount getProcessor() {
        return processor;
    }

    public void setProcessor(UserAccount processor) {
        this.processor = processor;
    }

    public String getProcessReason() {
        return processReason;
    }

    public void setProcessReason(String processReason) {
        this.processReason = processReason;
    }

    @Override
    public String toString() {
        return "OrderProcessingResult #" + this.getId() + " - " + operationType;
    }
}
