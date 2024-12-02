package com.nulstudio.dormitory.repository;

import com.nulstudio.dormitory.entity.NulInvite;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InviteRepository extends JpaRepository<NulInvite, Integer> {
    Optional<NulInvite> findByInviteCode(@NotNull String inviteCode);
}
