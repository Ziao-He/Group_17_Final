/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.Enterprise_4;

import basement_class.UserAccount;

/**
 *
 * @author yujie-liang
 */
public class HelpCenterAdminAccount extends UserAccount{
       public HelpCenterAdminAccount() {
        super();
    }

    @Override
    public String toString() {
        return getUsername();
    }
}
