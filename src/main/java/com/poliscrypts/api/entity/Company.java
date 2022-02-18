package com.poliscrypts.api.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Company")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Company {

    @Id
    @Column(name = "COMPANY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "tvaNumber is required")
    @Column(name = "TVA_NUMBER", unique = true)
    private Long tvaNumber;

    @NotBlank(message = "adress is required")
    @Column(name = "ADDRESS")
    private String address;

    @ManyToMany(mappedBy = "companies", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Contact> contacts = new ArrayList<>();
}
