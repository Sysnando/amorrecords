package com.amorrecords.web.rest;

import com.amorrecords.integration.discogs.client.DiscogsClient;
import com.codahale.metrics.annotation.Timed;
import com.amorrecords.domain.Disco;

import com.amorrecords.repository.DiscoRepository;
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
 * REST controller for managing Disco.
 */
@RestController
@RequestMapping("/api")
public class DiscoResource {

    private final Logger log = LoggerFactory.getLogger(DiscoResource.class);

    private static final String ENTITY_NAME = "disco";

    private final DiscoRepository discoRepository;

    public DiscoResource(DiscoRepository discoRepository) {
        this.discoRepository = discoRepository;
    }

    /**
     * POST  /discos : Create a new disco.
     *
     * @param disco the disco to create
     * @return the ResponseEntity with status 201 (Created) and with body the new disco, or with status 400 (Bad Request) if the disco has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/discos")
    @Timed
    public ResponseEntity<Disco> createDisco(@RequestBody Disco disco) throws URISyntaxException {
        log.debug("REST request to save Disco : {}", disco);
        if (disco.getId() != null) {
            throw new BadRequestAlertException("A new disco cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Disco result = discoRepository.save(disco);
        return ResponseEntity.created(new URI("/api/discos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /discos : Updates an existing disco.
     *
     * @param disco the disco to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated disco,
     * or with status 400 (Bad Request) if the disco is not valid,
     * or with status 500 (Internal Server Error) if the disco couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/discos")
    @Timed
    public ResponseEntity<Disco> updateDisco(@RequestBody Disco disco) throws URISyntaxException {
        log.debug("REST request to update Disco : {}", disco);
        if (disco.getId() == null) {
            return createDisco(disco);
        }
        Disco result = discoRepository.save(disco);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, disco.getId().toString()))
            .body(result);
    }

    /**
     * GET  /discos : get all the discos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of discos in body
     */
    @GetMapping("/discos")
    @Timed
    public List<Disco> getAllDiscos() {
        log.debug("REST request to get all Discos");
        return discoRepository.findAll();
        }

    /**
     * GET  /discos/:id : get the "id" disco.
     *
     * @param id the id of the disco to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the disco, or with status 404 (Not Found)
     */
    @GetMapping("/discos/{id}")
    @Timed
    public ResponseEntity<Disco> getDisco(@PathVariable Long id) {
        log.debug("REST request to get Disco : {}", id);
        Disco disco = discoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(disco));
    }

    /**
     * DELETE  /discos/:id : delete the "id" disco.
     *
     * @param id the id of the disco to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/discos/{id}")
    @Timed
    public ResponseEntity<Void> deleteDisco(@PathVariable Long id) {
        DiscogsClient discogsClient = new DiscogsClient();
        System.out.println(discogsClient.identity());
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/discos/teste")
    public ResponseEntity<Void> testeDiscogsApi(){
        DiscogsClient discogsClient = new DiscogsClient();
        System.out.println(discogsClient.identity());
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, "adsf")).build();
    }
}
