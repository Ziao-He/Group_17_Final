/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.DAO;

import basement_class.EcoSystem;
import basement_class.UserAccount;

/**
 *
 * @author Administrator
 */
public class UserAccountService {
       private final UserAccountDAO dao;
    private final EcoSystem system;

    public UserAccountService(UserAccountDAO dao, EcoSystem system) {
        this.dao = dao;
        this.system = system;
    }

    // ✅ 系统启动时调用一次
    public void loadAllUsers() {
        for (UserAccount ua : dao.loadAll()) {
            system.getUserAccountDirectory().addUserAccount(ua);
        }
    }

    // ✅ 注册
    public void register(UserAccount account) {
        system.getUserAccountDirectory().addUserAccount(account);
        dao.saveAll(system.getUserAccountDirectory().getUserAccounts());
    }

    // ✅ 登录
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

    // ✅ 封号 / 解封
    public void updateStatus(UserAccount ua, String status) {
        ua.setStatus(status);
        dao.saveAll(system.getUserAccountDirectory().getUserAccounts());
    }

    // ✅ 警告
    public void addWarning(UserAccount ua) {
        ua.incrementWarning();
        dao.saveAll(system.getUserAccountDirectory().getUserAccounts());
    }

    // ✅ 删除账号
    public void delete(UserAccount ua) {
        system.getUserAccountDirectory().removeUserAccount(ua);
        dao.saveAll(system.getUserAccountDirectory().getUserAccounts());
    }
}
