package com.amorrecords.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.amorrecords.domain.TableBar;

import com.amorrecords.repository.TableBarRepository;
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
 * REST controller for managing TableBar.
 */

@RestController
@RequestMapping("/api")
public class TableBarResource {

    private final Logger log = LoggerFactory.getLogger(TableBarResource.class);

    private static final String ENTITY_NAME = "TableBar";

    private final TableBarRepository tableBarRepository;

    public TableBarResource(TableBarRepository tableBarRepository) {
        this.tableBarRepository = tableBarRepository;
    }

    /**
     * POST  /table-bars : Create a new TableBar.
     *
     * @param tableBar the TableBar to create
     * @return the ResponseEntity with status 201 (Created) and with body the new TableBar, or with status 400 (Bad Request) if the TableBar has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */

    @PostMapping("/table-bars")
    @Timed
    public ResponseEntity<TableBar> createTableBar(@RequestBody TableBar tableBar) throws URISyntaxException {
        log.debug("REST request to save TableBar : {}", tableBar);
        if (tableBar.getId() != null) {
            throw new BadRequestAlertException("A new TableBar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TableBar result = tableBarRepository.save(tableBar);
        return ResponseEntity.created(new URI("/api/table-bars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /table-bars : Updates an existing TableBar.
     *
     * @param tableBar the TableBar to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated TableBar,
     * or with status 400 (Bad Request) if the TableBar is not valid,
     * or with status 500 (Internal Server Error) if the TableBar couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */

    @PutMapping("/table-bars")
    @Timed
    public ResponseEntity<TableBar> updateTableBar(@RequestBody TableBar tableBar) throws URISyntaxException {
        log.debug("REST request to update TableBar : {}", tableBar);
        if (tableBar.getId() == null) {
            return createTableBar(tableBar);
        }
        TableBar result = tableBarRepository.save(tableBar);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tableBar.getId().toString()))
            .body(result);
    }

/**
     * GET  /table-bars : get all the TableBars.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of TableBars in body
     */

    @GetMapping("/table-bars")
    @Timed
    public List<TableBar> getAllTableBars() {
        log.debug("REST request to get all TableBars");
        return tableBarRepository.findAll();
        }

/**
     * GET  /table-bars/:id : get the "id" TableBar.
     *
     * @param id the id of the TableBar to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the TableBar, or with status 404 (Not Found)
     */
    @GetMapping("/table-bars/{id}")
    @Timed
    public ResponseEntity<TableBar> getTableBar(@PathVariable Long id) {
        log.debug("REST request to get TableBar : {}", id);
        TableBar TableBar = tableBarRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(TableBar));
    }


/**
     * DELETE  /table-bars/:id : delete the "id" TableBar.
     *
     * @param id the id of the TableBar to delete
     * @return the ResponseEntity with status 200 (OK)
     */

    @DeleteMapping("/table-bars/{id}")
    @Timed
    public ResponseEntity<Void> deleteTableBar(@PathVariable Long id) {
        log.debug("REST request to delete TableBar : {}", id);
        tableBarRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
