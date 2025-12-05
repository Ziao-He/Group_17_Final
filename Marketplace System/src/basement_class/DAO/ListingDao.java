/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.DAO;

import basement_class.Enterprise_2.Listing;
import basement_class.UserAccount;
import basement_class.UserAccountDirectory;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author 心火牧神塞勒斯
 */
public class ListingDao {

    private static final String FILE_PATH = "data/listings.csv";
    private static final String DELIMITER = ",";
    private static final DateTimeFormatter DF = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private List<Listing> listings = new ArrayList<>();
    private UserAccountDirectory userAccountDirectory;

    public ListingDao(UserAccountDirectory userAccountDirectory) {
        this.userAccountDirectory = userAccountDirectory;
        loadFromCSV();
    }

    /** Save new listing */
    public boolean save(Listing listing) {
        if (listing == null || listing.getId() == null) return false;

        if (findById(listing.getId()) != null) return false;

        listings.add(listing);
        saveToCSV();
        return true;
    }

    /** Update listing */
    public boolean update(Listing listing) {
        if (listing == null || listing.getId() == null) return false;

        for (int i = 0; i < listings.size(); i++) {
            if (listings.get(i).getId().equals(listing.getId())) {
                listings.set(i, listing);
                saveToCSV();
                return true;
            }
        }
        return false;
    }

    /** Delete listing */
    public boolean delete(String id) {
        Listing l = findById(id);
        if (l != null) {
            listings.remove(l);
            saveToCSV();
            return true;
        }
        return false;
    }

    /** Find by listing id */
    public Listing findById(String id) {
        for (Listing listing : listings) {
            if (listing.getId().equals(id)) return listing;
        }
        return null;
    }

    /** Find listings by seller */
    public List<Listing> findBySellerId(String sellerId) {
        List<Listing> result = new ArrayList<>();
        for (Listing l : listings) {
            if (l.getSellerId().equals(sellerId))
                result.add(l);
        }
        return result;
    }

    /** Find by status */
    public List<Listing> findByStatus(String status) {
        List<Listing> result = new ArrayList<>();
        for (Listing l : listings) {
            if (l.getStatus().equalsIgnoreCase(status))
                result.add(l);
        }
        return result;
    }

    /** Get all listings */
    public List<Listing> getAll() {
        return new ArrayList<>(listings);
    }

    /** Count */
    public int count() {
        return listings.size();
    }

    // ---------------- CSV Loading ----------------

    private void loadFromCSV() {
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                writeHeader();
            } catch (Exception ex) {
                System.err.println("Error creating listings.csv");
            }
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            boolean first = true;

            while ((line = br.readLine()) != null) {
                if (first) {
                    first = false;
                    continue;
                }

                Listing listing = parse(line);
                if (listing != null) listings.add(listing);
            }

        } catch (Exception e) {
            System.err.println("Error loading listings.csv");
        }
    }

    private void saveToCSV() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {
            pw.println(getHeader());
            for (Listing l : listings) {
                pw.println(toCSV(l));
            }
        } catch (Exception e) {
            System.err.println("Error saving listings.csv");
        }
    }

    private void writeHeader() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {
            pw.println(getHeader());
        } catch (Exception e) {
            System.err.println("Error writing header");
        }
    }

    private String getHeader() {
        return "id,sellerId,title,description,imagePath,price,status,submitTime";
    }

    private Listing parse(String line) {
        try {
            String[] p = line.split(DELIMITER, -1);
            if (p.length < 8) return null;

            String id = p[0];
            String sellerId = p[1];

            UserAccount seller = userAccountDirectory.findByUserId(sellerId);
            if (seller == null) {
                System.err.println("Seller not found for ID = " + sellerId);
                return null;
            }

            Listing listing = new Listing(
                    id,
                    seller,
                    p[2],      // title
                    p[3],      // description
                    p[4],      // imagePath
                    Double.parseDouble(p[5])
            );

            listing.setStatus(p[6]);
            listing.setSubmitTime(LocalDateTime.parse(p[7], DF));

            return listing;

        } catch (Exception e) {
            System.err.println("Error parsing listing line: " + line);
            return null;
        }
    }

    private String toCSV(Listing l) {
        return String.join(DELIMITER,
                l.getId(),
                l.getSellerId(),
                escape(l.getTitle()),
                escape(l.getDescription()),
                escape(l.getImagePath()),
                String.valueOf(l.getPrice()),
                l.getStatus(),
                l.getSubmitTime().format(DF)
        );
    }

    private String escape(String v) {
        if (v == null) return "";
        if (v.contains(",") || v.contains("\""))
            return "\"" + v.replace("\"", "\"\"") + "\"";
        return v;
    }
}

