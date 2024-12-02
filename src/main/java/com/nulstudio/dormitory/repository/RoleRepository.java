package com.nulstudio.dormitory.repository;

import com.nulstudio.dormitory.entity.NulRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<NulRole, Integer> {
}
