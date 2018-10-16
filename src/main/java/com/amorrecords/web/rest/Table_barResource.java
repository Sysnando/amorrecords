package com.amorrecords.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.amorrecords.domain.Table_bar;

import com.amorrecords.repository.Table_barRepository;
import com.amorrecords.web.rest.errors.BadRequestAlertException;
import com.amorrecords.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Table_bar.
 */
@RestController
@RequestMapping("/api")
public class Table_barResource {

    private final Logger log = LoggerFactory.getLogger(Table_barResource.class);

    private static final String ENTITY_NAME = "table_bar";

    private final Table_barRepository table_barRepository;

    public Table_barResource(Table_barRepository table_barRepository) {
        this.table_barRepository = table_barRepository;
    }

    /**
     * POST  /table-bars : Create a new table_bar.
     *
     * @param table_bar the table_bar to create
     * @return the ResponseEntity with status 201 (Created) and with body the new table_bar, or with status 400 (Bad Request) if the table_bar has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/table-bars")
    @Timed
    public ResponseEntity<Table_bar> createTable_bar(@RequestBody Table_bar table_bar) throws URISyntaxException {
        log.debug("REST request to save Table_bar : {}", table_bar);
        if (table_bar.getId() != null) {
            throw new BadRequestAlertException("A new table_bar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Table_bar result = table_barRepository.save(table_bar);
        return ResponseEntity.created(new URI("/api/table-bars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /table-bars : Updates an existing table_bar.
     *
     * @param table_bar the table_bar to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated table_bar,
     * or with status 400 (Bad Request) if the table_bar is not valid,
     * or with status 500 (Internal Server Error) if the table_bar couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/table-bars")
    @Timed
    public ResponseEntity<Table_bar> updateTable_bar(@RequestBody Table_bar table_bar) throws URISyntaxException {
        log.debug("REST request to update Table_bar : {}", table_bar);
        if (table_bar.getId() == null) {
            return createTable_bar(table_bar);
        }
        Table_bar result = table_barRepository.save(table_bar);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, table_bar.getId().toString()))
            .body(result);
    }

    /**
     * GET  /table-bars : get all the table_bars.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of table_bars in body
     */
    @GetMapping("/table-bars")
    @Timed
    public List<Table_bar> getAllTable_bars() {
        log.debug("REST request to get all Table_bars");
        return table_barRepository.findAll();
        }

    /**
     * GET  /table-bars/:id : get the "id" table_bar.
     *
     * @param id the id of the table_bar to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the table_bar, or with status 404 (Not Found)
     */
    @GetMapping("/table-bars/{id}")
    @Timed
    public ResponseEntity<Table_bar> getTable_bar(@PathVariable Long id) {
        log.debug("REST request to get Table_bar : {}", id);
        Table_bar table_bar = table_barRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(table_bar));
    }

    /**
     * DELETE  /table-bars/:id : delete the "id" table_bar.
     *
     * @param id the id of the table_bar to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/table-bars/{id}")
    @Timed
    public ResponseEntity<Void> deleteTable_bar(@PathVariable Long id) {
        log.debug("REST request to delete Table_bar : {}", id);
        table_barRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
