/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_2.Organization;

import basement_class.Enterprise_2.Role.OrderProcessorRole;
import basement_class.Organization;
import basement_class.Enterprise_2.Role.SellerRole;
import basement_class.Role;
/**
 *
 * @author 心火牧神塞勒斯
 */

public class SellerOrganization extends Organization {

    public SellerOrganization() {
        super("Seller Organization");
        
        // Add primary seller role (Role 2.1.1)
        this.addRole(new SellerRole());
        
        // Add specialized order processor role (Role 2.2.1)
        this.addRole(new OrderProcessorRole());
    }
    
    

    
}
