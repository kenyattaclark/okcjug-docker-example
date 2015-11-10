package com.box20six.docker.repository;

import com.box20six.docker.domain.RegistrationDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RegistrationRepository extends MongoRepository<RegistrationDocument, String> {}
