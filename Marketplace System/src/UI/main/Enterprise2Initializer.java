/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.main;

import basement_class.EcoSystem;
import basement_class.Network;
import basement_class.Organization;
import basement_class.Role;
import basement_class.Enterprise_2.Account.ListingManagerAccount;
import basement_class.Enterprise_2.Account.SellerAccount;
import basement_class.Enterprise_2.Enterprise.MarketplaceEnterprise;
import basement_class.Enterprise_2.Organization.ListingManagementOrganization;
import basement_class.Enterprise_2.Organization.SellerOrganization;
import basement_class.Enterprise_2.Role.ListingManagerRole;
import basement_class.Enterprise_2.Role.OrderProcessorRole;
import basement_class.Enterprise_2.Role.SellerRole;
import java.util.UUID;
/**
 *
 * @author bob-h
 */
public class Enterprise2Initializer {
    
    /**
     * Initialize Enterprise 2: Seller Operations
     * 
     * @param system EcoSystem instance
     * @param network Network to add enterprise to
     */
    public static void initialize(EcoSystem system, Network network) {
        System.out.println("\nInitializing Enterprise 2: Seller Operations...");
        
        // Create the enterprise (organizations and roles are created in constructor)
        MarketplaceEnterprise enterprise = new MarketplaceEnterprise();
        network.addEnterprise(enterprise);
        
        // Get organizations
        SellerOrganization sellerOrg = findSellerOrganization(enterprise);
        ListingManagementOrganization listingMgmtOrg = findListingManagementOrganization(enterprise);
        
        // Log enterprise structure
        System.out.println("  Created Enterprise: " + enterprise.getName());
        System.out.println("  Organizations:");
        for (Organization org : enterprise.getOrganizations()) {
            System.out.println("    • " + org.getName() + " (" + getRoleCount(org) + " roles)");
            for (Role role : org.getRoles()) {
                System.out.println("      - " + role.getRoleName());
            }
        }
        
        // Create test data
        createTestAccounts(system, sellerOrg, listingMgmtOrg);
        
        // Link services (optional - you might want to initialize services here)
        initializeServices(system, enterprise);
    }
    
    /**
     * Create test seller accounts
     */
    private static void createTestAccounts(EcoSystem system, 
                                          SellerOrganization sellerOrg,
                                          ListingManagementOrganization listingMgmtOrg) {
        System.out.println("  Creating test accounts...");
        
        // Test Account 1: Standard Seller (Primary Role)
        SellerAccount seller1 = createSellerAccount(
            "SELLER-001", "seller1", "seller1@university.edu", 
            "123-555-1001", "SELLER-ORG-001", new SellerRole()
        );
        addAccount(seller1, sellerOrg, system);
        System.out.println("    • seller1 (SellerRole) - password: seller123");
        
        // Test Account 2: Order Processor (Specialized Role)
        SellerAccount seller2 = createSellerAccount(
            "SELLER-002", "order_processor", "processor@university.edu",
            "123-555-1002", "SELLER-ORG-001", new OrderProcessorRole()
        );
        seller2.addListing(createTestListing("LIST-001", seller2, "Calculus Textbook", "Like new calculus textbook", 45.99));
        seller2.addListing(createTestListing("LIST-002", seller2, "Desk Lamp", "LED desk lamp with adjustable brightness", 25.50));
        addAccount(seller2, sellerOrg, system);
        System.out.println("    • order_processor (OrderProcessorRole) - password: seller123");
        
        // Test Account 3: Product Manager (if implemented - optional)
        // SellerAccount seller3 = createSellerAccount(...);
        
        // Test Account 4: Listing Manager (Content Moderator role)
        ListingManagerAccount listingManager = createListingManagerAccount(
            "LIST-MGR-001", "listing_mgr", "listing.mgr@university.edu",
            "123-555-2001", "LISTING-MGMT-ORG-001", new ListingManagerRole()
        );
        addAccount(listingManager, listingMgmtOrg, system);
        System.out.println("    • listing_mgr (ListingManagerRole) - password: admin123");
        
        System.out.println("  Total test accounts created: 3");
    }
    
    /**
     * Helper: Create a seller account with common fields
     */
    private static SellerAccount createSellerAccount(String userId, String username, 
                                                    String email, String phone,
                                                    String orgId, Role role) {
        SellerAccount account = new SellerAccount();
        account.setUserId(userId);
        account.setUsername(username);
        account.setEmail(email);
        account.setPasswordHash("seller123"); // TODO: Implement proper hashing
        account.setPhoneNumber(phone);
        account.setStatus("ACTIVE");
        account.setOrganizationId(orgId);
        account.setRole(role);
        return account;
    }
    
    /**
     * Helper: Create a listing manager account
     */
    private static ListingManagerAccount createListingManagerAccount(String userId, String username, 
                                                                    String email, String phone,
                                                                    String orgId, Role role) {
        ListingManagerAccount account = new ListingManagerAccount();
        account.setUserId(userId);
        account.setUsername(username);
        account.setEmail(email);
        account.setPasswordHash("admin123"); // TODO: Implement proper hashing
        account.setPhoneNumber(phone);
        account.setStatus("ACTIVE");
        account.setOrganizationId(orgId);
        account.setRole(role);
        return account;
    }
    
    /**
     * Helper: Create a test listing for sellers
     */
    private static basement_class.Enterprise_2.Listing createTestListing(String id, 
                                                                        SellerAccount seller,
                                                                        String title, 
                                                                        String description, 
                                                                        double price) {
        // This is a simple listing creation. In real implementation, use ListingManagementService
        return new basement_class.Enterprise_2.Listing(
            id,
            seller,
            title,
            description,
            "/images/test/" + id + ".jpg", // Placeholder image path
            price
        );
    }
    
    /**
     * Helper: Add account to organization and system
     */
    private static void addAccount(basement_class.UserAccount account, Organization org, EcoSystem system) {
        // Note: This assumes your Organization class has getUserAccountDirectory() method
        // If not, you may need to adapt this based on your actual Organization structure
        
        // Try different method names based on your implementation
        try {
            // Option 1: If Organization has getUserAccountDirectory()
            if (org.getClass().getMethod("getUserAccountDirectory") != null) {
                org.getUserAccountDirectory().addUserAccount(account);
            }
        } catch (NoSuchMethodException e) {
            // Option 2: Direct access if organization manages accounts differently
            System.out.println("Note: Organization.getUserAccountDirectory() not available. Using system directory only.");
        }
        
        // Always add to system directory
        system.getUserAccountDirectory().addUserAccount(account);
    }
    
    /**
     * Helper: Get role count from organization (safe method)
     */
    private static int getRoleCount(Organization org) {
        try {
            return org.getRoles().size();
        } catch (Exception e) {
            // If getRoles() doesn't exist, return 0 or a default value
            return 0;
        }
    }
    
    /**
     * Find SellerOrganization in the enterprise
     */
    private static SellerOrganization findSellerOrganization(MarketplaceEnterprise enterprise) {
        for (Organization org : enterprise.getOrganizations()) {
            if (org instanceof SellerOrganization) {
                return (SellerOrganization) org;
            }
        }
        return null;
    }
    
    /**
     * Find ListingManagementOrganization in the enterprise
     */
    private static ListingManagementOrganization findListingManagementOrganization(MarketplaceEnterprise enterprise) {
        for (Organization org : enterprise.getOrganizations()) {
            if (org instanceof ListingManagementOrganization) {
                return (ListingManagementOrganization) org;
            }
        }
        return null;
    }
    
    /**
     * Initialize services for Enterprise 2
     * This might be called elsewhere in your system initialization
     */
    private static void initializeServices(EcoSystem system, MarketplaceEnterprise enterprise) {
        System.out.println("  Initializing Enterprise 2 services...");
        
        // Note: Service initialization depends on your system architecture
        // You might want to create services and store them in the system or enterprise
        
        // Example service initialization (adjust based on your actual implementation):
        /*
        ListingDao listingDao = new ListingDao(system.getUserAccountDirectory());
        OrderDao orderDao = new OrderDao();
        
        ListingManagementService listingService = new ListingManagementService(
            system, listingDao, new ListingDirectory()
        );
        
        OrderFulfillmentService orderService = new OrderFulfillmentService(
            system, orderDao, listingDao
        );
        
        // Store services somewhere accessible (e.g., in enterprise or system)
        enterprise.setService("ListingManagementService", listingService);
        enterprise.setService("OrderFulfillmentService", orderService);
        */
        
        System.out.println("    • Services initialized (conceptually)");
    }
    
    /**
     * Generate a unique ID for testing
     */
    private static String generateId() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
