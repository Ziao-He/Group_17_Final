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
import basement_class.Enterprise_4.Message;
import basement_class.Enterprise_4.MessageDirectory;
import basement_class.Enterprise_4.MessageFlagRequest;
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

        CommunicationServiceOrganization csOrg = new CommunicationServiceOrganization();
        IssueResolutionOrganization irOrg = new IssueResolutionOrganization();

        enterprise4.addOrganization(csOrg);
        enterprise4.addOrganization(irOrg);

        irOrg.addRole(new ComplaintHandlerRole());
        csOrg.addRole(new MessageHandlerRole());
        
        createTestAccounts(system, csOrg, irOrg);

    }
    private static void createTestAccounts(EcoSystem system,
                                           CommunicationServiceOrganization csOrg,
                                           IssueResolutionOrganization irOrg) {

        System.out.println("  Creating Help Center test accounts...");

        // 1. Help Center Admin
        HelpCenterAccount admin = new HelpCenterAccount();
        admin.setUserId("HC-ADMIN-001");
        admin.setUsername("h");
        admin.setPasswordHash("1234");
        admin.setStatus("ACTIVE");
        admin.setRole(new HelpCenterAdmin());

        // Put in IssueResolutionOrganization, can also be understood as the administrator
        irOrg.getUserAccountDirectory().addUserAccount(admin);
        system.getUserAccountDirectory().addUserAccount(admin);

        // 2. Message Handler
        HelpCenterAccount msgHandler = new HelpCenterAccount();
        msgHandler.setUserId("HC-MSG-001");
        msgHandler.setUsername("msg1");
        msgHandler.setPasswordHash("1234");
        msgHandler.setStatus("ACTIVE");
        msgHandler.setRole(new MessageHandlerRole());

        // Placed in the Communication Service organization
        csOrg.getUserAccountDirectory().addUserAccount(msgHandler);
        system.getUserAccountDirectory().addUserAccount(msgHandler);

        // Complaint Handler
        HelpCenterAccount complaintHandler = new HelpCenterAccount();
        complaintHandler.setUserId("HC-CMP-001");
        complaintHandler.setUsername("ch1");
        complaintHandler.setPasswordHash("1234");
        complaintHandler.setStatus("ACTIVE");
        complaintHandler.setRole(new ComplaintHandlerRole());

        // Place it in the Issue Resolution organization
        irOrg.getUserAccountDirectory().addUserAccount(complaintHandler);
        system.getUserAccountDirectory().addUserAccount(complaintHandler);
    }
    
//    private static void createDemoMessagesBetweenBuyerAndSeller(EcoSystem system) {
//
//        MessageDirectory messageDirectory = system.getMessageDirectory();
//        if (messageDirectory == null) {
//            messageDirectory = new MessageDirectory();
//            system.setMessageDirectory(messageDirectory);
//            System.out.println("[Enterprise4] MessageDirectory created.");
//        }
//
//        if (messageDirectory.getAllMessages() != null
//                && !messageDirectory.getAllMessages().isEmpty()) {
//            System.out.println("[Enterprise4] MessageDirectory already has messages, skip demo messages.");
//            return;
//        }
//
//        UserAccount buyer = system.getUserAccountDirectory().findByUserId("BUYER-001");
//        UserAccount seller = system.getUserAccountDirectory().findByUserId("SELLER-001");
//
//        if (buyer == null || seller == null) {
//            System.err.println("[Enterprise4] Cannot find buyer1 or seller1, skip demo messages.");
//            return;
//        }
//
//        messageDirectory.addMessage(
//                buyer,
//                seller,
//                "Hi, I saw your listing for the CS textbook. Is it still available?"
//        );
//
//        messageDirectory.addMessage(
//                seller,
//                buyer,
//                "Hi! Yes, it's still available. Are you still looking for it this week?"
//        );
//
//        messageDirectory.addMessage(
//                buyer,
//                seller,
//                "Yes, I need it for my exam review. What condition is the book in?"
//        );
//
//        messageDirectory.addMessage(
//                seller,
//                buyer,
//                "It's in very good condition, no notes inside, just a little wear on the cover."
//        );
//
//        messageDirectory.addMessage(
//                buyer,
//                seller,
//                "Nice. Could you do 25 dollars instead of 30?"
//        );
//
//        messageDirectory.addMessage(
//                seller,
//                buyer,
//                "I can do 27 dollars. It's almost like new."
//        );
//
//        messageDirectory.addMessage(
//                buyer,
//                seller,
//                "27 is okay for me. Where can we meet on campus?"
//        );
//
//        messageDirectory.addMessage(
//                seller,
//                buyer,
//                "How about tomorrow 3pm at the library entrance?"
//        );
//
//        messageDirectory.addMessage(
//                buyer,
//                seller,
//                "Sounds good. I will bring cash. See you tomorrow!"
//        );
//
//        messageDirectory.addMessage(
//                seller,
//                buyer,
//                "Great, see you then. If anything changes, I will message you here."
//        );
//
//        System.out.println("[Enterprise4] Demo messages between buyer1 and seller1 created.");
//    }
//    
//
//private static void createDemoMessageFlagRequests(EcoSystem system,
//                                                  CommunicationServiceOrganization csOrg) {
//
//
//    MessageDirectory messageDirectory = system.getMessageDirectory();
//    java.util.List<Message> allMessages = messageDirectory.getAllMessages();
//        MessageFlagRequest req = new MessageFlagRequest(allMessages.get(0), allMessages.get(0).getSender(), "001");
//        csOrg.getWorkRequestDirectory().addWorkRequest(req);
//        allMessages.get(0).setFlaggedByUser(true);
//        
//        MessageFlagRequest req1 = new MessageFlagRequest(allMessages.get(1), allMessages.get(1).getSender(), "002");
//        csOrg.getWorkRequestDirectory().addWorkRequest(req1);
//        allMessages.get(1).setFlaggedByUser(true);
//        
//        MessageFlagRequest req2 = new MessageFlagRequest(allMessages.get(2), allMessages.get(2).getSender(), "003");
//        csOrg.getWorkRequestDirectory().addWorkRequest(req2);
//        allMessages.get(2).setFlaggedByUser(true);
//        
//
//
//    
//}


  
}
