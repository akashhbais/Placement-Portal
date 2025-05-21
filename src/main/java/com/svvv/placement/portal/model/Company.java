/**
 * 
 */
package com.svvv.placement.portal.model;

/**
 * @author Akash Bais
 *
 */

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String name;
    private String packageOffered;
    private String criteria;
    private String roles;
    private int vacancies;
 
}