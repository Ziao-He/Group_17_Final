/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class;

import java.util.ArrayList;

/**
 *
 * @author yujie-liang
 */
public class Network {
    private String name;
    private ArrayList<Enterprise> enterprises;
    
    public Network(String name) {
        this.name = name;
        this.enterprises = new ArrayList<>();
    }
    
    /**
     * Add an enterprise to this network
     * @param enterprise The enterprise to add
     */
    public void addEnterprise(Enterprise enterprise) {
        if (!enterprises.contains(enterprise)) {
            enterprises.add(enterprise);
        }
    }
    
    /**
     * Find an enterprise by name
     * @param name Enterprise name
     * @return The enterprise, or null if not found
     */
    public Enterprise getEnterpriseByName(String name) {
        for (Enterprise enterprise : enterprises) {
            if (enterprise.getName().equals(name)) {
                return enterprise;
            }
        }
        return null;
    }
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public ArrayList<Enterprise> getEnterprises() {
        return enterprises;
    }
    
    public void setEnterprises(ArrayList<Enterprise> enterprises) {
        this.enterprises = enterprises;
    }
}
