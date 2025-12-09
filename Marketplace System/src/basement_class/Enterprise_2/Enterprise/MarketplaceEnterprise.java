/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_2.Enterprise;

import basement_class.Enterprise;
import basement_class.Enterprise_2.Organization.SellerOrganization;
import basement_class.Enterprise_2.Organization.OrderManagementOrganization;
/**
 *
 * @author 心火牧神塞勒斯
 */
public class MarketplaceEnterprise extends Enterprise {
    
    public MarketplaceEnterprise() {
        super("Marketplace Enterprise");
        
        // Add organizations
        this.addOrganization(new SellerOrganization());
        this.addOrganization(new OrderManagementOrganization());
    }
}