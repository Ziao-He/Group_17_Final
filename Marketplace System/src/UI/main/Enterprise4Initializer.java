/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.main;

import basement_class.EcoSystem;
import basement_class.Enterprise;
import basement_class.Enterprise_4.CommunicationServiceOrganization;
import basement_class.Enterprise_4.ComplaintHandlerRole;
import basement_class.Enterprise_4.HelpCenterAccount;
import basement_class.Enterprise_4.HelpCenterAdmin;
import basement_class.Enterprise_4.IssueResolutionOrganization;
import basement_class.Enterprise_4.MessageDirectory;
import basement_class.Enterprise_4.MessageHandlerRole;
import basement_class.Network;
import basement_class.UserAccount;

/**
 *
 * @author bob-h
 */
public class Enterprise4Initializer {
    public static void initialize(EcoSystem system, Network network) {
        Enterprise enterprise4 = new Enterprise("Help Center") {};
        network.addEnterprise(enterprise4);

        // ✅ 2️⃣ 创建 Organization
        CommunicationServiceOrganization csOrg = new CommunicationServiceOrganization();
        IssueResolutionOrganization irOrg = new IssueResolutionOrganization();

        enterprise4.addOrganization(csOrg);
        enterprise4.addOrganization(csOrg);

        // ✅ 3️⃣ 注册 Role 到 Organization
        irOrg.addRole(new ComplaintHandlerRole());
        csOrg.addRole(new MessageHandlerRole());
        
        createTestAccounts(system, csOrg, irOrg);
        
        createDemoMessagesBetweenBuyerAndSeller(system);

    }
    private static void createTestAccounts(EcoSystem system,
                                           CommunicationServiceOrganization csOrg,
                                           IssueResolutionOrganization irOrg) {

        System.out.println("  Creating Help Center test accounts...");

        // ===== 1. Help Center Admin 账号 =====
        HelpCenterAccount admin = new HelpCenterAccount();
        admin.setUserId("HC-ADMIN-001");
        admin.setUsername("h");
        admin.setPasswordHash("1234");
        admin.setStatus("ACTIVE");
        admin.setRole(new HelpCenterAdmin());

        // 放在 IssueResolutionOrganization 里，也可以理解为总管理员
        irOrg.getUserAccountDirectory().addUserAccount(admin);
        system.getUserAccountDirectory().addUserAccount(admin);

        // ===== 2. Message Handler 账号 =====
        HelpCenterAccount msgHandler = new HelpCenterAccount();
        msgHandler.setUserId("HC-MSG-001");
        msgHandler.setUsername("msg1");
        msgHandler.setPasswordHash("1234");
        msgHandler.setStatus("ACTIVE");
        msgHandler.setRole(new MessageHandlerRole());

        // 放在 Communication Service 组织中
        csOrg.getUserAccountDirectory().addUserAccount(msgHandler);
        system.getUserAccountDirectory().addUserAccount(msgHandler);

        // ===== 3. Complaint Handler 账号 =====
        HelpCenterAccount complaintHandler = new HelpCenterAccount();
        complaintHandler.setUserId("HC-CMP-001");
        complaintHandler.setUsername("ch1");
        complaintHandler.setPasswordHash("1234");
        complaintHandler.setStatus("ACTIVE");
        complaintHandler.setRole(new ComplaintHandlerRole());

        // 放在 Issue Resolution 组织中
        irOrg.getUserAccountDirectory().addUserAccount(complaintHandler);
        system.getUserAccountDirectory().addUserAccount(complaintHandler);
    }
    
    private static void createDemoMessagesBetweenBuyerAndSeller(EcoSystem system) {

        MessageDirectory messageDirectory = system.getMessageDirectory();
        if (messageDirectory == null) {
            messageDirectory = new MessageDirectory();
            system.setMessageDirectory(messageDirectory);
            System.out.println("[Enterprise4] MessageDirectory created.");
        }

        // 如果已经有消息了，就不重复塞（可选）
        if (messageDirectory.getAllMessages() != null
                && !messageDirectory.getAllMessages().isEmpty()) {
            System.out.println("[Enterprise4] MessageDirectory already has messages, skip demo messages.");
            return;
        }

        // 2. 在系统的 UserAccountDirectory 里找到 buyer1 和 seller1
        UserAccount buyer = system.getUserAccountDirectory().findByUserId("BUYER-001");
        UserAccount seller = system.getUserAccountDirectory().findByUserId("SELLER-001");

        if (buyer == null || seller == null) {
            System.err.println("[Enterprise4] Cannot find buyer1 or seller1, skip demo messages.");
            return;
        }

        // 3. 创建 10 条 Buyer / Seller 互相发送的信息
        //    （BuyerAccount <-> SellerAccount，都是 UserAccount 类型）

        messageDirectory.addMessage(
                buyer,
                seller,
                "Hi, I saw your listing for the CS textbook. Is it still available?"
        );

        messageDirectory.addMessage(
                seller,
                buyer,
                "Hi! Yes, it's still available. Are you still looking for it this week?"
        );

        messageDirectory.addMessage(
                buyer,
                seller,
                "Yes, I need it for my exam review. What condition is the book in?"
        );

        messageDirectory.addMessage(
                seller,
                buyer,
                "It's in very good condition, no notes inside, just a little wear on the cover."
        );

        messageDirectory.addMessage(
                buyer,
                seller,
                "Nice. Could you do 25 dollars instead of 30?"
        );

        messageDirectory.addMessage(
                seller,
                buyer,
                "I can do 27 dollars. It's almost like new."
        );

        messageDirectory.addMessage(
                buyer,
                seller,
                "27 is okay for me. Where can we meet on campus?"
        );

        messageDirectory.addMessage(
                seller,
                buyer,
                "How about tomorrow 3pm at the library entrance?"
        );

        messageDirectory.addMessage(
                buyer,
                seller,
                "Sounds good. I will bring cash. See you tomorrow!"
        );

        messageDirectory.addMessage(
                seller,
                buyer,
                "Great, see you then. If anything changes, I will message you here."
        );

        System.out.println("[Enterprise4] Demo messages between buyer1 and seller1 created.");
    }

  
}
