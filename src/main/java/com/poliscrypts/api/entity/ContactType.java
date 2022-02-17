package com.poliscrypts.api.entity;

import com.poliscrypts.api.enumeration.ContactTypeEnum;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "CONTACT_TYPE")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ContactType {

    @Id
    @Column(name = "CONTACT_TYPE_ID")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "TYPE")
    @NotNull
    private String type;

    @OneToOne(mappedBy = "contactType")
    private Contact contact;
}
