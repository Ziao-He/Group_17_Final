/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package basement_class.DAO;

import basement_class.UserAccount;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface UserAccountDAO {
     List<UserAccount> loadAll();          // 启动加载
    void saveAll(List<UserAccount> users); // 修改后回写
}
