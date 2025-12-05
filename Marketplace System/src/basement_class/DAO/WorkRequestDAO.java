/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class.DAO;

import basement_class.EcoSystem;
import basement_class.Enterprise_2.Listing;
import basement_class.Enterprise_3.WorkRequest.AccountStatusReviewRequest;
import basement_class.Enterprise_3.WorkRequest.ListingReviewRequest;
import basement_class.Enterprise_3.WorkRequest.PolicyViolationRequest;
import basement_class.Enterprise_3.WorkRequest.RegistrationApprovalRequest;
import basement_class.UserAccount;
import basement_class.UserAccountDirectory;
import basement_class.WorkRequest;
import basement_class.WorkRequestDirectory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class WorkRequestDAO {
     private static final String FILE_PATH = "data/workrequests.csv";

    /** 保存所有 WorkRequest */
    public void save(WorkRequestDirectory dir) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {

            pw.println(
                "id,type,status,senderId,receiverId,resolved," +
                "targetUserId,newUserId,listingId,action,reqDesc,reviewerReason," +
                "violCat,violInfo,evidencePaths," +
                "operationType,reason,rejectionReason"
            );

            for (WorkRequest r : dir.getRequestList()) {

                String type = r.getClass().getSimpleName();

                String senderId = r.getSender() == null ? "" : r.getSender().getUserId();
                String receiverId = r.getReceiver() == null ? "" : r.getReceiver().getUserId();

                // ========== 通用字段 ==========
                String id = r.getId();
                String status = r.getStatus();
                String resolved = String.valueOf(r.isResolved());

                // ========== 子类字段初始化 ==========
                String targetUser = "";
                String newUser = "";
                String listingId = "";
                String action = "";
                String reqDesc = "";
                String reviewerReason = "";
                String vCat = "";
                String vInfo = "";
                String evidence = "";
                String opType = "";
                String reason = "";
                String rejectionReason = "";

                // ========== 根据子类提取字段 ==========
                if (r instanceof AccountStatusReviewRequest req) {
                    targetUser = req.getTargetUser().getUserId();
                    action = req.getAction();
                    reqDesc = req.getRequestDescription();
                    reviewerReason = req.getReviewerDecisionReason();

                } else if (r instanceof RegistrationApprovalRequest req) {
                    newUser = req.getNewUser().getUserId();

                } else if (r instanceof PolicyViolationRequest req) {
                    targetUser = req.getTargetUser().getUserId();
                    vCat = req.getViolationCategory();
                    vInfo = req.getViolationInfo();

                    if (req.getListing() != null) {
                        listingId = req.getListing().getId();
                    }

                    evidence = String.join("|", req.getEvidencePaths());

                } else if (r instanceof ListingReviewRequest req) {
                    listingId = req.getListing().getId();
                    opType = req.getOperationType();
                    reason = req.getReason();
                    rejectionReason = req.getRejectionReason();
                }

                // ========== 输出到 CSV ==========
                pw.println(
                    id + "," + type + "," + status + "," +
                    senderId + "," + receiverId + "," + resolved + "," +
                    targetUser + "," + newUser + "," + listingId + "," +
                    action + "," + reqDesc + "," + reviewerReason + "," +
                    vCat + "," + vInfo + "," + evidence + "," +
                    opType + "," + reason + "," + rejectionReason
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 加载（需要 system 提供 account 和 listing 查询） */
    public WorkRequestDirectory load(EcoSystem system) {

        WorkRequestDirectory dir = new WorkRequestDirectory();
        File file = new File(FILE_PATH);

        if (!file.exists()) return dir;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {

            String line = br.readLine(); // skip header

            while ((line = br.readLine()) != null) {
                String[] p = line.split(",", -1);

                String id = p[0];
                String type = p[1];
                String status = p[2];
                boolean resolved = Boolean.parseBoolean(p[5]);

                // 解析关联对象
                UserAccount sender = system.getUserAccountDirectory().findByUserId(p[3]);
                UserAccount receiver = system.getUserAccountDirectory().findByUserId(p[4]);
                UserAccount target = system.getUserAccountDirectory().findByUserId(p[6]);
                UserAccount newUser = system.getUserAccountDirectory().findByUserId(p[7]);
                Listing listing = system.findListingById(p[8]); 

                WorkRequest req = null;

                // ========== 根据类型构造对象 ==========
                switch (type) {

                    case "AccountStatusReviewRequest" -> {
                        req = new AccountStatusReviewRequest(
                                target, p[9], p[10]
                        );
                        ((AccountStatusReviewRequest) req)
                                .setReviewerDecisionReason(p[11]);
                    }

                    case "RegistrationApprovalRequest" -> {
                        req = new RegistrationApprovalRequest(newUser);
                    }

                    case "PolicyViolationRequest" -> {
                        req = new PolicyViolationRequest(
                                sender, target, listing,
                                p[12], p[13]
                        );
                        String[] ev = p[14].split("\\|");
                        for (String epath : ev) {
                            if (!epath.isBlank()) {
                                ((PolicyViolationRequest) req).addEvidencePath(epath);
                            }
                        }
                    }

                    case "ListingReviewRequest" -> {
                        req = new ListingReviewRequest(
                                listing, p[15], p[16]
                        );
                        ((ListingReviewRequest) req).setRejectionReason(p[17]);
                    }
                }

                if (req == null) continue;

                // 设置通用属性
                req.setId(id);
                req.setStatus(status);
                req.setSender(sender);
                req.setReceiver(receiver);

                if (resolved) req.resolve();
                
                dir.addWorkRequest(req);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dir;
    }
}
