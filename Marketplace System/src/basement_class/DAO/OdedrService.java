/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.DAO;

import basement_class.OrderDirectory;
import common_class.Order;
import java.util.List;

/**
 *
 * @author Linyiyang
 */
public class OdedrService {
    private final OderDAO dao;
    private final OrderDirectory directory;

    public OdedrService(OderDAO dao, OrderDirectory directory) {
        this.dao = dao;
        this.directory = directory;
    }

    public void loadOrders() {
        List<Order> orders = dao.loadAll();
        for (Order o : orders) {
            directory.addOrder(o);
        }
    }

    public void saveOrders() {
        dao.saveAll(directory.getAllOrders());
}
}
