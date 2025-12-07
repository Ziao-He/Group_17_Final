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

    // 操作类型（方便扩展）
    // buyer_place_order / seller_accept / seller_reject / system_cancel
    private String operationType;

    // Buyer / Processor 备注
    private String message;

    // OrderProcessor 的处理结果说明
    private String processingResult;

    /**
     * Buyer creates a new order request
     */
    public OrderReviewRequest(
            UserAccount buyer,
            Order order,
            String message) {

        super();
        this.order = order;
        this.operationType = "buyer_place_order";
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

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProcessingResult() {
        return processingResult;
    }

    public void setProcessingResult(String processingResult) {
        this.processingResult = processingResult;
    }

    @Override
    public String toString() {
        return "OrderRequest #" + this.getId();
    }
}
