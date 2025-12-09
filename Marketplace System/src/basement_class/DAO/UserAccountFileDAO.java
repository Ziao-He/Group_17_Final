package basement_class.DAO;

import basement_class.Enterprise_1.Account.BuyerAccount;
import basement_class.Enterprise_1.Account.BuyerProfile;
import basement_class.Enterprise_2.Account.OrderProcessorAccount;
import basement_class.Enterprise_2.Account.SellerAccount;
import basement_class.Enterprise_3.Account.PlatformAdminAccount;
import basement_class.Enterprise_3.Account.SystemAdminAccount;
import basement_class.Enterprise_4.HelpCenterAccount;
import basement_class.UserAccount;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Linyiyang
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

            String buyerPreferredCategories = p.length > 9  ? p[9]  : "";
            String buyerMaxBudget           = p.length > 10 ? p[10] : "";
            String buyerLocation            = p.length > 11 ? p[11] : "";
            String buyerPayMethod           = p.length > 12 ? p[12] : "";

            // ✅ 新增：Profile 基础字段
            String profileFullName = p.length > 13 ? p[13] : "";
            String profileEmail    = p.length > 14 ? p[14] : "";
            String profilePhone    = p.length > 15 ? p[15] : "";

            // ✅ 1️⃣ 创建 Account 子类
            UserAccount ua = createByRole(role);
            if (ua == null) {
                System.out.println("❌ Unknown role in CSV: " + role);
                continue;
            }

            // ✅ 2️⃣ 绑定 Role 对象
            switch (role) {

                case "BuyerRole", "OrderTrackerRole"
                        -> ua.setRole(new basement_class.Enterprise_1.Role.BuyerRole());

                case "SellerRole"
                        -> ua.setRole(new basement_class.Enterprise_2.Role.SellerRole());

                case "ListingManagerRole"
                        -> ua.setRole(new basement_class.Enterprise_2.Role.ListingManagerRole());

                case "OrderProcessorRole"
                        -> ua.setRole(new basement_class.Enterprise_2.Role.OrderProcessorRole());

                case "PlatformAdminRole"
                        -> ua.setRole(new basement_class.Enterprise_3.Role.PlatformAdminRole());

                case "SystemAdminRole"
                        -> ua.setRole(new basement_class.Enterprise_3.Role.SystemAdminRole());

                case "AccountAdminRole"
                        -> ua.setRole(new basement_class.Enterprise_3.Role.AccountAdminRole());

                case "ContentModeratorRole"
                        -> ua.setRole(new basement_class.Enterprise_3.Role.ContentModeratorRole());

                case "PolicyEnforcerRole"
                        -> ua.setRole(new basement_class.Enterprise_3.Role.PolicyEnforcerRole());

                default -> System.out.println("❌ Unhandled role in CSV: " + role);
            }

            // ✅ 3️⃣ 设置基础字段（UserAccount 本身）
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

            if (ua instanceof basement_class.Enterprise_1.Account.BuyerAccount buyer) {

                BuyerProfile profile = buyer.getProfile();
                if (profile == null) {
                    profile = new BuyerProfile();
                    buyer.setProfile(profile);
                }

                // --- 偏好分类 ---
                if (!buyerPreferredCategories.isEmpty()) {
                    String[] cats = buyerPreferredCategories.split("\\|");
                    for (String c : cats) {
                        if (!c.isBlank()) {
                            profile.addPreferredCategory(c.trim());
                        }
                    }
                }

                // --- 预算 ---
                if (!buyerMaxBudget.isEmpty()) {
                    try {
                        profile.setMaxBudget(Double.parseDouble(buyerMaxBudget));
                    } catch (Exception ignored) {}
                }

                // --- 业务偏好 ---
                profile.setPreferredLocation(buyerLocation);
                profile.setPreferredPaymentMethod(buyerPayMethod);

                // ✅ ✅ ✅ 你现在最关键的 FullName
                profile.setFullName(profileFullName);

                // 这两个你现在是空列，设不设都无所谓
                profile.setEmail(profileEmail);
                profile.setPhoneNumber(profilePhone);
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
            "buyerPreferredCategories,buyerMaxBudget,buyerLocation,buyerPayMethod," +
            "profileFullName,profileEmail,profilePhone"
        );

        for (UserAccount ua : users) {

            String buyerPreferredCategories = "";
            String buyerMaxBudget = "";
            String buyerLocation = "";
            String buyerPayMethod = "";

            String profileFullName = "";
            String profileEmail = "";
            String profilePhone = "";

          
            if (ua instanceof BuyerAccount buyer) {
                BuyerProfile profile = buyer.getProfile();

                if (profile != null) {

               
                    if (profile.getPreferredCategories() != null && !profile.getPreferredCategories().isEmpty()) {
                        buyerPreferredCategories =
                                String.join("|", profile.getPreferredCategories());
                    }

                    buyerMaxBudget = String.valueOf(profile.getMaxBudget());
                    buyerLocation = profile.getPreferredLocation();
                    buyerPayMethod = profile.getPreferredPaymentMethod();

                    
                    profileFullName = profile.getFullName();
                    profileEmail = profile.getEmail();
                    profilePhone = profile.getPhoneNumber();
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
                    buyerPayMethod + "," +
                    profileFullName + "," +
                    profileEmail + "," +
                    profilePhone
            );
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}

 private UserAccount createByRole(String role) {

        return switch (role) {

            case "BuyerRole", "OrderTrackerRole"
                    -> new BuyerAccount();

            case "SellerRole", "ListingManagerRole"
                    -> new SellerAccount();

            case "OrderProcessorRole"
                    -> new OrderProcessorAccount();

            case "PlatformAdminRole", "AccountAdminRole",
                 "ContentModeratorRole", "PolicyEnforcerRole"
                    -> new PlatformAdminAccount();

            case "SystemAdminRole"
                    -> new SystemAdminAccount();

            case "HelpCenterAdminRole"
                    -> new HelpCenterAccount();

            default -> null;
        };
    }
}

