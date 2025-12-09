/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.main;

import basement_class.*;
import basement_class.DAO.ListingFileDAO;
import basement_class.DAO.ListingService;
import basement_class.DAO.OdedrService;
import basement_class.DAO.OderFileDAO;
import basement_class.DAO.UserAccountFileDAO;
import basement_class.DAO.UserAccountService;
import basement_class.DAO.WorkRequestDAO;
import basement_class.DAO.WorkRequestFileDAO;
import java.io.File;
import service.E3.WorkRequestRouter;



/**
 *
 * @author bob-h
 */
public class SystemInitializer {
    public static boolean firstLaunch = true;

    /**
     * Initialize the entire Campus Marketplace System
     */
    public static EcoSystem initialize() {

        System.out.println("=== Campus Marketplace System Initialization ===\n");

        EcoSystem system = EcoSystem.getInstance();

        if (!system.getNetworks().isEmpty()) {
            System.out.println("⚠ EcoSystem already initialized, skip rebuild.");
            return system;
        }

        Network campusNetwork =
                system.createNetwork("Campus Marketplace Network");

        System.out.println("✓ Created Network: " + campusNetwork.getName());

        System.out.println("--- Initializing Enterprises ---");

        initializeEnterprise1(system, campusNetwork);
        initializeEnterprise2(system, campusNetwork);
        initializeEnterprise3(system, campusNetwork);
        initializeEnterprise4(system, campusNetwork);

        // =========================================================
        // STEP 1: LOAD EXISTING CSV DATA (if exists)
        // =========================================================

        System.out.println("\n========================================");
        System.out.println("  Step 1: Loading Existing CSV Data");
        System.out.println("========================================\n");

        int existingUsers = 0;
        int existingListings = 0;
        int existingOrders = 0;

        UserAccountService userService =
                new UserAccountService(new UserAccountFileDAO(), system);

        // Try to load existing user accounts
        try {
            userService.loadAllUsers();
            userService.distributeUsersToOrganizations();
            existingUsers = system.getUserAccountDirectory().size();
            
            if (existingUsers > 0) {
                System.out.println("✓ Loaded " + existingUsers + " existing users from CSV");
            } else {
                System.out.println("ℹ No existing users found in CSV");
            }
        } catch (Exception e) {
            System.out.println("ℹ No existing user_accounts.csv found");
        }

        // Try to load existing orders
        if (firstLaunch) {
            try {
                System.out.println("--- Loading existing Orders (CSV) ---");
                OrderDirectory orderDir = system.getOrderDirectory();
                OdedrService orderService =
                        new OdedrService(new OderFileDAO(), orderDir);
                orderService.loadOrders();
                existingOrders = orderDir.size();
                
                if (existingOrders > 0) {
                    System.out.println("✓ Loaded " + existingOrders + " existing orders");
                } else {
                    System.out.println("ℹ No existing orders found");
                }
            } catch (Exception e) {
                System.out.println("ℹ No existing orders.csv found");
            }

            // Try to load existing listings
            try {
                System.out.println("--- Loading existing Listings (CSV) ---");
                ListingService listingService =
                        new ListingService(new ListingFileDAO(),
                                system.getListingDirectory());
                listingService.loadListings();
                existingListings = system.getListingDirectory().size();
                
                if (existingListings > 0) {
                    System.out.println("✓ Loaded " + existingListings + " existing listings");
                } else {
                    System.out.println("ℹ No existing listings found");
                }
            } catch (Exception e) {
                System.out.println("ℹ No existing listings.csv found");
            }

            // Try to load existing work requests
            try {
                System.out.println("--- Loading existing WorkRequests (CSV) ---");
                WorkRequestDAO wrDao = new WorkRequestFileDAO();
                int wrCount = 0;
                for (WorkRequest req : wrDao.loadAll()) {
                    system.getWorkRequestDirectory().addWorkRequest(req);
                    WorkRequestRouter.routeToEnterprise3(req);
                    wrCount++;
                }
                if (wrCount > 0) {
                    System.out.println("✓ Loaded " + wrCount + " existing work requests");
                }
            } catch (Exception e) {
                System.out.println("ℹ No existing work_requests.csv found");
            }
        }

        // =========================================================
        // STEP 2: GENERATE ADDITIONAL DATA WITH FAKER
        // =========================================================

        if (firstLaunch) {
            System.out.println("\n========================================");
            System.out.println("  Step 2: Generating Additional Data with Faker");
            System.out.println("========================================\n");

            try {
                // Generate additional test data
                FakeDataGenerator generator = new FakeDataGenerator(system);
                generator.generateAllData();

                // Redistribute all users (existing + new) to organizations
                userService.distributeUsersToOrganizations();

                // Print what was generated
                generator.printSummary();

                System.out.println("✅ Faker data generation completed!\n");

            } catch (Exception e) {
                System.err.println("⚠ Faker generation failed: " + e.getMessage());
                e.printStackTrace();
            }
        }

        // =========================================================
        // STEP 3: SAVE COMBINED DATA TO CSV
        // =========================================================

        if (firstLaunch) {
            System.out.println("\n========================================");
            System.out.println("  Step 3: Saving Combined Data to CSV");
            System.out.println("========================================\n");

            try {
                // Create data directory if needed
                File dataDir = new File("data");
                if (!dataDir.exists()) {
                    dataDir.mkdirs();
                    System.out.println("✓ Created data directory");
                }

                // Save all users (existing + Faker generated)
                UserAccountFileDAO userDAO = new UserAccountFileDAO();
                userDAO.saveAll(system.getUserAccountDirectory().getUserAccounts());
                System.out.println("✓ Saved " + system.getUserAccountDirectory().size() + 
                                 " users (existing " + existingUsers + " + new " + 
                                 (system.getUserAccountDirectory().size() - existingUsers) + 
                                 ") to data/user_accounts.csv");

                // Save all listings
                ListingFileDAO listingDAO = new ListingFileDAO();
                listingDAO.saveAll(system.getListingDirectory().getAllListings());
                System.out.println("✓ Saved " + system.getListingDirectory().size() + 
                                 " listings (existing " + existingListings + " + new " + 
                                 (system.getListingDirectory().size() - existingListings) + 
                                 ") to data/listings.csv");

                // Save all orders
                OderFileDAO orderDAO = new OderFileDAO();
                orderDAO.saveAll(system.getOrderDirectory().getAllOrders());
                System.out.println("✓ Saved " + system.getOrderDirectory().size() + 
                                 " orders (existing " + existingOrders + " + new " + 
                                 (system.getOrderDirectory().size() - existingOrders) + 
                                 ") to data/orders.csv");

                System.out.println("\n✅ All data saved to CSV!\n");

            } catch (Exception e) {
                System.err.println("⚠ CSV saving failed: " + e.getMessage());
                e.printStackTrace();
            }
        }

        printSummary(system, campusNetwork);

        System.out.println("\n=== System Ready ===\n");
        firstLaunch = false;

        return system;
    }

    // =========================================================
    // Enterprise Initializers
    // =========================================================
    private static void initializeEnterprise1(EcoSystem system, Network network) {
        try {
            Enterprise1Initializer.initialize(system, network);
            System.out.println("✓ Enterprise 1 initialized");
        } catch (Exception e) {
            System.err.println("✗ Enterprise 1 failed: " + e.getMessage());
        }
    }
    private static void initializeEnterprise2(EcoSystem system, Network network) {
        try {
            Enterprise2Initializer.initialize(system, network);
            System.out.println("✓ Enterprise 2 initialized");
        } catch (Exception e) {
            System.err.println("✗ Enterprise 2 failed: " + e.getMessage());
        }
    }
    private static void initializeEnterprise3(EcoSystem system, Network network) {
        try {
            Enterprise3Initializer.initialize(system, network);
            System.out.println("✓ Enterprise 3 initialized");
        } catch (Exception e) {
            System.err.println("✗ Enterprise 3 failed: " + e.getMessage());
        }
    }

    private static void initializeEnterprise4(EcoSystem system, Network network) {
        try {
            Enterprise4Initializer.initialize(system, network);
            System.out.println("✓ Enterprise 4 initialized");
        } catch (Exception e) {
            System.err.println("✗ Enterprise 4 failed: " + e.getMessage());
        }
    }

    // =========================================================
    // System Summary
    // =========================================================
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
        System.out.println("Users: " +
                system.getUserAccountDirectory().size());
        System.out.println("Listings: " +
                system.getListingDirectory().size());
        System.out.println("Orders: " +
                system.getOrderDirectory().size());
        System.out.println("WorkRequests: " +
                system.getWorkRequestDirectory().getRequestList().size());
    }
}