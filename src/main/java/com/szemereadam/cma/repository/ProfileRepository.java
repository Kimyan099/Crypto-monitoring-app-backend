package com.szemereadam.cma.repository;

import com.szemereadam.cma.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
