package com.example.play_view.company;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CompanyDTOMapper implements Function<CompanyEntity, CompanyDTO> {

    @Override
    public CompanyDTO apply(CompanyEntity companyEntity) {
        return new CompanyDTO(
                companyEntity.getCompanyId(),
                companyEntity.getCompanyName()
        );
    }

    public CompanyEntity toEntity(CompanyDTO companyDTO) {
        CompanyEntity company = new CompanyEntity();

        if (company.getCompanyId() < 0) {
            company.setCompanyId(0);
        }else {
            company.setCompanyId(companyDTO.companyId());
        }

        company.setCompanyName(companyDTO.companyName());
        return company;
    }

}
