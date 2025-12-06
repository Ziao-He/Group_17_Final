/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.DAO;

import basement_class.Enterprise_1.Account.BuyerAccount;
import basement_class.Enterprise_2.Account.SellerAccount;
import basement_class.UserAccount;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class UserAccountDAO {
    private String filePath = "data/users.csv";

    public List<UserAccount> loadUsers() {
        List<UserAccount> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line;
            while ((line = br.readLine()) != null) {

                String[] p = line.split(",");

                String id = p[0];
                String username = p[1];
                String password = p[2];
                String role = p[3];
                String status = p[4];

                UserAccount ua = createUserByRole(role, username, password);

                if (ua == null) continue; // unknown role

                ua.setUserId(id);
                ua.setStatus(status);

                list.add(ua);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    private UserAccount createUserByRole(String role, String username, String password) {

        switch (role.toUpperCase()) {

//            case "BUYER":
//                return new BuyerAccount(username, password);
//
//            case "SELLER":
//                return new SellerAccount(username, password);
//
//            case "ADMIN":
//                return new AdminAccount(username, password);

            default:
                System.err.println("Unknown role: " + role);
                return null;
        }
    }

    public void saveUsers(List<UserAccount> users) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {

            for (UserAccount u : users) {
                pw.println(
                        u.getUserId() + "," +
                        u.getUsername() + "," +
                        u.getPasswordHash() + "," +
                        u.getRole() + "," +
                        u.getStatus()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
