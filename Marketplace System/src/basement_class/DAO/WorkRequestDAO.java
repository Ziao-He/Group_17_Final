/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package basement_class.DAO;

import basement_class.WorkRequest;
import common_class.Order;
import java.util.List;

/**
 *
 * @author Linyiyang
 */
public interface WorkRequestDAO {
     
    List<WorkRequest> loadAll();

    void saveAll(List<WorkRequest> list);

    void append(WorkRequest req); 
}
