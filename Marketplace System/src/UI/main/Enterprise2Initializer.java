/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.main;

import basement_class.EcoSystem;
import basement_class.Network;
import basement_class.Enterprise_2.Account.OrderProcessorAccount;
import basement_class.Enterprise_2.Account.SellerAccount;
import basement_class.Enterprise_2.Enterprise.MarketplaceEnterprise;
import basement_class.Enterprise_2.Listing;
import basement_class.Enterprise_2.Organization.OrderManagementOrganization;
import basement_class.Enterprise_2.Organization.SellerOrganization;
import basement_class.Enterprise_2.Role.ListingManagerRole;
import basement_class.Enterprise_2.Role.OrderProcessorRole;
import basement_class.Enterprise_2.Role.SellerRole;
import basement_class.UserAccount;
import java.util.UUID;
/**
 *
 * @author 心火牧神塞勒斯
 */


public class Enterprise2Initializer {

    /**
     * Initialize Enterprise 2: Marketplace Enterprise
     */
    public static void initialize(EcoSystem system, Network network) {
        System.out.println("Initializing Enterprise 2: Marketplace...");

        // 1. 创建 Enterprise2
        MarketplaceEnterprise enterprise = new MarketplaceEnterprise();
        network.addEnterprise(enterprise);

        // 2. 找组织
        SellerOrganization sellerOrg =
                (SellerOrganization) enterprise.getOrganizationByName("Seller Organization");

        OrderManagementOrganization listingOrg =
        (OrderManagementOrganization) enterprise.getOrganizationByName("Order Management Organization");

        System.out.println("  Created Enterprise: " + enterprise.getName());
        System.out.println("  Organizations: " + enterprise.getOrganizations().size());

<<<<<<< Updated upstream
        // 3. 创建测试账户
        createTestAccounts(system, sellerOrg, listingOrg);
        
        // 4. 创建测试Listing数据 ← 新增！
=======
//        // 3. 创建测试账户
//        createTestAccounts(system, sellerOrg, listingOrg);
//        
//        // 4. 创建测试Listing数据 ← 新增！
>>>>>>> Stashed changes
//        createTestListings(system, sellerOrg);
    }

    /**
     * 创建卖家账号 + ListingManager 账号
     */
    private static void createTestAccounts(EcoSystem system,
                                           SellerOrganization sellerOrg,
                                           OrderManagementOrganization OrderOrg) {

        System.out.println("  Creating test accounts...");

        // ===== 1. Seller Account =====
        SellerAccount seller1 = new SellerAccount();
        seller1.setUserId("SELLER-001");
        seller1.setUsername("seller1");
        seller1.setPasswordHash("123");
        seller1.setStatus("ACTIVE");
        seller1.setRole(new SellerRole());

        sellerOrg.getUserAccountDirectory().addUserAccount(seller1);
        system.getUserAccountDirectory().addUserAccount(seller1);

        System.out.println("    • seller1 / password123");

        // ===== 2. Listing Manager Account =====
        SellerAccount manager1 = new SellerAccount();
        manager1.setUserId("LM-001");
        manager1.setUsername("listingmanager1");
        manager1.setPasswordHash("1234");
        manager1.setStatus("ACTIVE");
        manager1.setRole(new ListingManagerRole());

        sellerOrg.getUserAccountDirectory().addUserAccount(manager1);
        system.getUserAccountDirectory().addUserAccount(manager1);

        System.out.println("    • listingmanager1 / 1234");
        
        OrderProcessorAccount orderProcessor = new OrderProcessorAccount();
        orderProcessor.setUserId("OP-001");
        orderProcessor.setUsername("order1");
        orderProcessor.setPasswordHash("12345");
        orderProcessor.setStatus("ACTIVE");
        orderProcessor.setRole(new OrderProcessorRole());

        OrderOrg.getUserAccountDirectory().addUserAccount(orderProcessor);
        system.getUserAccountDirectory().addUserAccount(orderProcessor);

        System.out.println("    • order1 / 12345");
        
        System.out.println("系统用户目录中的账号：");
        system.getUserAccountDirectory().getUserAccounts().forEach(u -> 
            System.out.println(u.getUsername() + " - " + u.getPasswordHash())
        );
    }
    
    /**
     * Create test listings (NEW!)
     */
//    private static void createTestListings(EcoSystem system, SellerOrganization sellerOrg) {
//        System.out.println("  Creating test listings...");
//        
//        // Get seller1 account
//        SellerAccount seller1 = (SellerAccount) system.getUserAccountDirectory()
//            .findByUsername("seller1");
//        
//        if (seller1 == null) {
//            System.out.println("    WARNING: seller1 not found!");
//            return;
//        }
//        
//        // Create 10 test listings
//        String[] titles = {
//            "iPhone 13 Pro - Excellent Condition",
//            "MacBook Pro 2021 - 16GB RAM",
//            "Calculus Textbook - 9th Edition",
//            "IKEA Desk - Slightly Used",
//            "Winter Jacket - North Face",
//            "Gaming Chair - Ergonomic",
//            "Chemistry Lab Manual",
//            "Bicycle - Mountain Bike",
//            "Coffee Maker - Keurig",
//            "Bookshelf - 5 Shelves"
//        };
//        
//        String[] descriptions = {
//            "Gently used iPhone 13 Pro, 256GB, no scratches. Comes with original box and charger.",
//            "Perfect for students! MacBook Pro 2021, runs smoothly, battery health 95%.",
//            "Calculus textbook in great condition. Only used for one semester. All pages intact.",
//            "IKEA desk, white color, some minor scratches but very sturdy. Dimensions: 120x60cm.",
//            "North Face winter jacket, size M, kept me warm through last winter!",
//            "Comfortable gaming chair with lumbar support. Used for 6 months, like new.",
//            "Chemistry lab manual for CHEM 101. Includes all experiments and data sheets.",
//            "Mountain bike, 21-speed, good condition. Great for campus commuting.",
//            "Keurig coffee maker, works perfectly. Includes 10 free K-cups!",
//            "Wooden bookshelf, 5 shelves, can hold many textbooks. Easy to assemble."
//        };
//        
//        double[] prices = {
//            650.00, 1200.00, 45.00, 80.00, 120.00,
//            150.00, 25.00, 200.00, 35.00, 60.00
//        };
//        
//        for (int i = 0; i < titles.length; i++) {
//            String listingId = "LIST-" + UUID.randomUUID().toString().substring(0, 8);
//            
//            Listing listing = new Listing(
//                listingId,
//                seller1,           // seller
//                titles[i],         // title
//                descriptions[i],   // description
//                "",                // imagePath (empty for now)
//                prices[i]          // price
//            );
//            
//            // Set status to Approved so buyers can see them
//            listing.setStatus("Approved");
//            
//            // Add to ListingDirectory
//            system.getListingDirectory().addListing(listing);
//            // ✅ 同步添加到 SellerAccount
//            seller1.addListing(listing);
//            System.out.println("    • Created: " + titles[i] + " - $" + prices[i]);
//        }
//        
//        System.out.println("  Total listings created: " + system.getListingDirectory().size());
//    }
}

