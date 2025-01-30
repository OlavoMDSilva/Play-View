package com.example.play_view.publisher;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface PublisherService {
    boolean existById(long id);
    List<PublisherDTO> findAll(String order, Sort.Direction orderDir, int pageNum, int pageSize);
    List<PublisherDTO> findByAttribute(String order, Sort.Direction orderDir,
                                   int pageNum, int pageSize,
                                   String publisherName);
    List<PublisherDTO> findById(long id);
    @Transactional
    PublisherDTO savePublisher(PublisherDTO publisherDTO);
    @Transactional
    void deletePublisherById(long id);
}
