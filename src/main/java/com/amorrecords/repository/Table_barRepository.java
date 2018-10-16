package com.amorrecords.repository;

import com.amorrecords.domain.Table_bar;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Table_bar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Table_barRepository extends JpaRepository<Table_bar, Long> {

}
