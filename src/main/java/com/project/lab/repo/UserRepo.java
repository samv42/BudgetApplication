package com.project.lab.repo;

import com.project.lab.CustomUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<CustomUserDetails, Long> {
    CustomUserDetails findByUsername(String username);
}
