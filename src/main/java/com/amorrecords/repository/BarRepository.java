package com.amorrecords.repository;

import com.amorrecords.domain.Bar;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Bar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BarRepository extends JpaRepository<Bar, Long> {

}
