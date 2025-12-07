/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.DAO;

import basement_class.EcoSystem;
import basement_class.Enterprise;
import basement_class.Network;
import basement_class.Organization;
import basement_class.Role;
import basement_class.UserAccount;
import java.util.UUID;

/**
 *
 * @author Administrator
 */
public class UserAccountService {
       private final UserAccountDAO dao;
    private final EcoSystem system;

    public UserAccountService(UserAccountDAO dao, EcoSystem system) {
        this.dao = dao;
        this.system = system;
    }

    // ✅ 系统启动时调用一次
    public void loadAllUsers() {
        for (UserAccount ua : dao.loadAll()) {
            system.getUserAccountDirectory().addUserAccount(ua);
        }
    }

    // ✅ 注册
    public void register(UserAccount account) {
        system.getUserAccountDirectory().addUserAccount(account);
        dao.saveAll(system.getUserAccountDirectory().getUserAccounts());
    }

    // ✅ 登录
    public UserAccount login(String username, String password) {
        UserAccount ua =
            system.getUserAccountDirectory().findByUsername(username);

        if (ua != null && ua.authenticate(password) && ua.isActive()) {
            ua.recordLogin();
            dao.saveAll(system.getUserAccountDirectory().getUserAccounts());
            return ua;
        }
        return null;
    }

    // ✅ 封号 / 解封
    public void updateStatus(UserAccount ua, String status) {
        ua.setStatus(status);
        dao.saveAll(system.getUserAccountDirectory().getUserAccounts());
    }

    // ✅ 警告
    public void addWarning(UserAccount ua) {
        ua.incrementWarning();
        dao.saveAll(system.getUserAccountDirectory().getUserAccounts());
    }

    // ✅ 删除账号
    public void delete(UserAccount ua) {
        system.getUserAccountDirectory().removeUserAccount(ua);
        dao.saveAll(system.getUserAccountDirectory().getUserAccounts());
    }

public void distributeUsersToOrganizations() {

    System.out.println("\n===== [START REDISTRIBUTE] =====");

    // ✅ 打印 system 中的所有用户 + 角色
    for (UserAccount ua : system.getUserAccountDirectory().getUserAccounts()) {
        System.out.println(
            "[SYSTEM USER] " + ua.getUsername()
            + " | role = " + (ua.getRole() == null ? "NULL" : ua.getRole().getClass().getSimpleName())
        );
    }

    // ✅ 原有逻辑（你现在用的那一版）
    for (Network n : system.getNetworks()) {
        for (Enterprise e : n.getEnterprises()) {
            for (Organization o : e.getOrganizations()) {
                o.getUserAccountDirectory().getUserAccounts().clear();

                // 🔍 打印每个 org 拥有哪些 role
                System.out.println(
                    "[ORG ROLE] " + e.getName() + " -> " + o.getName()
                    + " roles = " + o.getRoles()
                );
            }
        }
    }

    for (UserAccount ua : system.getUserAccountDirectory().getUserAccounts()) {

        if (ua.getRole() == null) continue;
        Class<? extends Role> userRoleClass = ua.getRole().getClass();

        for (Network n : system.getNetworks()) {
            for (Enterprise e : n.getEnterprises()) {
                for (Organization org : e.getOrganizations()) {

                    if (org.hasRole(userRoleClass)) {
                        org.getUserAccountDirectory().addUserAccount(ua);

                        System.out.println(
                            "[AUTO-BIND SUCCESS] " + ua.getUsername()
                            + " -> " + e.getName()
                            + " -> " + org.getName()
                        );
                    }
                }
            }
        }
    }

    System.out.println("===== [END REDISTRIBUTE] =====\n");
}

public UserAccount registerUser(
        String username,
        String password,
        String email,
        String phone,
        String userType   // "BUYER" or "SELLER"
) {
    // ✅ 1️⃣ 重名校验
    if (system.getUserAccountDirectory().findByUsername(username) != null) {
        throw new IllegalArgumentException("Username already exists");
    }

    UserAccount ua;

    // ✅ 2️⃣ 只允许注册 Enterprise 1 和 2
    if ("BUYER".equalsIgnoreCase(userType)) {
        ua = new basement_class.Enterprise_1.Account.BuyerAccount();
        ua.setRole(new basement_class.Enterprise_1.Role.BuyerRole());
        ua.setOrganizationId("SHOPPING_ORG");  // 可按你现在 CSV 风格
    } 
    else if ("SELLER".equalsIgnoreCase(userType)) {
        ua = new basement_class.Enterprise_2.Account.SellerAccount();
        ua.setRole(new basement_class.Enterprise_2.Role.SellerRole());
        ua.setOrganizationId("SELLER_ORG");
    } 
    else {
        throw new IllegalArgumentException("Only Buyer and Seller can register.");
    }

    // ✅ 3️⃣ 基本字段
    ua.setUserId(UUID.randomUUID().toString());
    ua.setUsername(username);
    ua.setPasswordHash(password);   // 你现在是明文，先按现有风格
    ua.setEmail(email);
    ua.setPhoneNumber(phone);
    ua.setStatus("ACTIVE");

    // ✅ 4️⃣ 加入 system 全局目录
    system.getUserAccountDirectory().addUserAccount(ua);

    // ✅ 5️⃣ 立刻回绑到 Organization（否则登录会丢 org）
    distributeUsersToOrganizations();

    // ✅ 6️⃣ 立刻回写 CSV
    dao.saveAll(system.getUserAccountDirectory().getUserAccounts());

    return ua;
}
}
