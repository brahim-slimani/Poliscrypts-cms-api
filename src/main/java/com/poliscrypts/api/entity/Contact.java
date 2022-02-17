package com.poliscrypts.api.entity;

import lombok.*;

import javax.persistence.*;
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

    @NotNull
    @Column(name = "FIRSTANME")
    private String firstName;

    @NotNull
    @Column(name = "LASTNAME")
    private String lastName;

    @Column(name = "ADDRESS")
    private String address;

    @ManyToMany
    @JoinTable(
            name = "CONTACT_ENTREPRISES",
            joinColumns = {@JoinColumn(name = "CONTACT_ID", referencedColumnName = "CONTACT_ID")},
            inverseJoinColumns = {@JoinColumn(name="ENTREPRISE_ID", referencedColumnName = "ENTREPRISE_ID")})
    private List<Entreprise> entreprises = new ArrayList<>();
}
