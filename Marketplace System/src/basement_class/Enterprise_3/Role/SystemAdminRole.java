/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import UI.Enterprise3.SuperAdmin;
import basement_class.EcoSystem;
import basement_class.Enterprise;
import basement_class.Organization;
import basement_class.Role;
import basement_class.UserAccount;
import javax.swing.JPanel;

/**
 *
 * @author Administrator
 */
public class SystemAdminRole extends Role  {

    @Override
    public String getRoleName() {
        return "SystemAdmin";
    }

    @Override
    public JPanel createWorkArea(UserAccount userAccount, 
        Organization organization, 
        Enterprise enterprise, 
        EcoSystem system) {
        
        return new SuperAdmin(system, userAccount);
        
    }


}
