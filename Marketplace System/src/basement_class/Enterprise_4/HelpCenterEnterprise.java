/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_4;

import basement_class.Enterprise;

/**
 *
 * @author yujie-liang
 */
public class HelpCenterEnterprise extends Enterprise {

    public HelpCenterEnterprise() {
        super("Help Center");
        this.addOrganization(new CommunicationServiceOrganization());
        this.addOrganization(new IssueResolutionOrganization());
    }
}