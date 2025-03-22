package com.example.relational_test.repository;

import com.example.relational_test.domain.entity.Tellphone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TellphoneRepository extends JpaRepository<Tellphone, Long> {
}
