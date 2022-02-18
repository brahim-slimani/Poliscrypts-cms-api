package com.poliscrypts.api.repository;

import com.poliscrypts.api.entity.Role;
import com.poliscrypts.api.enumeration.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(RoleEnum roleEnum);
}
