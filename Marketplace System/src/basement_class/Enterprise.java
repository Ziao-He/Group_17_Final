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
public abstract class Enterprise {
    private String name;
    private ArrayList<Organization> organizations;
    
    public Enterprise(String name) {
        this.name = name;
        this.organizations = new ArrayList<>();
    }
    
    /**
     * Add an organization to this enterprise
     * @param organization The organization to add
     */
    public void addOrganization(Organization organization) {
        if (!organizations.contains(organization)) {
            organizations.add(organization);
        }
    }
    
    /**
     * Find an organization by name
     * @param name Organization name
     * @return The organization, or null if not found
     */
    public Organization getOrganizationByName(String name) {
        for (Organization organization : organizations) {
            if (organization.getName().equals(name)) {
                return organization;
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
    
    public ArrayList<Organization> getOrganizations() {
        return organizations;
    }
    
    public void setOrganizations(ArrayList<Organization> organizations) {
        this.organizations = organizations;
    }
}
