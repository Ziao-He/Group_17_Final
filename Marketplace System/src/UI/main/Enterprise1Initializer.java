/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.main;

import basement_class.*;
import basement_class.Enterprise_1.Enterprise.BuyerOperationsEnterprise;
import basement_class.Enterprise_1.Account.BuyerAccount;
import basement_class.Enterprise_1.Account.BuyerProfile;
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
    /**
     * Initialize Enterprise 1: Buyer Operations
     */
    public static void initialize(EcoSystem system, Network network) {
        System.out.println("Initializing Enterprise 1: Buyer Operations...");
        
        // Create enterprise
        BuyerOperationsEnterprise enterprise = new BuyerOperationsEnterprise("Buyer Operations");
        network.addEnterprise(enterprise);
        
        // Get organizations
        ShoppingServicesOrganization shoppingOrg = 
            (ShoppingServicesOrganization) enterprise.getOrganizationByName("Shopping Services");
        OrderManagementOrganization orderOrg = 
            (OrderManagementOrganization) enterprise.getOrganizationByName("Order Management");
        
        // Log structure
        System.out.println("  Created Enterprise: " + enterprise.getName());
        System.out.println("  Organizations: " + enterprise.getOrganizations().size());
        
//        // Create test accounts
//        createTestAccounts(system, shoppingOrg, orderOrg);
    }
    
    /**
     * Create test buyer accounts
     */
    private static void createTestAccounts(EcoSystem system,
                                          ShoppingServicesOrganization shoppingOrg,
                                          OrderManagementOrganization orderOrg) {
        System.out.println("  Creating test accounts...");
        
        // Account 1: BuyerRole
        BuyerAccount buyer1 = new BuyerAccount();
        buyer1.setUserId("BUYER-001");
        buyer1.setUsername("buyer1");
        buyer1.setPasswordHash("password123");
        buyer1.setStatus("ACTIVE");
        buyer1.setRole(new BuyerRole());
        
        BuyerProfile profile1 = buyer1.getProfile();
        profile1.setUserId("BUYER-001");
        profile1.setFullName("John Buyer");
        profile1.setEmail("buyer1@university.edu");
        profile1.setPhoneNumber("123-456-7890");
        
        shoppingOrg.getUserAccountDirectory().addUserAccount(buyer1);
        system.getUserAccountDirectory().addUserAccount(buyer1);
        System.out.println("    • buyer1 / password123");
        
        // Account 2: ProductSearcherRole
//        BuyerAccount buyer2 = new BuyerAccount();
//       buyer2.setUserId("BUYER-002");
//        buyer2.setUsername("searcher1");
//        buyer2.setPasswordHash("password123");
//        buyer2.setStatus("ACTIVE");
//        buyer2.setRole(new ProductSearcherRole());
//        
//        BuyerProfile profile2 = buyer2.getProfile();
//        profile2.setUserId("BUYER-002");
//        profile2.setFullName("Alice Searcher");
//        profile2.setEmail("searcher1@university.edu");
//        profile2.setPhoneNumber("123-456-7891");
//        
//        shoppingOrg.getUserAccountDirectory().addUserAccount(buyer2);
//        system.getUserAccountDirectory().addUserAccount(buyer2);
//        System.out.println("    • searcher1 / password123");
//        
//        // Account 3: OrderTrackerRole
//        BuyerAccount buyer3 = new BuyerAccount();
//        buyer3.setUserId("BUYER-003");
//        buyer3.setUsername("tracker1");
//        buyer3.setPasswordHash("password123");
//        buyer3.setStatus("ACTIVE");
//        buyer3.setRole(new OrderTrackerRole());
//        
//        BuyerProfile profile3 = buyer3.getProfile();
//        profile3.setUserId("BUYER-003");
//        profile3.setFullName("Bob Tracker");
//        profile3.setEmail("tracker1@university.edu");
//        profile3.setPhoneNumber("123-456-7892");
//        
//        orderOrg.getUserAccountDirectory().addUserAccount(buyer3);
//        system.getUserAccountDirectory().addUserAccount(buyer3);
//        System.out.println("    • tracker1 / password123");
    }
}