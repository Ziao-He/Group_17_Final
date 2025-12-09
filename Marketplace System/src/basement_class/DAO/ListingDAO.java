/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package basement_class.DAO;

import basement_class.Enterprise_2.Listing;
import java.util.List;

/**
 *
<<<<<<< Updated upstream
 * @author Administrator
=======
 * @author Linyiyang
>>>>>>> Stashed changes
 */
public interface ListingDAO  {
    void saveAll(List<Listing> listings);

    List<Listing> loadAll();

    void clearFile();
}
