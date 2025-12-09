/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.main;

import UI.Enterprise3.FakeDataGenerator;
import basement_class.EcoSystem;
import basement_class.Enterprise;
import basement_class.Enterprise_2.Listing;
import basement_class.Enterprise_3.Account.PlatformAdminAccount;
import basement_class.Enterprise_3.Account.SystemAdminAccount;
import basement_class.Enterprise_3.Enterprise.PlatformManagementEnterprise;
import basement_class.Enterprise_3.Organization.ContentControlOrganization;
import basement_class.Enterprise_3.Organization.UserControlOrganization;
import basement_class.Enterprise_3.Role.AccountAdminRole;
import basement_class.Enterprise_3.Role.ContentModeratorRole;
import basement_class.Enterprise_3.Role.PlatformAdminRole;
import basement_class.Enterprise_3.Role.PolicyEnforcerRole;
import basement_class.Enterprise_3.Role.RegistrationReviewerRole;
import basement_class.Enterprise_3.WorkRequest.AccountStatusReviewRequest;
import basement_class.Enterprise_3.WorkRequest.ListingReviewRequest;
import basement_class.Enterprise_3.WorkRequest.PolicyViolationRequest;
import basement_class.Enterprise_3.WorkRequest.RegistrationApprovalRequest;
import basement_class.Network;
import basement_class.Role;
import basement_class.UserAccount;

/**
 *
 * @author bob-h
 */
public class Enterprise3Initializer {
public static void initialize(EcoSystem system, Network network) {

        System.out.println("[Enterprise3] Initializing Platform Management...");

        // ✅ 1️⃣ 创建 Enterprise 3
        Enterprise enterprise3 = new Enterprise("Platform Management") {};
        network.addEnterprise(enterprise3);

        // ✅ 2️⃣ 创建 Organization
        UserControlOrganization userOrg = new UserControlOrganization();
        ContentControlOrganization contentOrg = new ContentControlOrganization();

        enterprise3.addOrganization(userOrg);
        enterprise3.addOrganization(contentOrg);

        // ✅ 3️⃣ 注册 Role 到 Organization
        userOrg.addRole(new AccountAdminRole());
        userOrg.addRole(new RegistrationReviewerRole());

        contentOrg.addRole(new ContentModeratorRole());
        contentOrg.addRole(new PolicyEnforcerRole());

//        // ✅ 4️⃣ 创建 Platform Admin 账号（总管理员）
//        PlatformAdminAccount admin = new PlatformAdminAccount();
//        admin.setUserId("ADMIN_001");
//        admin.setUsername("platformadmin");
//        admin.setPasswordHash("123");
//        admin.setStatus("ACTIVE");
//
//        userOrg.getUserAccountDirectory().addUserAccount(admin);
//
//        UserAccount systemAdmin = new SystemAdminAccount();
//        systemAdmin.setUserId("SYS_001");
//        systemAdmin.setUsername("s");
//        systemAdmin.setPasswordHash("123");
//        systemAdmin.setStatus("ACTIVE");
//
//
//        system.getUserAccountDirectory().addUserAccount(systemAdmin);
//
//        // ✅ 5️⃣ 创建 UserControl 管理员
//        UserAccount accountAdmin = new PlatformAdminAccount();
//        accountAdmin.setUserId("ADMIN_002");
//        accountAdmin.setUsername("a");
//        accountAdmin.setPasswordHash("1234");
//        accountAdmin.setStatus("ACTIVE");
//        accountAdmin.setRole(new AccountAdminRole());
//
//        userOrg.getUserAccountDirectory().addUserAccount(accountAdmin);
//
//        // ✅ 6️⃣ 创建 ContentControl 管理员
//        UserAccount contentAdmin = new PlatformAdminAccount();
//        contentAdmin.setUserId("ADMIN_003");
//        contentAdmin.setUsername("c");
//        contentAdmin.setPasswordHash("1234");
//        contentAdmin.setStatus("ACTIVE");
//        contentAdmin.setRole(new ContentModeratorRole());
//
//        contentOrg.getUserAccountDirectory().addUserAccount(contentAdmin);
//
////         ✅ 7️⃣ 构造一些测试 Listing
//        Listing l1 = new Listing("L001", admin, "Old iPhone", "Used iPhone 11", "iphone.png", 200);
//        Listing l2 = new Listing("L002", accountAdmin, "MacBook", "M1 MacBook Air", "mac.png", 900);
//
//        system.getListingDirectory().addListing(l1);
//        system.getListingDirectory().addListing(l2);
//
//        // ✅ 8️⃣ 构造测试 WorkRequest
//        RegistrationApprovalRequest r1 =
//            new RegistrationApprovalRequest(accountAdmin);
//        r1.setReason("New student account");
//
//        userOrg.getWorkRequestDirectory().addWorkRequest(r1);
//
//        AccountStatusReviewRequest s1 =
//            new AccountStatusReviewRequest(accountAdmin, "suspend", "Spam behavior");
//
//        userOrg.getWorkRequestDirectory().addWorkRequest(s1);
//
//        ListingReviewRequest lReq =
//            new ListingReviewRequest(l1, "update_request", "Title updated");
//
//        contentOrg.getWorkRequestDirectory().addWorkRequest(lReq);
//
//        PolicyViolationRequest pReq =
//            new PolicyViolationRequest(accountAdmin, contentAdmin, l1,
//                "keyword_violation", "Illegal keyword found");
//
//        contentOrg.getWorkRequestDirectory().addWorkRequest(pReq);

        System.out.println("[Enterprise3] Platform Management initialized successfully.\n");
   }
}
