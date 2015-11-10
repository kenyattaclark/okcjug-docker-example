package com.box20six.docker.repository.search;

import com.box20six.docker.domain.RegistrationDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface RegistrationSearchRepository extends ElasticsearchRepository<RegistrationDocument, String> {}
