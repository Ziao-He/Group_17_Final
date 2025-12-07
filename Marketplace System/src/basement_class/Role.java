/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class;

/**
 *
 * @author yujie-liang
 */
import javax.swing.JPanel;
public abstract class Role {
    /**
     * Create the work area (UI panel) for this role
     * This method must be implemented by each concrete role class
     * 
     * @param userAccount The user account accessing the work area
     * @param organization The organization this role belongs to
     * @param enterprise The enterprise this organization belongs to
     * @param system The entire ecosystem
     * @return JPanel containing the role-specific UI
     */
    public abstract JPanel createWorkArea(
        UserAccount userAccount, 
        Organization organization, 
        Enterprise enterprise, 
        EcoSystem system
    );
    
    
    /**
     * Get the role name (for display purposes)
     * @return Role name as string
     */
    public abstract String getRoleName();
    
    @Override
    public String toString() {
        return getRoleName();
    }
}

