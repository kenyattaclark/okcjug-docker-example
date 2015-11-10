package com.box20six.docker.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "registration")
public class RegistrationDocument {

    @Id
    private String id;

    private String firstName;
    private String lastName;
    private String emailAddress;

}
