package com.poliscrypts.api;

import com.poliscrypts.api.entity.*;
import com.poliscrypts.api.enumeration.ContactTypeEnum;
import com.poliscrypts.api.enumeration.RoleEnum;
import com.poliscrypts.api.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.HashSet;

@Slf4j
@SpringBootApplication
public class ContactManagementApiApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ContactManagementApiApplication.class, args);
    }

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    CompanyRepository compnayRepository;

    @Autowired
    ContactTypeRepository contactTypeRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    /**
     * Initailize CONTACT & COMPANY Tables with few rows in runnable thread
     *
     * @param args
     */
    @Override
    public void run(String... args) {

        Company company0 = Company.builder().address("PoliScrypts").tvaNumber(Long.valueOf(4349023)).build();
        Company company1 = Company.builder().address("Ooredoo Algérie").tvaNumber(Long.valueOf(9054054)).build();
        compnayRepository.saveAll(Arrays.asList(company0, company1));

        Arrays.asList(ContactTypeEnum.values()).forEach((type) -> {
            ContactType contactType = ContactType.builder().type(type).build();
            contactTypeRepository.save(contactType);
        });

        Contact contact0 = Contact.builder().firstName("Brahim").lastName("SLIMANI").address("Algiers").contactType(contactTypeRepository.findByType(ContactTypeEnum.EMPLOYEE)).tvaNumber(Long.valueOf(1234)).build();
        Contact contact1 = Contact.builder().firstName("Ahmed").lastName("Amine").address("Oran").contactType(contactTypeRepository.findByType(ContactTypeEnum.FREELANCER)).build();
        Contact contact2 = Contact.builder().firstName("Mohamed").lastName("Ali").address("Algiers").contactType(contactTypeRepository.findByType(ContactTypeEnum.FREELANCER)).build();

        contact0.setCompanies(Arrays.asList(company0, company1));
        contact1.setCompanies(Arrays.asList(company0));
        contactRepository.saveAll(Arrays.asList(contact0, contact1, contact2));

        Arrays.asList(RoleEnum.values()).forEach((item)->{
            Role role = Role.builder().name(item).build();
            roleRepository.save(role);
        });

    }
}
