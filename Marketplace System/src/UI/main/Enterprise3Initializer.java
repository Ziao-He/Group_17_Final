/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.main;

import basement_class.EcoSystem;
import basement_class.Enterprise_3.Enterprise.PlatformManagementEnterprise;
import basement_class.Network;
import basement_class.UserAccount;

/**
 *
 * @author bob-h
 */
public class Enterprise3Initializer {
   public static void initialize(EcoSystem system) {

        // 1. 创建 Network
        Network network = system.createNetwork("NEU Network");

        // 2. 创建 Enterprise3
        PlatformManagementEnterprise enterprise = new PlatformManagementEnterprise();
        network.addEnterprise(enterprise);

        // 3. 创建一个简单 admin 账号（无需角色、无需组织）
        SimpleAdminAccount admin = new SimpleAdminAccount(
                "admin", "123"
        );

        // 将账号加入系统全局目录
        system.getUserAccountDirectory().addUserAccount(admin);

        System.out.println("[Initializer] Admin 账号已创建：用户名=admin, 密码=123");
    }

    /**
     * 一个简单的 UserAccount 实现（因为你的 UserAccount 是 abstract）
     */
    public static class SimpleAdminAccount extends UserAccount {
        public SimpleAdminAccount(String username, String password) {
            this.setUserId("ADMIN001");
            this.setUsername(username);
            this.setPasswordHash(password);
            this.setStatus("ACTIVE");
            this.setRole(null);   // AdminJPanel 不需要 role
        }
    }
}
