/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_1.Role;

import basement_class.*;
import basement_class.Enterprise_1.Account.BuyerAccount;
import javax.swing.JPanel;
import javax.swing.JLabel;

/**
 *
 * @author bob-h
 */
public class BuyerRole extends Role {
    
    @Override
    public JPanel createWorkArea(UserAccount userAccount, 
                                 Organization organization, 
                                 Enterprise enterprise, 
                                 EcoSystem system) {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Buyer Dashboard"));
        return null;
    }
    
    /**
     * Get the role name for display purposes
     * @return "Buyer"
     */
    @Override
    public String getRoleName() {
        return "Buyer";
    }
}
