package com.poliscrypts.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CONTACT")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Contact {

    @Id
    @Column(name = "CONTACT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "firstName is required")
    @Column(name = "FIRSTANME", nullable = false)
    private String firstName;

    @NotBlank(message = "lastName is required")
    @Column(name = "LASTNAME")
    private String lastName;

    @Column(name = "ADDRESS")
    private String address;

    @OneToOne
    @NotNull(message = "contactType is required")
    @JoinColumn(name = "CONTACT_TYPE_ID", referencedColumnName = "CONTACT_TYPE_ID")
    private ContactType contactType;

    @Column(nullable = true)
    private Long tvaNumber;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "CONTACT_COMPANIES",
            joinColumns = {@JoinColumn(name = "CONTACT_ID", referencedColumnName = "CONTACT_ID")},
            inverseJoinColumns = {@JoinColumn(name = "COMPANY_ID", referencedColumnName = "COMPANY_ID")})
    private List<Company> companies = new ArrayList<>();

}
