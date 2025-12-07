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
public class PlatformAdminRole extends Role {
     @Override
    public JPanel createWorkArea(UserAccount account,
                                 Organization organization,
                                 Enterprise enterprise,
                                 EcoSystem business) {

        // ⚠️ 总管理员不属于某个 org，但要能访问全部 org
        return new AdminJPanel(business, account, enterprise, null);
    }

    @Override
    public String getRoleName() {
        return "PlatformAdmin";
    }
}
