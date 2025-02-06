package com.example.play_view.company;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public record CompanyController(CompanyServiceImpl companyService) {

    @Autowired
    public CompanyController(CompanyServiceImpl companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public List<CompanyDTO> findAllCompanies(@RequestParam(name = "orderBy", defaultValue = "companyId", required = false) String order,
                                             @RequestParam(name = "orderDirection", defaultValue = "asc", required = false) String orderDir,
                                             @RequestParam(name = "pageNum", defaultValue = "0", required = false) int pageNum,
                                             @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {

        Sort.Direction direction = orderDir.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        return companyService.findAll(order, direction, pageNum, pageSize);
    }

    @GetMapping("/{id}")
    public List<CompanyDTO> findCompanyById(@PathVariable long id) {
        return companyService.findById(id);
    }

    @GetMapping("/filters")
    public List<CompanyDTO> findCompaniesByAttribute(@RequestParam(name = "orderBy", defaultValue = "companyId", required = false) String order,
                                                     @RequestParam(name = "orderDirection", defaultValue = "asc", required = false) String orderDir,
                                                     @RequestParam(name = "pageNum", defaultValue = "0", required = false) int pageNum,
                                                     @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize,
                                                     @RequestParam(name = "companyName", defaultValue = "", required = false) String companyName) {

        Sort.Direction direction = orderDir.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        return companyService.findByAttribute(order, direction, pageNum, pageSize, companyName);
    }

    @PostMapping
    public CompanyDTO createCompany(@Valid @RequestBody CompanyDTO companyDTO) {
        return companyService.saveCompany(companyDTO);
    }

    @PutMapping("/{id}")
    public CompanyDTO updateCompany(@RequestBody CompanyDTO companyDTO, @PathVariable long id) {
        CompanyDTO updatedCompany = new CompanyDTO(id, companyDTO.companyName());
        return companyService.saveCompany(updatedCompany);
    }

    @DeleteMapping("/{id}")
    public String deleteCompanyById(@PathVariable long id) {
        companyService.deleteCompanyById(id);
        return "Company with Id: " + id + " successfully deleted";
    }

}



