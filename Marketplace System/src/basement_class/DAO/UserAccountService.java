/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.DAO;

import basement_class.EcoSystem;
import basement_class.Enterprise;
import basement_class.Network;
import basement_class.Organization;
import basement_class.Role;
import basement_class.UserAccount;
import java.util.UUID;

/**
 *
 * @author Linyiyang
 */
public class UserAccountService {
       private final UserAccountDAO dao;
    private final EcoSystem system;

    public UserAccountService(UserAccountDAO dao, EcoSystem system) {
        this.dao = dao;
        this.system = system;
    }

    public void loadAllUsers() {
        for (UserAccount ua : dao.loadAll()) {
            system.getUserAccountDirectory().addUserAccount(ua);
        }
    }

    public void register(UserAccount account) {
        system.getUserAccountDirectory().addUserAccount(account);
        dao.saveAll(system.getUserAccountDirectory().getUserAccounts());
    }

    public UserAccount login(String username, String password) {
        UserAccount ua =
            system.getUserAccountDirectory().findByUsername(username);

        if (ua != null && ua.authenticate(password) && ua.isActive()) {
            ua.recordLogin();
            dao.saveAll(system.getUserAccountDirectory().getUserAccounts());
            return ua;
        }
        return null;
    }

    public void updateStatus(UserAccount ua, String status) {
        ua.setStatus(status);
        dao.saveAll(system.getUserAccountDirectory().getUserAccounts());
    }

    public void addWarning(UserAccount ua) {
        ua.incrementWarning();
        dao.saveAll(system.getUserAccountDirectory().getUserAccounts());
    }

    public void delete(UserAccount ua) {
        system.getUserAccountDirectory().removeUserAccount(ua);
        dao.saveAll(system.getUserAccountDirectory().getUserAccounts());
    }

public void distributeUsersToOrganizations() {

    System.out.println("\n===== [START REDISTRIBUTE] =====");

 
    for (UserAccount ua : system.getUserAccountDirectory().getUserAccounts()) {
        System.out.println(
            "[SYSTEM USER] " + ua.getUsername()
            + " | role = " + (ua.getRole() == null ? "NULL" : ua.getRole().getClass().getSimpleName())
        );
    }

    for (Network n : system.getNetworks()) {
        for (Enterprise e : n.getEnterprises()) {
            for (Organization o : e.getOrganizations()) {
                o.getUserAccountDirectory().getUserAccounts().clear();

               
                System.out.println(
                    "[ORG ROLE] " + e.getName() + " -> " + o.getName()
                    + " roles = " + o.getRoles()
                );
            }
        }
    }

    for (UserAccount ua : system.getUserAccountDirectory().getUserAccounts()) {

        if (ua.getRole() == null) continue;
        Class<? extends Role> userRoleClass = ua.getRole().getClass();

        for (Network n : system.getNetworks()) {
            for (Enterprise e : n.getEnterprises()) {
                for (Organization org : e.getOrganizations()) {

                    if (org.hasRole(userRoleClass)) {
                        org.getUserAccountDirectory().addUserAccount(ua);

                        System.out.println(
                            "[AUTO-BIND SUCCESS] " + ua.getUsername()
                            + " -> " + e.getName()
                            + " -> " + org.getName()
                        );
                    }
                }
            }
        }
    }

    System.out.println("===== [END REDISTRIBUTE] =====\n");
}

public UserAccount registerUser(
        String username,
        String password,
        String email,
        String phone,
        String userType   // "BUYER" or "SELLER"
) {
  
    if (system.getUserAccountDirectory().findByUsername(username) != null) {
        throw new IllegalArgumentException("Username already exists");
    }

    UserAccount ua;

    if ("BUYER".equalsIgnoreCase(userType)) {
        ua = new basement_class.Enterprise_1.Account.BuyerAccount();
        ua.setRole(new basement_class.Enterprise_1.Role.BuyerRole());
        ua.setOrganizationId("SHOPPING_ORG");  // 可按你现在 CSV 风格
    } 
    else if ("SELLER".equalsIgnoreCase(userType)) {
        ua = new basement_class.Enterprise_2.Account.SellerAccount();
        ua.setRole(new basement_class.Enterprise_2.Role.SellerRole());
        ua.setOrganizationId("SELLER_ORG");
    } 
    else {
        throw new IllegalArgumentException("Only Buyer and Seller can register.");
    }

    ua.setUserId(UUID.randomUUID().toString());
    ua.setUsername(username);
    ua.setPasswordHash(password);   
    ua.setEmail(email);
    ua.setPhoneNumber(phone);
    ua.setStatus("ACTIVE");

    system.getUserAccountDirectory().addUserAccount(ua);


    distributeUsersToOrganizations();


    dao.saveAll(system.getUserAccountDirectory().getUserAccounts());

    return ua;
}
}
