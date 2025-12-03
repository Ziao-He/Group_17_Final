/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_1.Organization;

import basement_class.Organization;
import basement_class.Enterprise_1.Role.OrderTrackerRole;

/**
 *
 * @author bob-h
 */
public class OrderManagementOrganization extends Organization {
    
    public OrderManagementOrganization(String name) {
        super(name);
        
        // Add role
        this.addRole(new OrderTrackerRole());
    }
}
