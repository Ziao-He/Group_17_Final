/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.Enterprise3;

import basement_class.EcoSystem;
import basement_class.Enterprise;
import basement_class.Enterprise_2.Listing;
import basement_class.Enterprise_3.Organization.ContentControlOrganization;
import basement_class.Enterprise_3.Organization.UserControlOrganization;
import basement_class.Enterprise_3.WorkRequest.AccountStatusReviewRequest;
import basement_class.Enterprise_3.WorkRequest.ListingReviewRequest;
import basement_class.Enterprise_3.WorkRequest.PolicyViolationRequest;
import basement_class.Enterprise_3.WorkRequest.RegistrationApprovalRequest;
import basement_class.UserAccount;

/**
 *
 * @author Administrator
 */
public class FakeDataGenerator {
      public static void generate(EcoSystem system, Enterprise enterprise) {

        System.out.println("[FakeData] 开始生成测试数据...");

        // 找到组织
        UserControlOrganization userOrg =
                (UserControlOrganization) enterprise.getOrganizationByName("User Control");

        ContentControlOrganization contentOrg =
                (ContentControlOrganization) enterprise.getOrganizationByName("Content Control");

        // 创建一些 fake 用户
        UserAccount userA = createUser(system, "userA");
        UserAccount userB = createUser(system, "userB");
        UserAccount userC = createUser(system, "userC");

        // 创建一些 fake Listing
                Listing l1 = new Listing("L001", userA, 
                "Old iPhone 11", 
                "Used iPhone 11, 64GB", 
                "iphone.png",
                199.0);

        Listing l2 = new Listing("L002", userB, 
                "Gaming Laptop", 
                "RTX 3060 Laptop - Good Condition", 
                "laptop.png",
                900.0);

        Listing l3 = new Listing("L003", userA,
                "Nike Shoes",
                "Air Force 1 - White",
                "shoes.png",
                80.0);
        
        // === 1. RegistrationApprovalRequest ===
        RegistrationApprovalRequest reg1 = new RegistrationApprovalRequest(userA);
        reg1.setReason("New student — needs account");
        userOrg.getWorkRequestDirectory().addWorkRequest(reg1);

        RegistrationApprovalRequest reg2 = new RegistrationApprovalRequest(userB);
        reg2.setReason("Wants to buy used books");
        userOrg.getWorkRequestDirectory().addWorkRequest(reg2);

        // === 2. AccountStatusReviewRequest ===
        AccountStatusReviewRequest ar1 = new AccountStatusReviewRequest(userA, "suspend", "Spam messages");
        userOrg.getWorkRequestDirectory().addWorkRequest(ar1);

        AccountStatusReviewRequest ar2 = new AccountStatusReviewRequest(userC, "ban", "Repeated violations");
        userOrg.getWorkRequestDirectory().addWorkRequest(ar2);

        // === 3. ListingReviewRequest ===（用于 Content Moderation）
        ListingReviewRequest lr1 =
            new ListingReviewRequest(l1, "seller_request_up", "请审核，我修改了标题");
        contentOrg.getWorkRequestDirectory().addWorkRequest(lr1);

        ListingReviewRequest lr2 =
            new ListingReviewRequest(l2, "seller_request_down", "卖家想自己下架");
        contentOrg.getWorkRequestDirectory().addWorkRequest(lr2);

        // === 4. PolicyViolationRequest ===（用于 Policy Enforcement）
        PolicyViolationRequest pr1 =
            new PolicyViolationRequest(userA, userB, l1, "listing_issue", "此 listing 存在违规关键词");
        pr1.addEvidencePath("evidence1.png");
        contentOrg.getWorkRequestDirectory().addWorkRequest(pr1);

        PolicyViolationRequest pr2 =
            new PolicyViolationRequest(userC, userA, "minor_dispute", "对方未按约定时间发货");
        contentOrg.getWorkRequestDirectory().addWorkRequest(pr2);

        System.out.println("[FakeData] 测试数据生成完毕！");
    }

    private static UserAccount createUser(EcoSystem system, String username) {
        FakeUserAccount u = new FakeUserAccount(username);
        system.getUserAccountDirectory().addUserAccount(u);
        return u;
    }

    public static class FakeUserAccount extends UserAccount {
        public FakeUserAccount(String username) {
            this.setUserId("FAKE_" + username);
            this.setUsername(username);
            this.setPasswordHash("123");
            this.setStatus("ACTIVE");
        }
    }
}
