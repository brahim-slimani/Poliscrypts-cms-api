package com.poliscrypts.api.repository;

import com.poliscrypts.api.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Optional<Company> findByTvaNumber(Long tvaNumber);
}
