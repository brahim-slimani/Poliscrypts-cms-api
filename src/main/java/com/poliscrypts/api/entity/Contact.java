package com.poliscrypts.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.poliscrypts.api.enumeration.ContactTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Table(name = "CONTACT")
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

    @Id
    @JsonIgnore
    @Column(name = "CONTACT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "UUID", unique = true, updatable = false)
    private UUID uuid;

    @NotBlank(message = "firstName is required")
    @Column(name = "FIRSTANME")
    private String firstName;

    @NotBlank(message = "lastName is required")
    @Column(name = "LASTNAME")
    private String lastName;

    @Column(name = "ADDRESS")
    private String address;

    @NotNull(message = "contactType is required & must take value as: EMPLOYEE | FREELANCER")
    @Enumerated(EnumType.STRING)
    private ContactTypeEnum contactType;

    @Column(nullable = true)
    private Long tvaNumber;

    @Column(name = "CREATED_AT", updatable = false)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date createdAt;

    @Column(name = "UPDATED_AT")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date updatedAt;

    @ManyToMany
    @JoinTable(
            name = "CONTACTS_COMPANIES",
            joinColumns = {@JoinColumn(name = "CONTACT_ID", referencedColumnName = "CONTACT_ID")},
            inverseJoinColumns = {@JoinColumn(name = "COMPANY_ID", referencedColumnName = "COMPANY_ID")})
    @JsonIgnoreProperties("contacts")
    private Set<Company> companies = new HashSet<>();

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
