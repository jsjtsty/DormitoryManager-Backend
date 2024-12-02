package com.nulstudio.dormitory.repository;

import com.nulstudio.dormitory.entity.NulCreditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditLogRepository extends JpaRepository<NulCreditLog, Integer> {

}
