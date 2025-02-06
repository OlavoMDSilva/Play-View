package com.example.play_view.company;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CompanyService {
    boolean existById(long id);
    List<CompanyDTO> findAll(String order, Sort.Direction orderDir, int pageNum, int pageSize);
    List<CompanyDTO> findByAttribute(String order, Sort.Direction orderDir,
                                            int pageNum, int pageSize, String companyName);
    List<CompanyDTO> findById(long id);
    @Transactional
    CompanyDTO saveCompany(CompanyDTO companyDTO);
    @Transactional
    void deleteCompanyById(long id);
}
