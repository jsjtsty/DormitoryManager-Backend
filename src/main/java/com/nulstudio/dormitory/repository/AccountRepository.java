package com.nulstudio.dormitory.repository;

import com.nulstudio.dormitory.entity.NulAccount;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<NulAccount, Integer> {
    Optional<NulAccount> findByUserName(@Size(max = 256) @NotNull String userName);
}
