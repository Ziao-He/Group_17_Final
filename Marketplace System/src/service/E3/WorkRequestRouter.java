/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.E3;

import basement_class.EcoSystem;
import basement_class.Enterprise;
import basement_class.Enterprise_3.Organization.ContentControlOrganization;
import basement_class.Enterprise_3.Organization.UserControlOrganization;
import basement_class.Enterprise_3.WorkRequest.AccountStatusReviewRequest;
import basement_class.Enterprise_3.WorkRequest.ListingReviewRequest;
import basement_class.Enterprise_3.WorkRequest.PolicyViolationRequest;
import basement_class.Enterprise_3.WorkRequest.RegistrationApprovalRequest;
import basement_class.Network;
import basement_class.Organization;
import basement_class.WorkRequest;

/**
 *
 * @author Administrator
 */
public class WorkRequestRouter {

    public static void routeToEnterprise3(WorkRequest req) {

        EcoSystem system = EcoSystem.getInstance(); 

        for (Network n : system.getNetworks()) {
            for (Enterprise e : n.getEnterprises()) {

                if (!"Platform Management".equals(e.getName())) {
                    continue;
                }

                for (Organization org : e.getOrganizations()) {

                    if ((req instanceof AccountStatusReviewRequest
                            || req instanceof RegistrationApprovalRequest)
                            && org instanceof UserControlOrganization) {

                        org.getWorkRequestDirectory().addWorkRequest(req);
                        return;
                    }

                    if ((req instanceof ListingReviewRequest
                            || req instanceof PolicyViolationRequest)
                            && org instanceof ContentControlOrganization) {

                        org.getWorkRequestDirectory().addWorkRequest(req);
                        return;
                    }
                }
            }
        }

        System.out.println("⚠ WorkRequestRouter: no matching Organization found for "
                + req.getClass().getSimpleName());
    }
}
