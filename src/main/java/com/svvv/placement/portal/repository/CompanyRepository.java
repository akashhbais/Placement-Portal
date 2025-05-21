/**
 * 
 */
package com.svvv.placement.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.svvv.placement.portal.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {

}