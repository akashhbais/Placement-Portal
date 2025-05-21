package com.svvv.placement.portal.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.svvv.placement.portal.model.Company;
import com.svvv.placement.portal.repository.CompanyRepository;

@Service
public class CompanyService {
	private final CompanyRepository companyRepository;

	public CompanyService(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}

	public List<Company> getAllCompanies() {
		return companyRepository.findAll();
	}

	public Company addCompany(Company company) {
		return companyRepository.save(company);
	}

	public void deleteCompany(Long id) {
		companyRepository.deleteById(id);
	}
}