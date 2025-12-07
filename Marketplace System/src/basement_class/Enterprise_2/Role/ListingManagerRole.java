/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_2.Role;


import UI.Enterprise2.ListingManagerJPanel;
import basement_class.EcoSystem;
import basement_class.Enterprise;
import basement_class.Enterprise_1.Account.BuyerAccount;
import basement_class.Enterprise_2.Account.ListingManagerAccount;
import basement_class.Organization;
import basement_class.Role;
import basement_class.UserAccount;
import javax.swing.JPanel;
/**
 *
 * @author 心火牧神塞勒斯
 */
public class ListingManagerRole extends Role {

    @Override
    public JPanel createWorkArea(UserAccount userAccount,
                                 Organization organization,
                                 Enterprise enterprise,
                                 EcoSystem system) {
        // TODO: Implement Listing Manager WorkArea Panel
        ListingManagerAccount listingManageAccount = (ListingManagerAccount) userAccount;
        
        // Create and return BuyerJPanel
        ListingManagerJPanel panel = new ListingManagerJPanel(listingManageAccount, organization, enterprise, system);
        return panel;
       
    }

    @Override
    public String getRoleName() {
        return "Listing Manager";
    }
}
