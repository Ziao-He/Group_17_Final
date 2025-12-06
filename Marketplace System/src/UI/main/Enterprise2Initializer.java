/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.main;

import basement_class.EcoSystem;
import basement_class.Network;
import basement_class.Enterprise_2.Account.ListingManagerAccount;
import basement_class.Enterprise_2.Account.SellerAccount;
import basement_class.Enterprise_2.Enterprise.MarketplaceEnterprise;
import basement_class.Enterprise_2.Organization.ListingManagementOrganization;
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

        ListingManagementOrganization listingOrg =
        (ListingManagementOrganization) enterprise.getOrganizationByName("Listing Management Organization");

        System.out.println("  Created Enterprise: " + enterprise.getName());
        System.out.println("  Organizations: " + enterprise.getOrganizations().size());

        // 3. 创建测试账户
        createTestAccounts(system, sellerOrg, listingOrg);
    }

    /**
     * 创建卖家账号 + ListingManager 账号
     */
    private static void createTestAccounts(EcoSystem system,
                                           SellerOrganization sellerOrg,
                                           ListingManagementOrganization listingOrg) {

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
        ListingManagerAccount manager1 = new ListingManagerAccount();
        manager1.setUserId("LM-001");
        manager1.setUsername("listingmanager1");
        manager1.setPasswordHash("1234");
        manager1.setStatus("ACTIVE");
        manager1.setRole(new ListingManagerRole());

        listingOrg.getUserAccountDirectory().addUserAccount(manager1);
        system.getUserAccountDirectory().addUserAccount(manager1);

        System.out.println("    • listingmanager1 / 1234");
        
        SellerAccount orderProcessor = new SellerAccount();
        orderProcessor.setUserId("OP-001");
        orderProcessor.setUsername("order1");
        orderProcessor.setPasswordHash("12345");
        orderProcessor.setStatus("ACTIVE");
        orderProcessor.setRole(new OrderProcessorRole());

        sellerOrg.getUserAccountDirectory().addUserAccount(orderProcessor);
        system.getUserAccountDirectory().addUserAccount(orderProcessor);

        System.out.println("    • order1 / 12345");
        
        System.out.println("系统用户目录中的账号：");
        system.getUserAccountDirectory().getUserAccounts().forEach(u -> 
            System.out.println(u.getUsername() + " - " + u.getPasswordHash())
        );
    }
}

