/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class;

/**
 *
 * @author yujie-liang
 */
import javax.swing.JPanel;
public abstract class Role {
    public abstract JPanel createWorkArea(UserAccount useraccount, Organization org, Enterprise ent, EcoSystem system);
}

