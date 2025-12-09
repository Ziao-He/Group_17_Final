/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.DAO;

import basement_class.EcoSystem;
import basement_class.Enterprise_3.WorkRequest.AccountStatusReviewRequest;
import basement_class.Enterprise_3.WorkRequest.ListingReviewRequest;
import basement_class.Enterprise_3.WorkRequest.PolicyViolationRequest;
import basement_class.Enterprise_3.WorkRequest.RegistrationApprovalRequest;
import basement_class.UserAccount;
import basement_class.WorkRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class WorkRequestFileDAO implements WorkRequestDAO {
       private final String filePath = "data/work_requests.csv";

    public WorkRequestFileDAO() {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
                    pw.println("id,type,senderId,receiverId,status,requestDate,resolveDate,reviewerId,reviewAction,reviewComment,extra1,extra2,extra3,extra4");
                }
                System.out.println("✅ work_requests.csv initialized.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


@Override
public List<WorkRequest> loadAll() {

    List<WorkRequest> list = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

        String line;
        boolean first = true;

        while ((line = br.readLine()) != null) {

            if (first) {
                first = false;
                continue;
            }

            if (line.trim().isEmpty()) continue;

            String[] arr = line.split(",", -1);

            String id = arr[0];
            String type = arr[1];
            String status = arr[4];

            String requestDateStr = arr[5];
            String resolveDateStr = arr[6];

            String reviewerId    = arr[7];   
            String reviewAction  = arr[8];
            String reviewComment = arr[9];

            String e1 = arr.length > 10 ? arr[10] : "";
            String e2 = arr.length > 11 ? arr[11] : "";
            String e3 = arr.length > 12 ? arr[12] : "";

            WorkRequest req = null;

            switch (type) {
                case "AccountStatusReview" -> {
                    req = new AccountStatusReviewRequest(null, e1, e2);
                }
                case "ListingReview" -> {
                    req = new ListingReviewRequest(null, e2, e3);
                }
                case "PolicyViolation" -> {
                    req = new PolicyViolationRequest(null, null, e1, e2);
                }
                case "RegistrationApproval" -> {
                    req = new RegistrationApprovalRequest(null);
                }
                default -> {
                    System.out.println("⚠ Unknown WorkRequest type: " + type);
                    continue;
                }
            }

           
            req.setId(id);
            req.setStatus(status);

            if (!requestDateStr.isEmpty()) {
                req.setRequestDate(LocalDateTime.parse(requestDateStr));
            }

            if (!resolveDateStr.isEmpty()) {
                req.setResolveDate(LocalDateTime.parse(resolveDateStr));
            }

           
            UserAccount reviewer = null;
            if (reviewerId != null && !reviewerId.isBlank()) {
                reviewer = EcoSystem.getInstance()
                        .getUserAccountDirectory()
                        .findByUserId(reviewerId);
            }

            if (!reviewAction.isEmpty() || !reviewComment.isEmpty()) {
                req.resolve(reviewer, reviewAction, reviewComment);  
            }
 

            list.add(req);
              System.out.println("DEBUG reviewerId from CSV = " + reviewerId);
            System.out.println("DEBUG resolved reviewer = " + reviewer);
        }

    } catch (Exception e) {
        System.out.println("⚠ WorkRequest load failed: " + e.getMessage());
        e.printStackTrace();
    }
    
    System.out.println("✅ WorkRequest loaded from CSV: " + list.size());
    
    return list;
}


    @Override
    public void saveAll(List<WorkRequest> list) {

        if (list == null || list.isEmpty()) {
            System.out.println("⚠ saveAll blocked: empty list, CSV will NOT be cleared.");
            return; //
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {

            pw.println("id,type,senderId,receiverId,status,requestDate,resolveDate,reviewerId,reviewAction,reviewComment,extra1,extra2,extra3,extra4");

            for (WorkRequest r : list) {
                pw.println(convertToCSV(r));
            }

            System.out.println("✅ saveAll finished, total records: " + list.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


        @Override
        public void append(WorkRequest r) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(filePath, true))) {
                pw.println(convertToCSV(r));
                System.out.println("✅ append success: " + r.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

 private String convertToCSV(WorkRequest r) {

    String type = r.getClass().getSimpleName()
            .replace("Request", "");

    String e1 = "", e2 = "", e3 = "", e4 = "";

    if (r instanceof AccountStatusReviewRequest ar) {
        e1 = ar.getAction();
        e2 = ar.getRequestDescription();
    }

    if (r instanceof ListingReviewRequest lr) {
        e1 = lr.getListing() == null ? "" : lr.getListing().getTitle();
        e2 = lr.getOperationType();
        e3 = lr.getReason();
    }

    if (r instanceof PolicyViolationRequest pv) {
        e1 = pv.getViolationCategory();
        e2 = pv.getViolationInfo();
    }

    if (r instanceof RegistrationApprovalRequest rr) {
        e1 = rr.getNewUser() == null ? "" : rr.getNewUser().getUsername();
    }

    
    String senderId =
            r.getSender() == null ? "" : r.getSender().getUserId();

    String receiverId =
            r.getReceiver() == null ? "" : r.getReceiver().getUserId();

    String reviewerId =
            r.getReviewer() == null ? "" : r.getReviewer().getUserId();

    return String.join(",",
            r.getId(),                           
            type,                                
            senderId,                            
            receiverId,                          
            r.getStatus(),                       
            r.getRequestDate().toString(),      
            r.getResolveDate() == null ? "" : r.getResolveDate().toString(),
            reviewerId,                          
            r.getReviewAction() == null ? "" : r.getReviewAction(),
            r.getReviewComment() == null ? "" : r.getReviewComment(),
            e1, e2, e3, e4
    );
}
}

