/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class;

import java.util.ArrayList;

/**
 *
 * @author yujie-liang
 */
public abstract class Organization {
    private String name;
    private WorkRequestDirectory workQueue; // Organization-level work queue
    private UserAccountDirectory userAccountDirectory; // Employees/Users belonging to this org
    private String organizationID;
    private static int counter = 0;

    public Organization(String name) {
        this.name = name;
        this.workQueue = new WorkRequestDirectory();
        this.userAccountDirectory = new UserAccountDirectory();
        this.organizationID = String.valueOf(++counter);
    }

    public abstract ArrayList<Role> getSupportedRole(); // Subclasses must implement this

    public UserAccountDirectory getUserAccountDirectory() {
        return userAccountDirectory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WorkRequestDirectory getWorkQueue() {
        return workQueue;
    }

    @Override
    public String toString() {
        return name;
    }
}

