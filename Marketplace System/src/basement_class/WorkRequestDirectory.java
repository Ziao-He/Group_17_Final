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
public class WorkRequestDirectory {
    private ArrayList<WorkRequest> requestList;
    
    public WorkRequestDirectory() {
        this.requestList = new ArrayList<>();
    }
    
    /**
     * Add a new work request
     * @param request The request to add
     */
    public void addWorkRequest(WorkRequest request) {
        if (!requestList.contains(request)) {
            requestList.add(request);
        }
    }
    
    /**
     * Remove a work request
     * @param request The request to remove
     * @return true if removed, false otherwise
     */
    public boolean removeWorkRequest(WorkRequest request) {
        return requestList.remove(request);
    }
    
    /**
     * Find work request by ID
     * @param id Request ID
     * @return WorkRequest if found, null otherwise
     */
    public WorkRequest findById(String id) {
        for (WorkRequest request : requestList) {
            if (request.getId().equals(id)) {
                return request;
            }
        }
        return null;
    }
    
    /**
     * Get all work requests for a specific receiver
     * @param receiver The receiver user account
     * @return List of work requests for this receiver
     */
    public ArrayList<WorkRequest> getRequestsForReceiver(UserAccount receiver) {
        ArrayList<WorkRequest> result = new ArrayList<>();
        for (WorkRequest request : requestList) {
            if (request.getReceiver() != null && 
                request.getReceiver().equals(receiver)) {
                result.add(request);
            }
        }
        return result;
    }
    
    /**
     * Get all work requests sent by a specific sender
     * @param sender The sender user account
     * @return List of work requests from this sender
     */
    public ArrayList<WorkRequest> getRequestsBySender(UserAccount sender) {
        ArrayList<WorkRequest> result = new ArrayList<>();
        for (WorkRequest request : requestList) {
            if (request.getSender() != null && 
                request.getSender().equals(sender)) {
                result.add(request);
            }
        }
        return result;
    }
    
    /**
     * Get all pending work requests (not resolved)
     * @return List of pending requests
     */
    public ArrayList<WorkRequest> getPendingRequests() {
        ArrayList<WorkRequest> result = new ArrayList<>();
        for (WorkRequest request : requestList) {
            if (!request.isResolved()) {
                result.add(request);
            }
        }
        return result;
    }
    
    /**
     * Get all work requests by status
     * @param status Status to filter by
     * @return List of matching work requests
     */
    public ArrayList<WorkRequest> getRequestsByStatus(String status) {
        ArrayList<WorkRequest> result = new ArrayList<>();
        for (WorkRequest request : requestList) {
            if (status.equals(request.getStatus())) {
                result.add(request);
            }
        }
        return result;
    }
    
    // Getters and Setters
    public ArrayList<WorkRequest> getRequestList() {
        return requestList;
    }
    
    public void setRequestList(ArrayList<WorkRequest> requestList) {
        this.requestList = requestList;
    }
    
    /**
     * Get count of requests
     * @return Number of work requests
     */
    public int size() {
        return requestList.size();
    }
}
