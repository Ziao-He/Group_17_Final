/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class;

import javax.swing.JPanel;

/**
 *
 * @author Administrator
 */
public class RegistrationReviewerRole extends Role  {

    @Override
    public JPanel createWorkArea(UserAccount userAccount, 
                                 Organization organization,
                                 Enterprise enterprise,
                                 EcoSystem system) {
//        return new RegistrationReviewerWorkAreaPanel(
//                userAccount, organization, enterprise, system
//        );
          return null;
    }

    @Override
    public String getRoleName() {
        return "Account Reviewer";
    }
}
    

