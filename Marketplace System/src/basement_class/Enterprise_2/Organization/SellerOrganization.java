/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_2.Organization;

import basement_class.Organization;
import basement_class.Enterprise_2.Role.SellerRole;
/**
 *
 * @author 心火牧神塞勒斯
 */
public class SellerOrganization extends Organization {

    public SellerOrganization() {
        super("Seller Organization");
        this.addRole(new SellerRole());
    }
}
