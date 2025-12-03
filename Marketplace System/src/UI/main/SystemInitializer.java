/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.main;

import basement_class.*;
import basement_class.Enterprise_1.Account.BuyerAccount;
import basement_class.Enterprise_1.Enterprise.BuyerOperationsEnterprise;
import basement_class.Enterprise_1.Role.*;
import basement_class.Enterprise_1.Organization.*;


/**
 *
 * @author bob-h
 */
public class SystemInitializer {
    
    /**
     * Initialize the entire system and return the EcoSystem
     * Call this method once at application startup
     * 
     * @return Initialized EcoSystem instance
     */
    public static EcoSystem initialize() {
        System.out.println("=== System Initialization Started ===");
        
        // Get ecosystem singleton instance
        EcoSystem system = EcoSystem.getInstance();
        
        // Create network
        Network campusNetwork = system.createNetwork("Campus Marketplace Network");
        
        // Create all enterprises
        createEnterprises(campusNetwork);
        
        // Populate test data
        populateTestData(system, campusNetwork);
        
        System.out.println("=== System Initialization Completed ===");
        System.out.println("Total networks: " + system.getNetworks().size());
        System.out.println("Total enterprises: " + campusNetwork.getEnterprises().size());
        System.out.println("Total users: " + system.getUserAccountDirectory().size());
        System.out.println();
        
        return system;
    }
    
    /**
     * Create all enterprises and their organizations
     */
    private static void createEnterprises(Network campusNetwork) {
        System.out.println("\n--- Creating Enterprises ---");
        
        // Enterprise 1: Buyer Operations
        createBuyerOperationsEnterprise(campusNetwork);
        
        // TODO: Enterprise 2: Seller Operations (to be implemented by team member 2)
        // createSellerOperationsEnterprise(campusNetwork);
        
        // TODO: Enterprise 3: Platform Management (to be implemented by team member 3)
        // createPlatformManagementEnterprise(campusNetwork);
        
        // TODO: Enterprise 4: Help Center (to be implemented by team member 4)
        // createHelpCenterEnterprise(campusNetwork);
    }
    
    /**
     * Create Enterprise 1: Buyer Operations with its organizations and roles
     */
    private static void createBuyerOperationsEnterprise(Network campusNetwork) {
        BuyerOperationsEnterprise buyerEnterprise = 
            new BuyerOperationsEnterprise("Buyer Operations");
        campusNetwork.addEnterprise(buyerEnterprise);
        
        System.out.println("✓ Created Enterprise: Buyer Operations");
        System.out.println("  - Organizations: " + buyerEnterprise.getOrganizations().size());
        
        // Verify organizations
        for (Organization org : buyerEnterprise.getOrganizations()) {
            System.out.println("    • " + org.getName() + " (Roles: " + org.getRoles().size() + ")");
        }
    }
    
    /**
     * Populate system with test data including users, products, and orders
     */
    private static void populateTestData(EcoSystem system, Network campusNetwork) {
        System.out.println("\n--- Populating Test Data ---");
        
        // Create test buyer accounts
        createTestBuyerAccounts(system, campusNetwork);
        
        // TODO: Create test seller accounts
        // createTestSellerAccounts(system, campusNetwork);
        
        // TODO: Create test products
        // createTestProducts(system);
        
        // TODO: Create test orders
        // createTestOrders(system);
        
        // TODO: Create test messages
        // createTestMessages(system);
    }
    
    /**
     * Create test buyer accounts for all three buyer roles
     */
    private static void createTestBuyerAccounts(EcoSystem system, Network campusNetwork) {
        // Get Buyer Operations Enterprise
        BuyerOperationsEnterprise buyerEnterprise = 
            (BuyerOperationsEnterprise) campusNetwork.getEnterpriseByName("Buyer Operations");
        
        if (buyerEnterprise == null) {
            System.err.println("✗ Error: Buyer Operations Enterprise not found");
            return;
        }
        
        // Get organizations
        ShoppingServicesOrganization shoppingOrg = 
            (ShoppingServicesOrganization) buyerEnterprise.getOrganizationByName("Shopping Services");
        OrderManagementOrganization orderOrg = 
            (OrderManagementOrganization) buyerEnterprise.getOrganizationByName("Order Management");
        
        if (shoppingOrg == null || orderOrg == null) {
            System.err.println("✗ Error: Organizations not found");
            return;
        }
        
        // Create test accounts
        createBuyer1(shoppingOrg, system);
        createBuyer2(shoppingOrg, system);
        createBuyer3(orderOrg, system);
        
        System.out.println("✓ Created 3 test buyer accounts");
    }
    
    /**
     * Create Test Buyer 1 - Standard Buyer Role
     */
    private static void createBuyer1(ShoppingServicesOrganization shoppingOrg, EcoSystem system) {
        BuyerAccount buyer = new BuyerAccount();
        buyer.setUserId("BUYER-001");
        buyer.setUsername("buyer1");
        buyer.setEmail("buyer1@university.edu");
        buyer.setPasswordHash("password123"); // TODO: Implement password hashing
        buyer.setPhoneNumber("123-456-7890");
        buyer.setStatus("ACTIVE");
        buyer.setOrganizationId("ORG-SHOPPING-001");
        buyer.setRole(new BuyerRole());
        
        // Add to organization and system
        shoppingOrg.getUserAccountDirectory().addUserAccount(buyer);
        system.getUserAccountDirectory().addUserAccount(buyer);
        
        System.out.println("  • buyer1 (BuyerRole) - Password: password123");
    }
    
    /**
     * Create Test Buyer 2 - Product Searcher Role
     */
    private static void createBuyer2(ShoppingServicesOrganization shoppingOrg, EcoSystem system) {
        BuyerAccount buyer = new BuyerAccount();
        buyer.setUserId("BUYER-002");
        buyer.setUsername("searcher1");
        buyer.setEmail("searcher1@university.edu");
        buyer.setPasswordHash("password123");
        buyer.setPhoneNumber("123-456-7891");
        buyer.setStatus("ACTIVE");
        buyer.setOrganizationId("ORG-SHOPPING-001");
        buyer.setRole(new ProductSearcherRole());
        
        // Add some sample favorites
        buyer.addToFavorites("PROD-001");
        buyer.addToFavorites("PROD-002");
        
        shoppingOrg.getUserAccountDirectory().addUserAccount(buyer);
        system.getUserAccountDirectory().addUserAccount(buyer);
        
        System.out.println("  • searcher1 (ProductSearcherRole) - Password: password123");
    }
    
    /**
     * Create Test Buyer 3 - Order Tracker Role
     */
    private static void createBuyer3(OrderManagementOrganization orderOrg, EcoSystem system) {
        BuyerAccount buyer = new BuyerAccount();
        buyer.setUserId("BUYER-003");
        buyer.setUsername("tracker1");
        buyer.setEmail("tracker1@university.edu");
        buyer.setPasswordHash("password123");
        buyer.setPhoneNumber("123-456-7892");
        buyer.setStatus("ACTIVE");
        buyer.setOrganizationId("ORG-ORDER-001");
        buyer.setRole(new OrderTrackerRole());
        
        // Add sample order data
        buyer.addOrder("ORDER-001");
        buyer.addOrder("ORDER-002");
        buyer.addOrder("ORDER-003");
        buyer.setCompletedPurchases(2);
        buyer.setPoints(200);
        
        orderOrg.getUserAccountDirectory().addUserAccount(buyer);
        system.getUserAccountDirectory().addUserAccount(buyer);
        
        System.out.println("  • tracker1 (OrderTrackerRole) - Password: password123");
    }
    
    /**
     * Create a suspended test account for testing account status validation
     */
    private static void createSuspendedAccount(ShoppingServicesOrganization shoppingOrg, EcoSystem system) {
        BuyerAccount buyer = new BuyerAccount();
        buyer.setUserId("BUYER-999");
        buyer.setUsername("suspended1");
        buyer.setEmail("suspended1@university.edu");
        buyer.setPasswordHash("password123");
        buyer.setStatus("SUSPENDED"); // This account cannot login
        buyer.setRole(new BuyerRole());
        
        shoppingOrg.getUserAccountDirectory().addUserAccount(buyer);
        system.getUserAccountDirectory().addUserAccount(buyer);
        
        System.out.println("  • suspended1 (SUSPENDED) - For testing purposes");
    }
}