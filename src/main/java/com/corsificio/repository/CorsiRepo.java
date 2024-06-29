package com.corsificio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.corsificio.model.Corso;

@Repository
public interface CorsiRepo extends JpaRepository<Corso, Long> {
	Optional<Corso> findById(Long id);

}
