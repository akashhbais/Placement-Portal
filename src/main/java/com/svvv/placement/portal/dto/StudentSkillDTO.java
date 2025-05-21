/**
 * 
 */
package com.svvv.placement.portal.dto;

import java.util.UUID;

import com.svvv.placement.portal.model.StudentSkill;

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
public class StudentSkillDTO {
    private UUID id;
    private String skill;
    private StudentSkill.Proficiency proficiency;
}
