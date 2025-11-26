/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class;

/**
 *
 * @author yujie-liang
 * Class representing a specific user account.
 * Responsibilities: Authenticate users, hold role information, and manage personal work queue.
 */
public class UserAccount {
    private String username;
    private String password;
    private Role role;
    private WorkRequestDirectory workQueue; // Updated type to match Organization's standard
    
    public UserAccount(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.workQueue = new WorkRequestDirectory(); // Initialize the directory
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public WorkRequestDirectory getWorkQueue() {
        return workQueue;
    }

    @Override
    public String toString() {
        return username;
    }
}