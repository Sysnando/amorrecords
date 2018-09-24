package com.amorrecords.repository;

import com.amorrecords.domain.Disco;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Disco entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiscoRepository extends JpaRepository<Disco, Long> {

}
