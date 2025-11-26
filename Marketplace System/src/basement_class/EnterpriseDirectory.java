/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class;

import java.util.ArrayList;

/**
 *
 * @author bob-h
 */
public class EnterpriseDirectory {
    private ArrayList<Enterprise> enterpriseList;

    public EnterpriseDirectory() {
        enterpriseList = new ArrayList<>();
    }

    public ArrayList<Enterprise> getEnterpriseList() {
        return enterpriseList;
    }

    public Enterprise createAndAddEnterprise(String name, String type) {
        // Logic to create specific Enterprise subclass based on type will go here
        // Example: if (type.equals("Buyer")) return new BuyerEnterprise(name);
        return null; 
    }
    
    public void addEnterprise(Enterprise enterprise) {
        enterpriseList.add(enterprise);
    }
}
