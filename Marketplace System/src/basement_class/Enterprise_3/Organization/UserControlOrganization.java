/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_3.Organization;

import basement_class.Enterprise_3.Role.AccountAdminRole;
import basement_class.Enterprise_3.Role.RegistrationReviewerRole;
import basement_class.Organization;

/**
 *
 * @author Administrator
 */
public class UserControlOrganization extends Organization {
    
    public UserControlOrganization(){
        super("User Control");
        this.addRole(new AccountAdminRole());
        this.addRole(new RegistrationReviewerRole());
    }
}
