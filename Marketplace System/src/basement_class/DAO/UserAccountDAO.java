/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package basement_class.DAO;

import basement_class.UserAccount;
import java.util.List;

/**
 *
 * @author Linyiyang
 */
public interface UserAccountDAO {
     List<UserAccount> loadAll();          
    void saveAll(List<UserAccount> users); 
}
