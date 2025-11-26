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
public abstract class WorkRequest{
    protected UserAccount sender;
    protected UserAccount receiver;
    protected String status;
    protected LocalDateTime resolveDate;
    protected String message; // Message field for additional notes (e.g., buyer instructions)

    public WorkRequest() {
        super(); // Call super to generate ID
        this.status = "PENDING"; // Default status
    }

    // Getters and Setters
    public UserAccount getSender() { 
        return sender; 
    }
    
    public void setSender(UserAccount sender) { 
        this.sender = sender; 
    }

    public UserAccount getReceiver() { 
        return receiver; 
    }
    
    public void setReceiver(UserAccount receiver) { 
        this.receiver = receiver; 
    }

    public String getStatus() { 
        return status; 
    }
    
    public void setStatus(String status) { 
        this.status = status; 
    }

    public LocalDateTime getResolveDate() { 
        return resolveDate; 
    }
    
    public void setResolveDate(LocalDateTime resolveDate) { 
        this.resolveDate = resolveDate; 
    }
    
    public String getMessage() { 
        return message; 
    }
    
    public void setMessage(String message) { 
        this.message = message; 
    }
}

