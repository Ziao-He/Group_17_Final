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
    protected String id;
    protected UserAccount sender;
    protected UserAccount receiver;
    protected String status;
    protected LocalDateTime requestDate;
    protected LocalDateTime resolveDate;
    
    
}

