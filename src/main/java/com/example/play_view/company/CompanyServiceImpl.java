package com.example.play_view.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import utility.SpecificationBuilder;
import utility.SpecificationHelper;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository;
    private CompanyDTOMapper companyDTOMapper;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, CompanyDTOMapper companyDTOMapper) {
        this.companyRepository = companyRepository;
        this.companyDTOMapper = companyDTOMapper;
    }

    @Override
    public boolean existById(long id) {
        return companyRepository.existsById(id);
    }

    @Override
    public List<CompanyDTO> findAll(String order, Sort.Direction orderDir, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(orderDir, order));

        return companyRepository.findAll(pageable).stream()
                .map(companyDTOMapper)
                .toList();
    }

    @Override
    public List<CompanyDTO> findByAttribute(String order, Sort.Direction orderDir, int pageNum, int pageSize, String companyName) {

        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(orderDir, order));

        SpecificationHelper<CompanyEntity> specificationHelper = new SpecificationHelper<>();
        Specification<CompanyEntity> spec = new SpecificationBuilder<CompanyEntity>()
                .add((root, query, cb) ->
                        cb.like(root.get("companyName"), "%" + companyName + "%"), companyName != null && !companyName.isEmpty())
                .build();

        return companyRepository.findAll(spec, pageable).stream()
                .map(companyDTOMapper)
                .toList();
    }

    @Override
    public List<CompanyDTO> findById(long id) {
        return companyRepository.findById(id).stream()
                .map(companyDTOMapper)
                .toList();
    }

    @Override
    public CompanyDTO saveCompany(CompanyDTO companyDTO) {
        CompanyEntity company = companyDTOMapper.toEntity(companyDTO);
        CompanyEntity savedCompany = companyRepository.save(company);
        return companyDTOMapper.apply(savedCompany);
    }

    @Override
    public void deleteCompanyById(long id) {
        companyRepository.deleteById(id);
    }

    public CompanyDTO findByName(String company) {
        return companyDTOMapper.apply(companyRepository.findByCompanyName(company));
    }
}
