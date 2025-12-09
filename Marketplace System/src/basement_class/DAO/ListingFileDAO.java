/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.DAO;

import basement_class.Enterprise_2.Listing;
import basement_class.UserAccount;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Linyiyang
 */
public class ListingFileDAO implements ListingDAO{
    private static final String FILE_PATH = "data/listings.csv";


    public ListingFileDAO() {
        initFile();
    }

    // ✅ 初始化 CSV（如果不存在就创建）
    private void initFile() {
        try {
            File file = new File(FILE_PATH);

            if (!file.exists()) {
                file.getParentFile().mkdirs();
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                bw.write("listingId,sellerId,title,description,imagePath,price,status,submitTime,quantity");
                bw.newLine();
                bw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ✅ 保存所有 Listings（imagePath 允许为空）
    @Override
    public void saveAll(List<Listing> listings) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {

            bw.write("listingId,sellerId,title,description,imagePath,price,status,submitTime,quantity");
            bw.newLine();

            for (Listing l : listings) {

                String imagePath = l.getImagePath() == null ? "" : l.getImagePath();

                bw.write(String.join(",",
                        safe(l.getId()),
                        safe(l.getSellerId()),
                        safe(l.getTitle()),
                        safe(l.getDescription()),
                        imagePath,
                        String.valueOf(l.getPrice()),
                        safe(l.getStatus()),
                        l.getSubmitTime() == null ? "" : l.getSubmitTime().toString(),
                        String.valueOf(l.getQuantity())
                ));
                bw.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ✅ ✅ ✅ 终极安全读取版本（永不 NPE）
    @Override
    public List<Listing> loadAll() {

        List<Listing> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {

            String line = br.readLine(); // 跳过表头
            if (line == null) return list;

            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty()) continue;

                String[] d = line.split(",", -1);   // ✅ 允许空列

                String listingId = d.length > 0 ? d[0] : null;
                String sellerId  = d.length > 1 ? d[1] : null;
                String title     = d.length > 2 ? d[2] : "";
                String desc      = d.length > 3 ? d[3] : "";
                String imagePath = d.length > 4 && !d[4].isEmpty() ? d[4] : null;
                double price     = d.length > 5 && !d[5].isEmpty() ? Double.parseDouble(d[5]) : 0;
                String status    = d.length > 6 ? d[6] : "Pending";
                LocalDateTime time = (d.length > 7 && !d[7].isEmpty())
                        ? LocalDateTime.parse(d[7])
                        : LocalDateTime.now();
                int quantity     = d.length > 8 && !d[8].isEmpty()
                        ? Integer.parseInt(d[8])
                        : 1;

                // ✅ 关键修复点：构造一个“虚拟 Seller”，避免 getSellerId() 空指针
                UserAccount dummySeller = new UserAccount() {};
                dummySeller.setUserId(sellerId);

                Listing l = new Listing(
                        listingId,
                        dummySeller,   // ✅ 永远不为 null
                        title,
                        desc,
                        imagePath,
                        price
                );

                l.setStatus(status);
                l.setSubmitTime(time);
                l.setQuantity(quantity);

                list.add(l);
            }

        } catch (Exception e) {
            e.printStackTrace();   // ✅ 有异常你能看到真实原因
        }

        return list;
    }

    // ✅ 清空 CSV，只保留表头
    @Override
    public void clearFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            bw.write("listingId,sellerId,title,description,imagePath,price,status,submitTime,quantity");
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String safe(String s) {
        return s == null ? "" : s.replace(",", " ");
    }
}
