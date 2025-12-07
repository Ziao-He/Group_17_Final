/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_1.Role;

import UI.Enterprise1.ProductSearchJPanel;
import basement_class.*;
import basement_class.Enterprise_1.Account.BuyerAccount;
import javax.swing.JPanel;
import javax.swing.JLabel;

/**
 *
 * @author bob-h
 */
public class ProductSearcherRole extends Role {
    @Override
    public JPanel createWorkArea(UserAccount userAccount, 
                                 Organization organization, 
                                 Enterprise enterprise, 
                                 EcoSystem system) {
        BuyerAccount buyerAccount = (BuyerAccount) userAccount;
        ProductSearchJPanel  panel = new ProductSearchJPanel(buyerAccount, organization, enterprise, system);
        return panel;
    }
    
    @Override
    public String getRoleName() {
        return "Product Searcher";
    }
}
