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
}
