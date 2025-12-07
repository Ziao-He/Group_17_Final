/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_3.Role;

import UI.Enterprise3.AdminJPanel;
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
public class ContentModeratorRoleOrg extends Role{
  @Override
    public JPanel createWorkArea(UserAccount account,
                                 Organization organization,
                                 Enterprise enterprise,
                                 EcoSystem business) {

        return new AdminJPanel(business, account, enterprise, organization);
    }

    @Override
    public String getRoleName() {
        return "ContentModerator";
    }  
}
