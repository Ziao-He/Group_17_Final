/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_1.Account;

import basement_class.Profile;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bob-h
 */
public class BuyerProfile extends Profile {
    
    // Buyer preferences and settings
    private List<String> preferredCategories;
    private double maxBudget;
    private String preferredLocation;
    private String preferredPaymentMethod;
    
    /**
     * Constructor
     */
    public BuyerProfile() {
        super();
        this.preferredCategories = new ArrayList<>();
        this.maxBudget = 0.0;
        this.preferredPaymentMethod = "";
        this.preferredLocation = "";
    }
    
    @Override
    public String getRole() {
        return "Buyer";
    }
    
    /**
     * Add preferred category
     */
    public void addPreferredCategory(String category) {
        if (!preferredCategories.contains(category)) {
            preferredCategories.add(category);
        }
    }
    
    /**
     * Remove preferred category
     */
    public void removePreferredCategory(String category) {
        preferredCategories.remove(category);
    }
    
    // Getters and Setters
    
    public List<String> getPreferredCategories() {
        return preferredCategories;
    }
    
    public void setPreferredCategories(List<String> preferredCategories) {
        this.preferredCategories = preferredCategories;
    }
    
    public double getMaxBudget() {
        return maxBudget;
    }
    
    public void setMaxBudget(double maxBudget) {
        this.maxBudget = maxBudget;
    }
    
    public String getPreferredLocation() {
        return preferredLocation;
    }
    
    public void setPreferredLocation(String preferredLocation) {
        this.preferredLocation = preferredLocation;
    }

    public String getPreferredPaymentMethod() {
        return preferredPaymentMethod;
    }

    public void setPreferredPaymentMethod(String preferredPaymentMethod) {
        this.preferredPaymentMethod = preferredPaymentMethod;
    }
}
