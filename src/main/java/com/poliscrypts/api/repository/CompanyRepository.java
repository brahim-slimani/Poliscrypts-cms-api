package com.poliscrypts.api.repository;

import com.poliscrypts.api.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Optional<Company> findByTvaNumber(Long tvaNumber);
    Optional<Company> findByUuid(UUID uuid);
}
