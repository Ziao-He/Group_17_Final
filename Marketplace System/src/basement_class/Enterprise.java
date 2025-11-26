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
public abstract class Enterprise extends Organization {
    
    private String enterpriseType;
    private OrganizationDirectory organizationDirectory; // Use a Directory class instead of raw ArrayList

    public Enterprise(String name, String type) {
        super(name);
        this.enterpriseType = type;
        this.organizationDirectory = new OrganizationDirectory(); // Init
    }

    public OrganizationDirectory getOrganizationDirectory() {
        return organizationDirectory;
    }
}
