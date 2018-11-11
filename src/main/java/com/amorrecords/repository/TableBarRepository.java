package com.amorrecords.repository;

import com.amorrecords.domain.TableBar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the Table_bar entity.
 */

@SuppressWarnings("unused")
@Repository
public interface TableBarRepository extends JpaRepository<TableBar, Long> {

}
