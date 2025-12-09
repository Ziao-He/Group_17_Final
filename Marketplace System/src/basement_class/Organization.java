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
public abstract class Organization {
    private String name;
    private ArrayList<Role> roles;
    private UserAccountDirectory userAccountDirectory;  // Users in this organization
    private WorkRequestDirectory workRequestDirectory;  // Work queue for this organization
    
    public Organization(String name) {
        this.name = name;
        this.roles = new ArrayList<>();
        this.userAccountDirectory = new UserAccountDirectory();
        this.workRequestDirectory = new WorkRequestDirectory();
    }
    
    /**
     * Add a role to this organization
     * @param role The role to add
     */
    public void addRole(Role role) {
        if (!roles.contains(role)) {
            roles.add(role);
        }
    }
    
    /**
     * Check if this organization has a specific role
     * @param roleClass The role class to check
     * @return true if role exists, false otherwise
     */
    public boolean hasRole(Class<? extends Role> roleClass) {
        for (Role role : roles) {
            if (role.getClass().equals(roleClass)) {
                return true;
            }
        }
        return false;
    }
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public ArrayList<Role> getRoles() {
        return roles;
    }
    
    public void setRoles(ArrayList<Role> roles) {
        this.roles = roles;
    }
    
    public UserAccountDirectory getUserAccountDirectory() {
        return userAccountDirectory;
    }
    
    public void setUserAccountDirectory(UserAccountDirectory userAccountDirectory) {
        this.userAccountDirectory = userAccountDirectory;
    }
    
    public WorkRequestDirectory getWorkRequestDirectory() {
        return workRequestDirectory;
    }
    
    public void setWorkRequestDirectory(WorkRequestDirectory workRequestDirectory) {
        this.workRequestDirectory = workRequestDirectory;
    }
}


