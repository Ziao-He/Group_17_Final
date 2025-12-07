/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.main;

import basement_class.EcoSystem;
import basement_class.Enterprise;
import basement_class.Enterprise_4.CommunicationServiceOrganization;
import basement_class.Enterprise_4.ComplaintHandlerRole;
import basement_class.Enterprise_4.IssueResolutionOrganization;
import basement_class.Enterprise_4.MessageHandlerRole;
import basement_class.Network;

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
        
        

    }
    
  
}
