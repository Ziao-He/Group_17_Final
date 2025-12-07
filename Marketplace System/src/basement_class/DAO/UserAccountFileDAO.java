/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.DAO;

import basement_class.Enterprise_1.Account.BuyerProfile;
import basement_class.UserAccount;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author Administrator
 */
public class UserAccountFileDAO implements UserAccountDAO{
    private final String filePath = "data/user_accounts.csv";

@Override
public List<UserAccount> loadAll() {

    List<UserAccount> list = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

        String line;
        br.readLine(); // ✅ 跳过表头

        while ((line = br.readLine()) != null) {

            String[] p = line.split(",");

            String id = p[0];
            String username = p[1];
            String email = p[2];
            String password = p[3];
            String phone = p[4];
            String orgId = p[5];
            String status = p[6];
            String role = p[7];
            int warning = Integer.parseInt(p[8]);

            String buyerPreferredCategories = p.length > 9 ? p[9] : "";
            String buyerMaxBudget = p.length > 10 ? p[10] : "";
            String buyerLocation = p.length > 11 ? p[11] : "";
            String buyerPayMethod = p.length > 12 ? p[12] : "";

            UserAccount ua = createByRole(role);

            if (ua == null) {
                System.out.println("❌ Unknown role in CSV: " + role);
                continue;
            }

            // ✅ 核心修复：绑定 Role 对象
            switch (role) {

                case "BuyerRole" -> ua.setRole(new basement_class.Enterprise_1.Role.BuyerRole());
                case "ProductSearcherRole" -> ua.setRole(new basement_class.Enterprise_1.Role.BuyerRole());
                case "OrderTrackerRole" -> ua.setRole(new basement_class.Enterprise_1.Role.OrderTrackerRole());

                case "SellerRole" -> ua.setRole(new basement_class.Enterprise_2.Role.SellerRole());
                case "ListingManagerRole" -> ua.setRole(new basement_class.Enterprise_2.Role.ListingManagerRole());
                case "OrderProcessorRole" -> ua.setRole(new basement_class.Enterprise_2.Role.OrderProcessorRole());

                case "PlatformAdminRole" -> ua.setRole(new basement_class.Enterprise_3.Role.PlatformAdminRole());
                case "SystemAdminRole" -> ua.setRole(new basement_class.Enterprise_3.Role.SystemAdminRole());
                case "AccountAdminRole" -> ua.setRole(new basement_class.Enterprise_3.Role.AccountAdminRole());
                case "ContentModeratorRole" -> ua.setRole(new basement_class.Enterprise_3.Role.ContentModeratorRole());
                case "PolicyEnforcerRole" -> ua.setRole(new basement_class.Enterprise_3.Role.PolicyEnforcerRole());

                default -> System.out.println("❌ Unhandled role: " + role);
            }

            ua.setUserId(id);
            ua.setUsername(username);
            ua.setEmail(email);
            ua.setPasswordHash(password);
            ua.setPhoneNumber(phone);
            ua.setOrganizationId(orgId);
            ua.setStatus(status);

            for (int i = 0; i < warning; i++) {
                ua.incrementWarning();
            }

            // ✅ ✅ ✅ 还原 BuyerProfile
            if (ua instanceof basement_class.Enterprise_1.Account.BuyerAccount buyer) {

                BuyerProfile profile = buyer.getProfile();
                if (profile == null) {
                    profile = new BuyerProfile();
                    buyer.setProfile(profile);
                }

                // 分类：Book|Electronics
                if (!buyerPreferredCategories.isEmpty()) {
                    String[] cats = buyerPreferredCategories.split("\\|");
                    for (String c : cats) {
                        if (!c.isBlank()) {
                            profile.addPreferredCategory(c.trim());
                        }
                    }
                }

                if (!buyerMaxBudget.isEmpty()) {
                    try {
                        profile.setMaxBudget(Double.parseDouble(buyerMaxBudget));
                    } catch (NumberFormatException ignored) {
                    }
                }

                profile.setPreferredLocation(buyerLocation);
                profile.setPreferredPaymentMethod(buyerPayMethod);
            }

            list.add(ua);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}


    @Override
public void saveAll(List<UserAccount> users) {

    try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {

        pw.println(
            "id,username,email,password,phone,orgId,status,role,warningCount," +
            "buyerPreferredCategories,buyerMaxBudget,buyerLocation,buyerPayMethod"
        );

        for (UserAccount ua : users) {

            String buyerPreferredCategories = "";
            String buyerMaxBudget = "";
            String buyerLocation = "";
            String buyerPayMethod = "";

            // ✅ 只有 BuyerAccount 才有 Profile
            if (ua instanceof basement_class.Enterprise_1.Account.BuyerAccount buyer) {
                BuyerProfile profile = buyer.getProfile();

                if (profile != null) {
                    if (profile.getPreferredCategories() != null && !profile.getPreferredCategories().isEmpty()) {
                        buyerPreferredCategories =
                                String.join("|", profile.getPreferredCategories());
                    }

                    buyerMaxBudget = String.valueOf(profile.getMaxBudget());
                    buyerLocation = profile.getPreferredLocation();
                    buyerPayMethod = profile.getPreferredPaymentMethod();
                }
            }

            pw.println(
                    ua.getUserId() + "," +
                    ua.getUsername() + "," +
                    ua.getEmail() + "," +
                    ua.getPasswordHash() + "," +
                    ua.getPhoneNumber() + "," +
                    ua.getOrganizationId() + "," +
                    ua.getStatus() + "," +
                    ua.getRole().getClass().getSimpleName() + "," +
                    ua.getWarningCount() + "," +
                    buyerPreferredCategories + "," +
                    buyerMaxBudget + "," +
                    buyerLocation + "," +
                    buyerPayMethod
            );
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}

private UserAccount createByRole(String role) {

    return switch (role) {

        // ✅ Enterprise 1 - Buyer
        case "BuyerRole", 
             "ProductSearcherRole", 
             "OrderTrackerRole" 
                -> new basement_class.Enterprise_1.Account.BuyerAccount();

        // ✅ Enterprise 2 - Seller
        case "SellerRole", 
             "ListingManagerRole" 
                -> new basement_class.Enterprise_2.Account.SellerAccount();

        case "OrderProcessorRole" 
                -> new basement_class.Enterprise_2.Account.OrderProcessorAccount();

        // ✅ Enterprise 3 - Platform Admin
        case "PlatformAdminRole", 
             "AccountAdminRole", 
             "ContentModeratorRole", 
             "PolicyEnforcerRole"
                -> new basement_class.Enterprise_3.Account.PlatformAdminAccount();

        // ✅ Enterprise 3 - System Admin
        case "SystemAdminRole" 
                -> new basement_class.Enterprise_3.Account.SystemAdminAccount();

        // ✅ Enterprise 4 - Help Center（如果你有）
        case "HelpCenterAdminRole" 
                -> new basement_class.Enterprise_4.HelpCenterAdminAccount();

        default -> null;
    };
}
}
