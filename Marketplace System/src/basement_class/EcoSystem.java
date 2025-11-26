/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class;

import java.util.ArrayList;

/**
 *
 * @author yujie-liang
 * EcoSystem: The "Root" Organization of the entire system.
 * Corresponds to the "Campus Trading Ecosystem" in the proposal.
 * It manages Networks and System-level UserAccounts.
 */
public class EcoSystem extends Organization {
    
    private static EcoSystem business;
    private ArrayList<Network> networkList;

    // Singleton Pattern: Ensure only one EcoSystem exists
    public static EcoSystem getInstance() {
        if (business == null) {
            business = new EcoSystem();
        }
        return business;
    }

    private EcoSystem() {
        super("Campus Marketplace Ecosystem"); // Name from Proposal
        networkList = new ArrayList<>();
    }

    public ArrayList<Network> getNetworkList() {
        return networkList;
    }

    public void setNetworkList(ArrayList<Network> networkList) {
        this.networkList = networkList;
    }

    public Network createAndAddNetwork(String name) {
        Network network = new Network(name);
        networkList.add(network);
        return network;
    }

    /**
     * Supports System Admin roles.
     * @return List of roles supported by the EcoSystem (e.g., SystemAdmin)
     */
    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList<>();
        // In the future, add new SystemAdminRole() here
        // roles.add(new SystemAdminRole());
        return roles;
    }

    /**
     * Check if a username is unique across the ENTIRE system (Networks -> Enterprises -> Organizations)
     * This is a critical function for the "Biggest Organization".
     */
    public boolean checkIfUserIsUnique(String userName) {
        // 1. Check EcoSystem's own user directory (System Admins)
        if (!this.getUserAccountDirectory().checkIfUsernameIsUnique(userName)) {
            return false;
        }

        // 2. Iterate through all Networks
        for (Network network : networkList) {
            // 3. Iterate through all Enterprises in the Network
            for (Enterprise enterprise : network.getEnterpriseDirectory()) {
                if (!enterprise.getUserAccountDirectory().checkIfUsernameIsUnique(userName)) {
                    return false;
                }
                
                // 4. Iterate through all Organizations in the Enterprise
                for (Organization organization : enterprise.getOrganizationDirectory().getOrganizationList()) {
                    if (!organization.getUserAccountDirectory().checkIfUsernameIsUnique(userName)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}