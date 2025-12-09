/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_4;

import basement_class.Organization;

/**
 *
 * @author yujie-liang
 */
   
public class CommunicationServiceOrganization extends Organization {

    public CommunicationServiceOrganization() {
        super("Communication Service");
        this.addRole(new MessageHandlerRole());
    }
}