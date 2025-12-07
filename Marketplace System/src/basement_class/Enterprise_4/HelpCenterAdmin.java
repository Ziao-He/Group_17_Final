/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_4;

import basement_class.EcoSystem;
import basement_class.Enterprise;
import basement_class.Organization;
import basement_class.Role;
import basement_class.UserAccount;
import javax.swing.JPanel;

/**
 *
 * @author yujie-liang
 */
public class HelpCenterAdmin extends Role{
    @Override
    public JPanel createWorkArea(UserAccount userAccount, Organization organization, Enterprise enterprise, EcoSystem system){
        return null;
    }
    
    @Override
    public String getRoleName() {
        return "Help Center Admin";
    }
}
