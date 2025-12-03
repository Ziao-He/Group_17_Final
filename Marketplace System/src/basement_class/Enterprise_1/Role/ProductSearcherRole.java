/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_1.Role;

import basement_class.*;
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
        JPanel panel = new JPanel();
        panel.add(new JLabel("Product Searcher Dashboard"));
        return panel;
    }
    
    @Override
    public String getRoleName() {
        return "Product Searcher";
    }
}
