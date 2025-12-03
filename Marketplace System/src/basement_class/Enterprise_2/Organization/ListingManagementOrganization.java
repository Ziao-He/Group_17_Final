/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_2.Organization;

import basement_class.Organization;
import basement_class.Enterprise_2.Role.ListingManagerRole;
/**
 *
 * @author 心火牧神塞勒斯
 */
public class ListingManagementOrganization extends Organization {

    public ListingManagementOrganization() {
        super("Listing Management Organization");
        this.addRole(new ListingManagerRole());
    }
}
