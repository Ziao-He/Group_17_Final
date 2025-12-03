/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.main;

import basement_class.*;


/**
 *
 * @author bob-h
 */
public class SystemInitializer {
    
    /**
     * Initialize the entire Campus Marketplace System
     * 
     * @return Initialized EcoSystem instance
     */
    public static EcoSystem initialize() {
        System.out.println("=== Campus Marketplace System Initialization ===\n");
        
        // Get EcoSystem singleton
        EcoSystem system = EcoSystem.getInstance();
        
        // Create Campus Marketplace Network
        Network campusNetwork = system.createNetwork("Campus Marketplace Network");
        System.out.println("✓ Created Network: " + campusNetwork.getName() + "\n");
        
        // Initialize all enterprises
        System.out.println("--- Initializing Enterprises ---\n");
        
        initializeEnterprise1(system, campusNetwork);
        initializeEnterprise2(system, campusNetwork);
        initializeEnterprise3(system, campusNetwork);
        initializeEnterprise4(system, campusNetwork);
        
        // Print summary
        printSummary(system, campusNetwork);
        
        System.out.println("\n=== System Ready ===\n");
        
        return system;
    }
    
    /**
     * Initialize Enterprise 1: Buyer Operations
     */
    private static void initializeEnterprise1(EcoSystem system, Network network) {
        try {
            Enterprise1Initializer.initialize(system, network);
            System.out.println("✓ Enterprise 1 initialized\n");
        } catch (Exception e) {
            System.err.println("✗ Enterprise 1 failed: " + e.getMessage());
        }
    }
    
    /**
     * Initialize Enterprise 2: Seller Operations
     */
    private static void initializeEnterprise2(EcoSystem system, Network network) {
        try {
            Enterprise2Initializer.initialize(system, network);
            System.out.println("✓ Enterprise 2 initialized\n");
        } catch (Exception e) {
            System.err.println("✗ Enterprise 2 failed: " + e.getMessage());
        }
    }
    
    /**
     * Initialize Enterprise 3: Platform Management
     */
    private static void initializeEnterprise3(EcoSystem system, Network network) {
        try {
            Enterprise3Initializer.initialize(system, network);
            System.out.println("✓ Enterprise 3 initialized\n");
        } catch (Exception e) {
            System.err.println("✗ Enterprise 3 failed: " + e.getMessage());
        }
    }
    
    /**
     * Initialize Enterprise 4: Help Center
     */
    private static void initializeEnterprise4(EcoSystem system, Network network) {
        try {
            Enterprise4Initializer.initialize(system, network);
            System.out.println("✓ Enterprise 4 initialized\n");
        } catch (Exception e) {
            System.err.println("✗ Enterprise 4 failed: " + e.getMessage());
        }
    }
    
    /**
     * Print system summary
     */
    private static void printSummary(EcoSystem system, Network network) {
        System.out.println("\n--- System Summary ---");
        System.out.println("Networks: " + system.getNetworks().size());
        System.out.println("Enterprises: " + network.getEnterprises().size());
        
        int totalOrgs = 0;
        int totalRoles = 0;
        for (Enterprise enterprise : network.getEnterprises()) {
            totalOrgs += enterprise.getOrganizations().size();
            for (Organization org : enterprise.getOrganizations()) {
                totalRoles += org.getRoles().size();
            }
        }
        
        System.out.println("Organizations: " + totalOrgs);
        System.out.println("Roles: " + totalRoles);
        System.out.println("Users: " + system.getUserAccountDirectory().size());
    }
}