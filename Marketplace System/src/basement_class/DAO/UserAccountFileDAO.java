package basement_class.DAO;

import basement_class.Enterprise_1.Account.BuyerAccount;
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

                UserAccount ua = createByRole(role);

if (ua == null) {
    System.out.println("❌ Unknown role in CSV: " + role);
    continue;
}

// ✅ ✅ ✅ 核心修复：给 UserAccount 正式绑定 Role 对象
switch (role) {

    // ===== Enterprise 1 =====
    case "BuyerRole" -> ua.setRole(new basement_class.Enterprise_1.Role.BuyerRole());
    case "ProductSearcherRole" -> ua.setRole(new basement_class.Enterprise_1.Role.BuyerRole());
    case "OrderTrackerRole" -> ua.setRole(new basement_class.Enterprise_1.Role.OrderTrackerRole());

    // ===== Enterprise 2 =====
    case "SellerRole" -> ua.setRole(new basement_class.Enterprise_2.Role.SellerRole());
    case "ListingManagerRole" -> ua.setRole(new basement_class.Enterprise_2.Role.ListingManagerRole());
    case "OrderProcessorRole" -> ua.setRole(new basement_class.Enterprise_2.Role.OrderProcessorRole());

    // ===== Enterprise 3 =====
    case "PlatformAdminRole" -> ua.setRole(new basement_class.Enterprise_3.Role.PlatformAdminRole());
    case "SystemAdminRole" -> ua.setRole(new basement_class.Enterprise_3.Role.SystemAdminRole());
    case "AccountAdminRole" -> ua.setRole(new basement_class.Enterprise_3.Role.AccountAdminRole());
    case "ContentModeratorRole" -> ua.setRole(new basement_class.Enterprise_3.Role.ContentModeratorRole());
    case "PolicyEnforcerRole" -> ua.setRole(new basement_class.Enterprise_3.Role.PolicyEnforcerRole());

    // ===== Enterprise 4 =====
//    case "HelpCenterAdminRole" -> ua.setRole(new basement_class.Enterprise_4.Role.HelpCenterAdminRole());

    default -> System.out.println("❌ Unhandled role in CSV: " + role);
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

            pw.println("id,username,email,password,phone,orgId,status,role,warningCount");

            for (UserAccount ua : users) {
                pw.println(
                        ua.getUserId() + "," +
                        ua.getUsername() + "," +
                        ua.getEmail() + "," +
                        ua.getPasswordHash() + "," +
                        ua.getPhoneNumber() + "," +
                        ua.getOrganizationId() + "," +
                        ua.getStatus() + "," +
                        ua.getRole().getClass().getSimpleName() + "," +
                        ua.getWarningCount()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ✅ 核心工厂方法（你现在架构里最关键的一步）
private UserAccount createByRole(String role) {

    return switch (role) {

        // ✅ Enterprise 1
        case "BuyerRole" -> new BuyerAccount();
        case "ProductSearcherRole" -> new BuyerAccount();
        case "OrderTrackerRole" -> new BuyerAccount();

        // ✅ Enterprise 2
        case "SellerRole" -> new SellerAccount();
        case "ListingManagerRole" -> new SellerAccount();
        case "OrderProcessorRole" -> new OrderProcessorAccount();

        // ✅ Enterprise 3
        case "PlatformAdminRole" -> new PlatformAdminAccount();
        case "SystemAdminRole" -> new SystemAdminAccount();
        case "AccountAdminRole" -> new PlatformAdminAccount();
        case "ContentModeratorRole" -> new PlatformAdminAccount();

        // ✅ Enterprise 4
        case "HelpCenterAdminRole" -> new HelpCenterAccount();

        default -> null;
    };
}
}



