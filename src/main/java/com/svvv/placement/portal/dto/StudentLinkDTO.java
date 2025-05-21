/**
 * 
 */
package com.svvv.placement.portal.dto;

import java.util.UUID;

import com.svvv.placement.portal.model.StudentLink;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Akash Bais
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentLinkDTO {
    private UUID id;
    private StudentLink.LinkType type;
    private String url;
}
