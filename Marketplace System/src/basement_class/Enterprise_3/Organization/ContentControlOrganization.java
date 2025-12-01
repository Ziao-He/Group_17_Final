/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_3.Organization;

import basement_class.Enterprise_3.Role.ContentModeratorRole;
import basement_class.Enterprise_3.Role.PolicyEnforcerRole;
import basement_class.Organization;



/**
 *
 * @author Linyiyang
 */
public class ContentControlOrganization extends Organization {
        
    public ContentControlOrganization(){
        super("User Control");
        this.addRole(new ContentModeratorRole());
        this.addRole(new PolicyEnforcerRole());
    }
}
