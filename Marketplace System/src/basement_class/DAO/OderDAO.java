/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package basement_class.DAO;

import common_class.Order;
import java.util.List;

/**
 *
 * @author Linyiyang
 */
public interface OderDAO {
     
    void saveAll(List<Order> orders);
    List<Order> loadAll();
    void clearFile();
}
