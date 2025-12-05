/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class;

import basement_class.Enterprise_2.Listing;
import java.util.ArrayList;

/**
 *
 * @author yujie-liang
 */
public class EcoSystem {
    private ArrayList<Network> networks;
    private UserAccountDirectory userAccountDirectory;
    private WorkRequestDirectory workRequestDirectory;
    
    // Singleton instance
    private static EcoSystem instance;
    
    // Private constructor for Singleton
    private EcoSystem() {
        this.networks = new ArrayList<>();
        this.userAccountDirectory = new UserAccountDirectory();
        this.workRequestDirectory = new WorkRequestDirectory();
    }
    
    /**
     * Get the singleton instance of EcoSystem
     * @return The single EcoSystem instance
     */
    public static EcoSystem getInstance() {
        if (instance == null) {
            instance = new EcoSystem();
        }
        return instance;
    }
    
    /**
     * Create and add a new network to the ecosystem
     * @param name Network name
     * @return The created Network
     */
    public Network createNetwork(String name) {
        Network network = new Network(name);
        networks.add(network);
        return network;
    }
    
    // Getters and Setters
    public ArrayList<Network> getNetworks() {
        return networks;
    }
    
    public void setNetworks(ArrayList<Network> networks) {
        this.networks = networks;
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

    public Listing findListingById(String id) {
    for (Network n : networks) {
        for (Enterprise e : n.getEnterprises()) {
            for (Organization o : e.getOrganizations()) {
                for (Listing l : o.g) {
                    if (l.getId().equals(id))
                        return l;
                }
            }
        }
    }
    return null;
}
}