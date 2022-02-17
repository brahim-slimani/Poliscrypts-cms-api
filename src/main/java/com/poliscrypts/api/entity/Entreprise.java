package com.poliscrypts.api.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Entreprise")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Entreprise {

    @Id
    @Column(name = "ENTREPRISE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "ADDRESS")
    private String address;

    @NotNull
    @Column(name = "TVA_NUMBER")
    private Long tvaNumber;

    @ManyToMany(mappedBy = "entreprises", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Contact> contacts = new ArrayList<>();
}
