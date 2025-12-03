/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_1.Enterprise;

import basement_class.Enterprise;
import basement_class.Enterprise_1.Organization.ShoppingServicesOrganization;
import basement_class.Enterprise_1.Organization.OrderManagementOrganization;


/**
 *
 * @author bob-h
 */
public class BuyerOperationsEnterprise extends Enterprise {
    
    public BuyerOperationsEnterprise(String name) {
        super(name);
        
        // Add organizations
        this.addOrganization(new ShoppingServicesOrganization("Shopping Services"));
        this.addOrganization(new OrderManagementOrganization("Order Management"));
    }
}
