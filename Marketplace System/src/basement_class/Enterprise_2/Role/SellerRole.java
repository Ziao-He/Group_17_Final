/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_2.Role;


import UI.Enterprise2.SellerJPanel;
import basement_class.EcoSystem;
import basement_class.Enterprise;
import basement_class.Enterprise_2.Account.SellerAccount;
import basement_class.Organization;
import basement_class.Role;
import basement_class.UserAccount;
import javax.swing.JPanel;
/**
 *
 * @author 心火牧神塞勒斯
 */
public class SellerRole extends Role {

    @Override
    public JPanel createWorkArea(UserAccount userAccount,
                                 Organization organization,
                                 Enterprise enterprise,
                                 EcoSystem system) {
        // TODO: Implement Seller WorkArea Panel
        SellerAccount sellerAccount = (SellerAccount) userAccount;
        
        // Create and return JPanel
        SellerJPanel panel = new SellerJPanel(sellerAccount, organization, enterprise, system);
        return panel;
       
    }

    @Override
    public String getRoleName() {
        return "Seller";
    }
}
