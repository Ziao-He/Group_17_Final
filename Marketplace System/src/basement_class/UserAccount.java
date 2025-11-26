/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basement_class;

import java.time.LocalDateTime;

/**
 *
 * @author yujie-liang
 */
public abstract class UserAccount {
    // Basic account information
    private String userId;
    private String username;
    private String email;
    private String passwordHash;      // Store hashed password, not plain text
    private String phoneNumber;
    private String organizationId;    // Which organization this user belongs to
    private String status;            // "ACTIVE", "SUSPENDED", "PENDING"
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
    
    public UserAccount() {
        this.createdAt = LocalDateTime.now();
        this.status = "PENDING";  // Default status
    }
    
    /**
     * Authenticate user login
     * @param password Plain text password to verify
     * @return true if password matches, false otherwise
     */
    public boolean authenticate(String password) {
        // TODO: Implement password hashing comparison
        return this.passwordHash.equals(hashPassword(password));
    }
    
    /**
     * Hash password (placeholder - implement proper hashing)
     * @param password Plain text password
     * @return Hashed password
     */
    private String hashPassword(String password) {
        // TODO: Implement proper hashing (e.g., BCrypt, SHA-256)
        return password; // TEMPORARY - replace with real hashing
    }
    
    /**
     * Check if account is active
     * @return true if status is ACTIVE
     */
    public boolean isActive() {
        return "ACTIVE".equals(this.status);
    }
    
    /**
     * Update last login timestamp
     */
    public void recordLogin() {
        this.lastLoginAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPasswordHash() {
        return passwordHash;
    }
    
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getOrganizationId() {
        return organizationId;
    }
    
    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Role getRole() {
        return role;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }
    
    public void setLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }
}
