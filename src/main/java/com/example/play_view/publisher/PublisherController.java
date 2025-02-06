package com.example.play_view.publisher;

import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publishers")
public record PublisherController(PublisherServiceImpl publisherService) {

    @GetMapping
    public List<PublisherDTO> findAllCompanies(@RequestParam(name = "orderBy", defaultValue = "publisherId", required = false) String order,
                                           @RequestParam(name = "orderDirection", defaultValue = "asc", required = false) String orderDir,
                                           @RequestParam(name = "pageNum", defaultValue = "0", required = false) int pageNum,
                                           @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {

        Sort.Direction direction = orderDir.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        return publisherService.findAll(order, direction, pageNum, pageSize);
    }

    @GetMapping("/{id}")
    public List<PublisherDTO> findPublisherById(@PathVariable long id) {
        return publisherService.findById(id);
    }

    @GetMapping("/filters")
    public List<PublisherDTO> findCompaniesByAttribute(@RequestParam(name = "orderBy", defaultValue = "publisherId", required = false) String order,
                                                   @RequestParam(name = "orderDirection", defaultValue = "asc", required = false) String orderDir,
                                                   @RequestParam(name = "pageNum", defaultValue = "0", required = false) int pageNum,
                                                   @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize,
                                                   @RequestParam(name = "publisher", defaultValue = "", required = false) String publisher) {

        Sort.Direction direction = orderDir.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        return publisherService.findByAttribute(order, direction, pageNum, pageSize, publisher);
    }

    @PostMapping
    public PublisherDTO createPublisher(@Valid @RequestBody PublisherDTO publisherDTO) {
        return publisherService.savePublisher(publisherDTO);
    }

    @PutMapping("/{id}")
    public PublisherDTO updatePublisher(@RequestBody PublisherDTO publisherDTO, @PathVariable long id) {
        PublisherDTO updatedPublisher = new PublisherDTO(id, publisherDTO.publisherName());
        return publisherService.savePublisher(updatedPublisher);
    }

    @DeleteMapping("/{id}")
    public String deletePublisherById(@PathVariable long id) {
        publisherService.deletePublisherById(id);
        return "Publisher with Id: " + id + " successfully deleted";
    }

}
