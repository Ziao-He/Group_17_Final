/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_2.Role;



import UI.Enterprise2.OrderProcessorJPanel;
import basement_class.EcoSystem;
import basement_class.Enterprise;
import basement_class.Enterprise_2.Account.SellerAccount;
import basement_class.Organization;
import basement_class.Role;
import basement_class.UserAccount;
import javax.swing.JPanel;
/**
 *
 * @author 心火牧神塞勒斯
 */
public class OrderProcessorRole extends Role {

    @Override
    public JPanel createWorkArea(UserAccount userAccount,
                                 Organization organization,
                                 Enterprise enterprise,
                                 EcoSystem system) {
        // TODO: Implement Order Processor WorkArea Panel
        SellerAccount sellerAccount = (SellerAccount) userAccount;
        
        // Create and return JPanel
        OrderProcessorJPanel panel = new OrderProcessorJPanel(sellerAccount, organization, enterprise, system);
        return panel;
    }

    @Override
    public String getRoleName() {
        return "Order Processor";
    }
    
    /**
     * Get role description for display purposes
     */
    public String getRoleDescription() {
        return "Specialized role for efficiently processing incoming orders. " +
               "Can view order queue, batch process orders, and track completion rates.";
    }
    
    /**
     * Check if this role has permission to perform order processing
     * This can be used for role-based access control
     */
    public boolean canProcessOrders() {
        return true;
    }
    
    /**
     * Check if this role has permission for batch operations
     */
    public boolean canPerformBatchOperations() {
        return true;
    }
}
