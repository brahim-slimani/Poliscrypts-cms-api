package com.poliscrypts.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@Entity
@Table(name = "Company")
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    @JsonIgnore
    @Column(name = "COMPANY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "UUID", unique = true, updatable = false)
    private UUID uuid;

    @NotNull(message = "tvaNumber is required")
    @Column(name = "TVA_NUMBER", unique = true)
    private Long tvaNumber;

    @NotBlank(message = "adress is required")
    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "CREATED_AT", updatable = false)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date createdAt;

    @Column(name = "UPDATED_AT")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date updatedAt;

    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("companies")
    @ManyToMany(mappedBy = "companies", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Contact> contacts = new HashSet<>();

    @PrePersist
    public void prePersist() {
        this.createdAt = new Date();
        this.uuid = java.util.UUID.randomUUID();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = new Date();
    }

}
