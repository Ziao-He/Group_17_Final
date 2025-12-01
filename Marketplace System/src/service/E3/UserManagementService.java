/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.E3;

import basement_class.EcoSystem;
import basement_class.UserAccount;

/**
 *
 * @author Administrator
 */
public class UserManagementService {
    private EcoSystem system;

    public UserManagementService(EcoSystem system) {
        this.system = system;
    }

    public void approveRegistration(UserAccount account) {
        account.setStatus("Active");
        system.getUserAccountDirectory().addUserAccount(account);
    }

    public void rejectRegistration(UserAccount account, String reason) {
        account.setStatus("Rejected: " + reason);
    }

    public void suspendUser(UserAccount account) {
        account.setStatus("Suspended");
    }

    public void reactivateUser(UserAccount account) {
        account.setStatus("Active");
    }    
}
