/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.main;

import basement_class.*;
import basement_class.Enterprise_1.Enterprise.BuyerOperationsEnterprise;
import basement_class.Enterprise_1.Account.BuyerAccount;
import basement_class.Enterprise_1.Organization.*;
import basement_class.Enterprise_1.Role.*;

/**
 *
 * @author bob-h
 */
public class Enterprise1Initializer {
    
    /**
     * Initialize Enterprise 1: Buyer Operations
     * 
     * @param system EcoSystem instance
     * @param network Network to add enterprise to
     */
    public static void initialize(EcoSystem system, Network network) {
        System.out.println("Initializing Enterprise 1: Buyer Operations...");
        
        // Create the enterprise (organizations and roles are created in constructor)
        BuyerOperationsEnterprise enterprise = new BuyerOperationsEnterprise("Buyer Operations");
        network.addEnterprise(enterprise);
        
        // Get organizations
        ShoppingServicesOrganization shoppingOrg = 
            (ShoppingServicesOrganization) enterprise.getOrganizationByName("Shopping Services");
        OrderManagementOrganization orderOrg = 
            (OrderManagementOrganization) enterprise.getOrganizationByName("Order Management");
        
        // Log enterprise structure
        System.out.println("  Created Enterprise: " + enterprise.getName());
        System.out.println("  Organizations:");
        for (Organization org : enterprise.getOrganizations()) {
            System.out.println("    • " + org.getName() + " (" + org.getRoles().size() + " roles)");
            for (Role role : org.getRoles()) {
                System.out.println("      - " + role.getRoleName());
            }
        }
        
        // Create test data
        createTestAccounts(system, shoppingOrg, orderOrg);
    }
    
    /**
     * Create test buyer accounts
     */
    private static void createTestAccounts(EcoSystem system, 
                                          ShoppingServicesOrganization shoppingOrg,
                                          OrderManagementOrganization orderOrg) {
        System.out.println("  Creating test accounts...");
        
        // Test Account 1: Standard Buyer
        BuyerAccount buyer1 = createBuyerAccount(
            "BUYER-001", "buyer1", "buyer1@university.edu", 
            "123-456-7890", "ORG-SHOPPING-001", new BuyerRole()
        );
        addAccount(buyer1, shoppingOrg, system);
        System.out.println("    • buyer1 (BuyerRole) - password: password123");
        
        // Test Account 2: Product Searcher
        BuyerAccount buyer2 = createBuyerAccount(
            "BUYER-002", "searcher1", "searcher1@university.edu",
            "123-456-7891", "ORG-SHOPPING-001", new ProductSearcherRole()
        );
        buyer2.addToFavorites("PROD-001");
        buyer2.addToFavorites("PROD-002");
        addAccount(buyer2, shoppingOrg, system);
        System.out.println("    • searcher1 (ProductSearcherRole) - password: password123");
        
        // Test Account 3: Order Tracker
        BuyerAccount buyer3 = createBuyerAccount(
            "BUYER-003", "tracker1", "tracker1@university.edu",
            "123-456-7892", "ORG-ORDER-001", new OrderTrackerRole()
        );
        buyer3.addOrder("ORDER-001");
        buyer3.addOrder("ORDER-002");
        buyer3.setCompletedPurchases(1);
        buyer3.setPoints(150);
        addAccount(buyer3, orderOrg, system);
        System.out.println("    • tracker1 (OrderTrackerRole) - password: password123");
    }
    
    /**
     * Helper: Create a buyer account with common fields
     */
    private static BuyerAccount createBuyerAccount(String userId, String username, 
                                                   String email, String phone,
                                                   String orgId, Role role) {
        BuyerAccount account = new BuyerAccount();
        account.setUserId(userId);
        account.setUsername(username);
        account.setEmail(email);
        account.setPasswordHash("password123"); // TODO: Implement proper hashing
        account.setPhoneNumber(phone);
        account.setStatus("ACTIVE");
        account.setOrganizationId(orgId);
        account.setRole(role);
        return account;
    }
    
    /**
     * Helper: Add account to organization and system
     */
    private static void addAccount(BuyerAccount account, Organization org, EcoSystem system) {
        org.getUserAccountDirectory().addUserAccount(account);
        system.getUserAccountDirectory().addUserAccount(account);
    }
}
