package com.poliscrypts.api.repository;

import com.poliscrypts.api.entity.ContactType;
import com.poliscrypts.api.enumeration.ContactTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ContactTypeRepository extends JpaRepository<ContactType, Integer> {
    ContactType findByType(String contactTypeEnum);
}
