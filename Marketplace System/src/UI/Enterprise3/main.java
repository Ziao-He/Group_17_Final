/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.Enterprise3;

import UI.main.Enterprise3Initializer;
import basement_class.EcoSystem;
import basement_class.Enterprise;
import basement_class.Network;
import basement_class.Organization;
import basement_class.UserAccount;
import javax.swing.JFrame;

/**
 *
 * @author Administrator
 */
public class main {
     public static void main(String[] args) {

        // A. 初始化系统
        EcoSystem system = EcoSystem.getInstance();

        // B. 初始化 Enterprise3（会自动创建 admin / 123）
        Enterprise3Initializer.initialize(system);

        // C. 获取 admin 用户
        UserAccount admin = system.getUserAccountDirectory().findByUsername("admin");

        // D. 获取平台管理 Enterprise（AdminJPanel 需要一个 enterprise）
        Network net = system.getNetworks().get(0);
        Enterprise enterprise = net.getEnterpriseByName("Platform Management");

        // E. 创建 JFrame 主窗口
        JFrame frame = new JFrame("Platform Management — Admin Panel Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 800);

        // ⚠️ 注意：AdminJPanel 需要一个组织，这里给它 Enterprise 的第一个组织
        Organization org = enterprise.getOrganizations().get(0);

        AdminJPanel panel = new AdminJPanel(system, admin, enterprise, org);

        frame.setContentPane(panel);
        frame.setVisible(true);

        System.out.println("[Main] Admin UI 已成功启动！");
    }
}
