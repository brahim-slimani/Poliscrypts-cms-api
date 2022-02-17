package com.poliscrypts.api;

import com.poliscrypts.api.entity.Contact;
import com.poliscrypts.api.entity.Entreprise;
import com.poliscrypts.api.enumeration.ContactType;
import com.poliscrypts.api.repository.ContactRepository;
import com.poliscrypts.api.repository.EntrepriseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Arrays;

@SpringBootApplication
public class ApiApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    EntrepriseRepository entrepriseRepository;

    /**
     * Initailize CONTACT & ENTREPRISE Tables with few rows in runnable thread
     *
     * @param args
     */
    @Override
    public void run(String... args) {
        Entreprise entreprise0 = Entreprise.builder().address("PoliScrypts").tvaNumber(Long.valueOf(4349023)).build();
        Entreprise entreprise1 = Entreprise.builder().address("Ooredoo Algérie").tvaNumber(Long.valueOf(9054054)).build();
        entrepriseRepository.saveAll(Arrays.asList(entreprise0, entreprise1));

        Contact contact0 = Contact.builder().firstName("Brahim").lastName("SLIMANI").address("Algiers").contactType(ContactType.EMPLOYEE).tvaNumber(Long.valueOf(1234)).build();
        Contact contact1 = Contact.builder().firstName("Ahmed").lastName("Amine").address("Oran").contactType(ContactType.FREELANCER).build();
        Contact contact2 = Contact.builder().firstName("Mohamed").lastName("Ali").address("Algiers").contactType(ContactType.FREELANCER).build();

        contact0.setEntreprises(Arrays.asList(entreprise0, entreprise1));
        contact1.setEntreprises(Arrays.asList(entreprise0));
        contactRepository.saveAll(Arrays.asList(contact0, contact1, contact2));
    }
}
