/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_3.Enterprise;

import basement_class.Enterprise;
import basement_class.Enterprise_3.Organization.ContentControlOrganization;
import basement_class.Enterprise_3.Organization.UserControlOrganization;


/**
 *
 * @author Linyiyang
 */
public class PlatformManagementEnterprise extends Enterprise {
    
    public PlatformManagementEnterprise() {
        super("Platform Management");
        this.addOrganization(new UserControlOrganization());
        this.addOrganization(new ContentControlOrganization());
    }
    
    

}
