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
public class UserAccountDirectory {
    private ArrayList<UserAccount> userAccounts;
    
    public UserAccountDirectory() {
        this.userAccounts = new ArrayList<>();
    }
    
    /**
     * Add a new user account
     * @param account The account to add
     */
    public void addUserAccount(UserAccount account) {
        if (!userAccounts.contains(account)) {
            userAccounts.add(account);
        }
    }
    
    /**
     * Remove a user account
     * @param account The account to remove
     * @return true if removed, false otherwise
     */
    public boolean removeUserAccount(UserAccount account) {
        return userAccounts.remove(account);
    }
    
    /**
     * Find user by username
     * @param username Username to search for
     * @return UserAccount if found, null otherwise
     */
    public UserAccount findByUsername(String username) {
        for (UserAccount account : userAccounts) {
            if (account.getUsername().equals(username)) {
                return account;
            }
        }
        return null;
    }
    
    /**
     * Find user by user ID
     * @param userId User ID to search for
     * @return UserAccount if found, null otherwise
     */
    public UserAccount findByUserId(String userId) {
        for (UserAccount account : userAccounts) {
            if (account.getUserId().equals(userId)) {
                return account;
            }
        }
        return null;
    }
    
    /**
     * Find user by email
     * @param email Email to search for
     * @return UserAccount if found, null otherwise
     */
    public UserAccount findByEmail(String email) {
        for (UserAccount account : userAccounts) {
            if (account.getEmail().equals(email)) {
                return account;
            }
        }
        return null;
    }
    
    /**
     * Get all user accounts
     * @return List of all accounts
     */
    public ArrayList<UserAccount> getUserAccounts() {
        return userAccounts;
    }
    
    public void setUserAccounts(ArrayList<UserAccount> userAccounts) {
        this.userAccounts = userAccounts;
    }
    
    /**
     * Get count of users
     * @return Number of user accounts
     */
    public int size() {
        return userAccounts.size();
    }
}