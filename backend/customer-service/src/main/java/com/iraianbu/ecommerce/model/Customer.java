package com.iraianbu.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document
public class Customer implements Serializable {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private Address address;
}
