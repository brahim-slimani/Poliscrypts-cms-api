package com.poliscrypts.api.repository;

import com.poliscrypts.api.entity.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface EntrepriseRepository extends JpaRepository<Entreprise, Integer> {
}
