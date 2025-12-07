/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_3.Account;

import basement_class.UserAccount;

/**
 *
 * @author Administrator
 */
public class PlatformAdminAccount extends UserAccount {
        public PlatformAdminAccount() {
        super();
    }

    @Override
    public String toString() {
        return getUsername();
    }
}
