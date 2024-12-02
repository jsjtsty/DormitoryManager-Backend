package com.nulstudio.dormitory.repository;

import com.nulstudio.dormitory.entity.NulCredit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditRepository extends JpaRepository<NulCredit, Integer> {

}
