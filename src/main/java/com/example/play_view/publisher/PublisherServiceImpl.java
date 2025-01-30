package com.example.play_view.publisher;

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
public class PublisherServiceImpl implements PublisherService{

    private PublisherRepository publisherRepository;
    private PublisherDTOMapper publisherDTOMapper;

    @Autowired
    public PublisherServiceImpl(PublisherRepository publisherRepository, PublisherDTOMapper publisherDTOMapper) {
        this.publisherRepository = publisherRepository;
        this.publisherDTOMapper = publisherDTOMapper;
    }

    @Override
    public boolean existById(long id) {
        return publisherRepository.existsById(id);
    }

    @Override
    public List<PublisherDTO> findAll(String order, Sort.Direction orderDir, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(orderDir, order));

        return publisherRepository.findAll(pageable).stream()
                .map(publisherDTOMapper)
                .toList();
    }

    @Override
    public List<PublisherDTO> findByAttribute(String order, Sort.Direction orderDir, int pageNum, int pageSize, String publisher) {

        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(orderDir, order));

        SpecificationHelper<PublisherEntity> specificationHelper = new SpecificationHelper<>();
        Specification<PublisherEntity> spec = new SpecificationBuilder<PublisherEntity>()
                .add((root, query, cb) ->
                        cb.like(root.get("publisherName"), "%" + publisher + "%"), publisher != null && !publisher.isEmpty())
                .build();

        return publisherRepository.findAll(spec, pageable).stream()
                .map(publisherDTOMapper)
                .toList();
    }

    @Override
    public List<PublisherDTO> findById(long id) {
        return publisherRepository.findById(id).stream()
                .map(publisherDTOMapper)
                .toList();
    }

    @Override
    public PublisherDTO savePublisher(PublisherDTO publisherDTO) {
        PublisherEntity publisher = publisherDTOMapper.toEntity(publisherDTO);
        PublisherEntity savedPublisher = publisherRepository.save(publisher);
        return publisherDTOMapper.apply(savedPublisher);
    }

    @Override
    public void deletePublisherById(long id) {
        publisherRepository.deleteById(id);
    }
}
