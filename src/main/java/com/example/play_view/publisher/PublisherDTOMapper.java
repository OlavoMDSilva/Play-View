package com.example.play_view.publisher;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PublisherDTOMapper implements Function<PublisherEntity, PublisherDTO> {
    @Override
    public PublisherDTO apply(PublisherEntity publisherEntity) {
        return new PublisherDTO(
                publisherEntity.getPublisherId(),
                publisherEntity.getPublisherName()
        );
    }

    public PublisherEntity toEntity(PublisherDTO publisherDTO) {
        PublisherEntity publisher = new PublisherEntity();

        if (publisher.getPublisherId() < 0) {
            publisher.setPublisherId(0);
        }else {
            publisher.setPublisherId(publisherDTO.publisherId());
        }

        publisher.setPublisherName(publisherDTO.publisherName());
        return publisher;
    }

}
