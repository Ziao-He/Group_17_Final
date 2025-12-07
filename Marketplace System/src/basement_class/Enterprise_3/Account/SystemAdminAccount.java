/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_3.Account;

import basement_class.Enterprise_3.Role.SystemAdminRole;
import basement_class.UserAccount;



/**
 *
 * @author Administrator
 */
public class SystemAdminAccount extends UserAccount { 

    public SystemAdminAccount() {
        super();
        this.setStatus("ACTIVE");
        this.setRole(new SystemAdminRole());   // ✅ 这里是合法的
    }

    @Override
    public String toString() {
        return getUsername();
    }

}
