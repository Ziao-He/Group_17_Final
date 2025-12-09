/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.main;

import basement_class.*;
import basement_class.Enterprise_1.Account.BuyerAccount;
import basement_class.Enterprise_1.Account.BuyerProfile;
import basement_class.Enterprise_2.Account.SellerAccount;
import basement_class.Enterprise_2.Listing;
import basement_class.Enterprise_2.WorkRequest.OrderReviewRequest;
import basement_class.Enterprise_3.Account.PlatformAdminAccount;
import basement_class.Enterprise_3.Account.SystemAdminAccount;
import basement_class.Enterprise_4.HelpCenterAccount;
import common_class.Order;
import com.github.javafaker.Faker;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author bob-h
 */
public class FakeDataGenerator {
    
    private final Faker faker;
    private final Random random;
    private final EcoSystem system;
    
    // Track existing IDs to avoid duplicates
    private Set<String> existingUserIds;
    private Set<String> existingListingIds;
    private Set<String> existingOrderIds;
    
    // Categories for products
    private static final String[] CATEGORIES = {
        "Electronics", "Books", "Furniture", "Clothing", 
        "Sports", "Kitchen", "Stationery", "Music"
    };
    
    // Payment methods
    private static final String[] PAYMENT_METHODS = {
        "Cash", "Venmo", "PayPal", "Zelle", "CreditCard", "DebitCard"
    };
    
    // Campus locations
    private static final String[] CAMPUS_LOCATIONS = {
        "Main Campus", "West Campus", "East Campus", "North Campus",
        "Student Center", "Library", "Dormitory A", "Dormitory B"
    };
    
    public FakeDataGenerator(EcoSystem system) {
        this.faker = new Faker(Locale.US);
        this.random = new Random();
        this.system = system;
        
        // Initialize tracking sets
        this.existingUserIds = new HashSet<>();
        this.existingListingIds = new HashSet<>();
        this.existingOrderIds = new HashSet<>();
        
        // Collect existing IDs to avoid duplicates
        collectExistingIds();
    }
    
    /**
     * Collect existing IDs from system to avoid duplicates
     */
    private void collectExistingIds() {
        // Collect existing user IDs
        for (UserAccount ua : system.getUserAccountDirectory().getUserAccounts()) {
            if (ua.getUserId() != null) {
                existingUserIds.add(ua.getUserId());
            }
        }
        
        // Collect existing listing IDs
        for (Listing listing : system.getListingDirectory().getAllListings()) {
            if (listing.getId() != null) {
                existingListingIds.add(listing.getId());
            }
        }
        
        // Collect existing order IDs
        for (Order order : system.getOrderDirectory().getAllOrders()) {
            if (order.getOrderId() != null) {
                existingOrderIds.add(order.getOrderId());
            }
        }
        
        if (!existingUserIds.isEmpty()) {
            System.out.println("ℹ Found " + existingUserIds.size() + " existing users, will avoid ID conflicts");
        }
    }
    
    /**
     * Generate unique user ID
     */
    private String generateUniqueUserId(String prefix) {
        String id;
        do {
            id = prefix + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (existingUserIds.contains(id));
        
        existingUserIds.add(id);
        return id;
    }
    
    /**
     * Generate unique listing ID
     */
    private String generateUniqueListingId() {
        String id;
        do {
            id = "LST-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (existingListingIds.contains(id));
        
        existingListingIds.add(id);
        return id;
    }
    
    /**
     * Generate unique order ID
     */
    private String generateUniqueOrderId() {
        String id;
        do {
            id = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (existingOrderIds.contains(id));
        
        existingOrderIds.add(id);
        return id;
    }
    
    /**
     * Generate all test data
     */
    public void generateAllData() {
        System.out.println("\n========================================");
        System.out.println("  Faker Data Generation Started");
        System.out.println("========================================\n");
        
        // Generate in order (dependencies)
        List<UserAccount> buyers = generateBuyers(8);
        List<UserAccount> sellers = generateSellers(6);
        List<UserAccount> admins = generateAdmins(4);
        List<UserAccount> helpCenter = generateHelpCenterStaff(2);
        
        // Get all sellers (existing + new) for listings
        List<UserAccount> allSellers = system.getUserAccountDirectory().getAllSellers();
        
        // Generate listings (use all available sellers)
        List<Listing> listings = generateListings(allSellers.isEmpty() ? sellers : allSellers, 20);
        
        // Get all buyers (existing + new) for orders
        List<UserAccount> allBuyers = system.getUserAccountDirectory().getAllBuyers();
        List<Listing> allListings = system.getListingDirectory().getAllListings();
        
        // Generate orders (use all available buyers and listings)
        List<Order> orders = generateOrders(
            allBuyers.isEmpty() ? buyers : allBuyers,
            allListings,
            15
        );
        
        // Generate work requests
        generateWorkRequests(buyers, sellers, orders, 10);
        
        System.out.println("\n========================================");
        System.out.println("  Faker Data Generation Completed");
        System.out.println("========================================");
        System.out.println("✓ New Buyers: " + buyers.size());
        System.out.println("✓ New Sellers: " + sellers.size());
        System.out.println("✓ New Admins: " + admins.size());
        System.out.println("✓ New Help Center: " + helpCenter.size());
        System.out.println("✓ New Listings: " + listings.size());
        System.out.println("✓ New Orders: " + orders.size());
        System.out.println("========================================\n");
    }
    
    /**
     * Generate fake buyer accounts
     */
    private List<UserAccount> generateBuyers(int count) {
        List<UserAccount> buyers = new ArrayList<>();
        
        System.out.println("Generating " + count + " buyers...");
        
        for (int i = 0; i < count; i++) {
            BuyerAccount buyer = new BuyerAccount();
            
            // Basic info with unique ID
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            buyer.setUserId(generateUniqueUserId("BUYER"));  // ✅ Unique ID
            buyer.setUsername("faker_" + firstName.toLowerCase() + lastName.toLowerCase() + random.nextInt(9999));  // ✅ Unique username
            buyer.setEmail(firstName.toLowerCase() + "." + lastName.toLowerCase() + random.nextInt(999) + "@university.edu");
            buyer.setPasswordHash("password123");
            buyer.setPhoneNumber(faker.phoneNumber().subscriberNumber(10));
            buyer.setOrganizationId("SHOPPING_ORG");
            buyer.setStatus("ACTIVE");
            
            // Set role (only BuyerRole and OrderTrackerRole)
            if (i % 2 == 0) {
                buyer.setRole(new basement_class.Enterprise_1.Role.OrderTrackerRole());
            } else {
                buyer.setRole(new basement_class.Enterprise_1.Role.BuyerRole());
            }
            
            // Profile
            BuyerProfile profile = buyer.getProfile();
            if (profile == null) {
                profile = new BuyerProfile();
                buyer.setProfile(profile);
            }
            
            profile.setFullName(firstName + " " + lastName);
            profile.setEmail(buyer.getEmail());
            profile.setPhoneNumber(buyer.getPhoneNumber());
            profile.setMaxBudget(faker.number().numberBetween(500, 5000));
            profile.setPreferredLocation(CAMPUS_LOCATIONS[random.nextInt(CAMPUS_LOCATIONS.length)]);
            profile.setPreferredPaymentMethod(PAYMENT_METHODS[random.nextInt(PAYMENT_METHODS.length)]);
            
            // Add preferred categories (1-3 random)
            int numCategories = random.nextInt(3) + 1;
            for (int j = 0; j < numCategories; j++) {
                profile.addPreferredCategory(CATEGORIES[random.nextInt(CATEGORIES.length)]);
            }
            
            // Add to system
            system.getUserAccountDirectory().addUserAccount(buyer);
            buyers.add(buyer);
        }
        
        System.out.println("✓ Generated " + buyers.size() + " buyers");
        return buyers;
    }
    
    /**
     * Generate fake seller accounts
     */
    private List<UserAccount> generateSellers(int count) {
        List<UserAccount> sellers = new ArrayList<>();
        
        System.out.println("Generating " + count + " sellers...");
        
        for (int i = 0; i < count; i++) {
            SellerAccount seller = new SellerAccount();
            
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            seller.setUserId(generateUniqueUserId("SELLER"));  // ✅ Unique ID
            seller.setUsername("faker_seller_" + firstName.toLowerCase() + random.nextInt(9999));  // ✅ Unique
            seller.setEmail("seller." + firstName.toLowerCase() + random.nextInt(999) + "@university.edu");
            seller.setPasswordHash("password123");
            seller.setPhoneNumber(faker.phoneNumber().subscriberNumber(10));
            seller.setOrganizationId("SELLER_ORG");
            seller.setStatus("ACTIVE");
            
            // Set role
            if (i % 2 == 0) {
                seller.setRole(new basement_class.Enterprise_2.Role.SellerRole());
            } else {
                seller.setRole(new basement_class.Enterprise_2.Role.ListingManagerRole());
            }
            
            system.getUserAccountDirectory().addUserAccount(seller);
            sellers.add(seller);
        }
        
        System.out.println("✓ Generated " + sellers.size() + " sellers");
        return sellers;
    }
    
    /**
     * Generate fake admin accounts
     */
    private List<UserAccount> generateAdmins(int count) {
        List<UserAccount> admins = new ArrayList<>();
        
        System.out.println("Generating " + count + " admins...");
        
        // Platform Admins only (skip SystemAdmin to avoid conflict)
        for (int i = 0; i < count; i++) {
            PlatformAdminAccount admin = new PlatformAdminAccount();
            
            String name = faker.name().fullName();
            admin.setUserId(generateUniqueUserId("ADMIN"));  // ✅ Unique ID
            admin.setUsername("faker_admin" + random.nextInt(9999));  // ✅ Unique
            admin.setEmail("admin" + random.nextInt(999) + "@marketplace.com");
            admin.setPasswordHash("admin123");
            admin.setPhoneNumber(faker.phoneNumber().subscriberNumber(10));
            admin.setOrganizationId(i % 2 == 0 ? "USER_ORG" : "CONTENT_ORG");
            admin.setStatus("ACTIVE");
            
            // Set different admin roles
            switch (i % 4) {
                case 0 -> admin.setRole(new basement_class.Enterprise_3.Role.PlatformAdminRole());
                case 1 -> admin.setRole(new basement_class.Enterprise_3.Role.AccountAdminRole());
                case 2 -> admin.setRole(new basement_class.Enterprise_3.Role.ContentModeratorRole());
                default -> admin.setRole(new basement_class.Enterprise_3.Role.PolicyEnforcerRole());
            }
            
            system.getUserAccountDirectory().addUserAccount(admin);
            admins.add(admin);
        }
        
        System.out.println("✓ Generated " + admins.size() + " admins");
        return admins;
    }
    
    /**
     * Generate fake help center staff
     */
    private List<UserAccount> generateHelpCenterStaff(int count) {
        List<UserAccount> staff = new ArrayList<>();
        
        System.out.println("Generating " + count + " help center staff...");
        
        for (int i = 0; i < count; i++) {
            HelpCenterAccount helpStaff = new HelpCenterAccount();
            
            String name = faker.name().fullName();
            helpStaff.setUserId(generateUniqueUserId("HC"));  // ✅ Unique ID
            helpStaff.setUsername("faker_help" + random.nextInt(9999));  // ✅ Unique
            helpStaff.setEmail("help" + random.nextInt(999) + "@marketplace.com");
            helpStaff.setPasswordHash("help123");
            helpStaff.setPhoneNumber(faker.phoneNumber().subscriberNumber(10));
            helpStaff.setOrganizationId("HELP_ORG");
            helpStaff.setStatus("ACTIVE");
            
            if (i % 2 == 0) {
                helpStaff.setRole(new basement_class.Enterprise_4.MessageHandlerRole());
            } else {
                helpStaff.setRole(new basement_class.Enterprise_4.ComplaintHandlerRole());
            }
            
            system.getUserAccountDirectory().addUserAccount(helpStaff);
            staff.add(helpStaff);
        }
        
        System.out.println("✓ Generated " + staff.size() + " help center staff");
        return staff;
    }
    
    /**
     * Generate fake product listings
     */
    private List<Listing> generateListings(List<UserAccount> sellers, int count) {
        List<Listing> listings = new ArrayList<>();
        
        System.out.println("Generating " + count + " listings...");
        
        if (sellers.isEmpty()) {
            System.out.println("⚠ No sellers available, skipping listing generation");
            return listings;
        }
        
        // Product templates for realistic data
        String[][] productTemplates = {
            {"iPhone 13 Pro", "Electronics", "Excellent condition, 95% battery health"},
            {"MacBook Air M2", "Electronics", "2023 model, barely used"},
            {"Calculus Textbook", "Books", "10th edition, minimal highlighting"},
            {"Desk Lamp", "Furniture", "LED, adjustable brightness"},
            {"Winter Jacket", "Clothing", "North Face, size M"},
            {"Basketball", "Sports", "Official size, good grip"},
            {"Coffee Maker", "Kitchen", "Keurig, works perfectly"},
            {"Wireless Mouse", "Electronics", "Logitech, Bluetooth"},
            {"Study Desk", "Furniture", "Wooden, sturdy, some scratches"},
            {"Backpack", "Clothing", "SwissGear, laptop compartment"}
        };
        
        for (int i = 0; i < count; i++) {
            // Random seller
            UserAccount seller = sellers.get(random.nextInt(sellers.size()));
            
            // Random product template or generate new
            String title, category, description;
            if (i < productTemplates.length) {
                String[] template = productTemplates[i];
                title = template[0];
                category = template[1];
                description = template[2];
            } else {
                // Use Faker for additional products
                title = faker.commerce().productName();
                category = CATEGORIES[random.nextInt(CATEGORIES.length)];
                description = faker.lorem().sentence(10);
            }
            
            // Generate listing with unique ID
            String listingId = generateUniqueListingId();  // ✅ Unique ID
            double price = faker.number().randomDouble(2, 10, 2000);
            
            Listing listing = new Listing(
                listingId,
                seller,
                title,
                description,
                "default.png",  // Default image
                price
            );
            
            // Random status (mostly Approved for testing)
            String[] statuses = {"Approved", "Approved", "Approved", "Pending", "Reserved"};
            listing.setStatus(statuses[random.nextInt(statuses.length)]);
            
            // Random quantity (1-3 for second-hand items)
            listing.setQuantity(random.nextInt(3) + 1);
            
            // Random submit time (within last 30 days)
            LocalDateTime submitTime = LocalDateTime.now()
                .minusDays(random.nextInt(30))
                .minusHours(random.nextInt(24));
            listing.setSubmitTime(submitTime);
            
            // Add to directory
            system.getListingDirectory().addListing(listing);
            listings.add(listing);
        }
        
        System.out.println("✓ Generated " + listings.size() + " listings");
        return listings;
    }
    
    /**
     * Generate fake orders
     */
    private List<Order> generateOrders(List<UserAccount> buyers, List<Listing> listings, int count) {
        List<Order> orders = new ArrayList<>();
        
        System.out.println("Generating " + count + " orders...");
        
        if (buyers.isEmpty() || listings.isEmpty()) {
            System.out.println("⚠ No buyers or listings available, skipping order generation");
            return orders;
        }
        
        for (int i = 0; i < count; i++) {
            // Random buyer and listing
            UserAccount buyer = buyers.get(random.nextInt(buyers.size()));
            Listing listing = listings.get(random.nextInt(listings.size()));
            
            // Generate order with unique ID
            String orderId = generateUniqueOrderId();  // ✅ Unique ID
            
            Order order = new Order(
                orderId,
                listing.getId(),
                buyer.getUserId(),
                listing.getSellerId(),
                listing.getPrice()
            );
            
            order.setQuantity(1);
            
            // Random status distribution
            int statusRoll = random.nextInt(100);
            if (statusRoll < 40) {
                order.setStatus(Order.STATUS_COMPLETED);
                
                // Update buyer statistics for completed orders
                if (buyer instanceof BuyerAccount buyerAcc) {
                    buyerAcc.getOrderIds().add(orderId);
                    buyerAcc.setTotalPurchases(buyerAcc.getTotalPurchases() + 1);
                    buyerAcc.incrementCompletedPurchases();
                    buyerAcc.setTotalSpending(buyerAcc.getTotalSpending() + listing.getPrice());
                    buyerAcc.addPoints((int)(listing.getPrice() * 0.05));
                }
            } else if (statusRoll < 60) {
                order.setStatus(Order.STATUS_ACCEPTED);
                
                if (buyer instanceof BuyerAccount buyerAcc) {
                    buyerAcc.getOrderIds().add(orderId);
                    buyerAcc.setTotalPurchases(buyerAcc.getTotalPurchases() + 1);
                }
            } else if (statusRoll < 80) {
                order.setStatus(Order.STATUS_PENDING);
                
                if (buyer instanceof BuyerAccount buyerAcc) {
                    buyerAcc.getOrderIds().add(orderId);
                    buyerAcc.setTotalPurchases(buyerAcc.getTotalPurchases() + 1);
                }
            } else if (statusRoll < 90) {
                order.setStatus(Order.STATUS_REJECTED);
            } else {
                order.setStatus(Order.STATUS_CANCELLED);
            }
            
            // Random delivery method
            String[] deliveryMethods = {"Pickup", "Campus Delivery", "Meet at Student Center"};
            order.setDeliveryMethod(deliveryMethods[random.nextInt(deliveryMethods.length)]);
            
            // Random meeting location
            order.setMeetingLocation(CAMPUS_LOCATIONS[random.nextInt(CAMPUS_LOCATIONS.length)]);
            
            // Add to directory
            system.getOrderDirectory().addOrder(order);
            orders.add(order);
        }
        
        System.out.println("✓ Generated " + orders.size() + " orders");
        return orders;
    }
    
    /**
     * Generate fake work requests
     */
    private void generateWorkRequests(List<UserAccount> buyers, List<UserAccount> sellers, 
                                      List<Order> orders, int count) {
        System.out.println("Generating " + count + " work requests...");
        
        int generated = 0;
        
        for (int i = 0; i < count && i < orders.size(); i++) {
            Order order = orders.get(i);
            
            // Find buyer and seller for this order
            UserAccount buyer = system.getUserAccountDirectory().findByUserId(order.getBuyerId());
            UserAccount seller = system.getUserAccountDirectory().findByUserId(order.getSellerId());
            
            if (buyer == null || seller == null) continue;
            
            // Generate OrderReviewRequest with unique ID
            String requestId = "WR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            
            OrderReviewRequest workRequest = new OrderReviewRequest(
                buyer,
                order,
                "Purchase request for listing: " + order.getListingId()
            );
            workRequest.setId(requestId);
            workRequest.setReceiver(seller);
            
            // Set status based on order status
            switch (order.getStatus()) {
                case Order.STATUS_COMPLETED:
                case Order.STATUS_ACCEPTED:
                    workRequest.setStatus("Resolved");
                    workRequest.resolve();
                    break;
                case Order.STATUS_REJECTED:
                    workRequest.setStatus("Rejected");
                    workRequest.resolve();
                    break;
                default:
                    workRequest.setStatus("Pending");
            }
            
            system.getWorkRequestDirectory().addWorkRequest(workRequest);
            generated++;
        }
        
        System.out.println("✓ Generated " + generated + " work requests");
    }
    
    /**
     * Print generation summary
     */
    public void printSummary() {
        System.out.println("\n========================================");
        System.out.println("  Combined Data Summary");
        System.out.println("========================================");
        System.out.println("Total Users: " + system.getUserAccountDirectory().size());
        System.out.println("  - Buyers: " + system.getUserAccountDirectory().getAllBuyers().size());
        System.out.println("  - Sellers: " + system.getUserAccountDirectory().getAllSellers().size());
        System.out.println("Total Listings: " + system.getListingDirectory().size());
        System.out.println("Total Orders: " + system.getOrderDirectory().size());
        System.out.println("Total WorkRequests: " + system.getWorkRequestDirectory().size());
        System.out.println("========================================\n");
    }
}