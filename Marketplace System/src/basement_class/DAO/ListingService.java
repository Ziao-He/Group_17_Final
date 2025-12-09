/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.DAO;

import basement_class.Enterprise_2.Listing;
import basement_class.Enterprise_2.ListingDirectory;
import java.util.List;

/**
 *
<<<<<<< Updated upstream
 * @author Administrator
=======
 * @author Linyiyang
>>>>>>> Stashed changes
 */
public class ListingService {
     private final ListingDAO dao;
    private final ListingDirectory directory;

    public ListingService(ListingDAO dao, ListingDirectory directory) {
        this.dao = dao;
        this.directory = directory;
    }

<<<<<<< Updated upstream
    // ✅ 启动读取
=======
>>>>>>> Stashed changes
    public void loadListings() {
        List<Listing> list = dao.loadAll();
        for (Listing l : list) {
            directory.addListing(l);
        }
    }

<<<<<<< Updated upstream
    // ✅ 进入 Login 自动保存
=======
>>>>>>> Stashed changes
    public void saveListings() {
        dao.saveAll(directory.getAllListings());
    }
}
