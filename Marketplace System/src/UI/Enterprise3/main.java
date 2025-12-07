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
import java.util.Locale;
import javax.swing.JFrame;

/**
 *
 * @author Administrator
 */
public class main {
    public static void main(String[] args) {
         Locale.setDefault(Locale.US);

        // A. 初始化系统
        EcoSystem system = EcoSystem.getInstance();

        // B. 初始化 Enterprise3（创建 User Control / Content Control 组织等）
        Enterprise3Initializer.initialize(system);

        // ✅ C. 生成 Fake 用户（关键：必须在取用户之前）
        FakeDataGenerator.generate(system,
                system.getNetworks().get(0)
                      .getEnterpriseByName("Platform Management"));

        // ✅ D. 从 Fake 数据中获取【总平台管理员】
        UserAccount admin = system.getUserAccountDirectory().findByUsername("userC");
        // 你也可以换成 userA / userB 来测试不同权限

        // E. 获取平台管理 Enterprise
        Network net = system.getNetworks().get(0);
        Enterprise enterprise = net.getEnterpriseByName("Platform Management");

        // F. 创建 JFrame 主窗口
        JFrame frame = new JFrame("Platform Management — Admin Panel Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 800);

        // ⚠️ 组织参数对总管理员无所谓，可以传 null
        Organization org = null;

        AdminJPanel panel = new AdminJPanel(system, admin, enterprise, org);

        frame.setContentPane(panel);
        frame.setVisible(true);

        System.out.println("[Main] Admin UI 已成功启动！");
        System.out.println("[Main] 当前登录用户 = " + admin.getUsername());
        System.out.println("[Main] 当前角色 = " + admin.getRole().getRoleName());
    }

}
