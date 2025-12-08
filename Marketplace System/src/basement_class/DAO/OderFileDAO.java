/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.DAO;

import common_class.Order;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class OderFileDAO implements OderDAO{
     private static final String FILE_PATH = "data/orders.csv";

    public OderFileDAO() {
        initFile();
    }

    // ✅ 初始化文件（带表头）
    private void initFile() {
        try {
            File file = new File(FILE_PATH);

            if (!file.exists()) {
                file.getParentFile().mkdirs();
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                bw.write("orderId,listingId,buyerId,sellerId,totalPrice,quantity,status,deliveryMethod,meetingLocation,isReviewed,buyerRating");
                bw.newLine();
                bw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ✅ 保存全部 Orders
    @Override
    public void saveAll(List<Order> orders) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {

            // 表头
            bw.write("orderId,listingId,buyerId,sellerId,totalPrice,quantity,status,deliveryMethod,meetingLocation,isReviewed,buyerRating");
            bw.newLine();

            for (Order o : orders) {
                bw.write(String.join(",",
                        safe(o.getOrderId()),
                        safe(o.getListingId()),
                        safe(o.getBuyerId()),
                        safe(o.getSellerId()),
                        String.valueOf(o.getTotalPrice()),
                        String.valueOf(o.getQuantity()),
                        safe(o.getStatus()),
                        safe(o.getDeliveryMethod()),
                        safe(o.getMeetingLocation()),
                        String.valueOf(o.isReviewed()),
                        String.valueOf(o.getBuyerRating())
                ));
                bw.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ✅ 读取全部 Orders
    @Override
    public List<Order> loadAll() {
        List<Order> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {

            String line = br.readLine(); // 跳过表头

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1);

                Order o = new Order();
                o.setOrderId(data[0]);
                o.setListingId(data[1]);
                o.setBuyerId(data[2]);
                o.setSellerId(data[3]);
                o.setTotalPrice(Double.parseDouble(data[4]));
                o.setQuantity(Integer.parseInt(data[5]));
                o.setStatus(data[6]);
                o.setDeliveryMethod(data[7]);
                o.setMeetingLocation(data[8]);
                o.setReviewed(Boolean.parseBoolean(data[9]));
                o.setBuyerRating(Integer.parseInt(data[10]));

                list.add(o);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void clearFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            bw.write("orderId,listingId,buyerId,sellerId,totalPrice,quantity,status,deliveryMethod,meetingLocation,isReviewed,buyerRating");
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String safe(String s) {
        return s == null ? "" : s;
    }
    
}
