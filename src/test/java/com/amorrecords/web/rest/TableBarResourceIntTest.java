package com.amorrecords.web.rest;

import com.amorrecords.AmorrecordsApp;

import com.amorrecords.domain.TableBar;
import com.amorrecords.repository.TableBarRepository;
import com.amorrecords.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.amorrecords.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TableBarResource REST controller.
 *
 * @see TableBarResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AmorrecordsApp.class)
public class TableBarResourceIntTest {

    @Autowired
    private TableBarRepository tableBarRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTableBarMockMvc;

    private TableBar TableBar;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TableBarResource TableBarResource = new TableBarResource(tableBarRepository);
        this.restTableBarMockMvc = MockMvcBuilders.standaloneSetup(TableBarResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TableBar createEntity(EntityManager em) {
        TableBar TableBar = new TableBar();
        return TableBar;
    }

    @Before
    public void initTest() {
        TableBar = createEntity(em);
    }

    @Test
    @Transactional
    public void createTableBar() throws Exception {
        int databaseSizeBeforeCreate = tableBarRepository.findAll().size();

        // Create the TableBar
        restTableBarMockMvc.perform(post("/api/table-bars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(TableBar)))
            .andExpect(status().isCreated());

        // Validate the TableBar in the database
        List<TableBar> TableBarList = tableBarRepository.findAll();
        assertThat(TableBarList).hasSize(databaseSizeBeforeCreate + 1);
        TableBar testTableBar = TableBarList.get(TableBarList.size() - 1);
    }

    @Test
    @Transactional
    public void createTableBarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tableBarRepository.findAll().size();

        // Create the TableBar with an existing ID
        TableBar.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTableBarMockMvc.perform(post("/api/table-bars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(TableBar)))
            .andExpect(status().isBadRequest());

        // Validate the TableBar in the database
        List<TableBar> TableBarList = tableBarRepository.findAll();
        assertThat(TableBarList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTableBars() throws Exception {
        // Initialize the database
        tableBarRepository.saveAndFlush(TableBar);

        // Get all the TableBarList
        restTableBarMockMvc.perform(get("/api/table-bars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(TableBar.getId().intValue())));
    }

    @Test
    @Transactional
    public void getTableBar() throws Exception {
        // Initialize the database
        tableBarRepository.saveAndFlush(TableBar);

        // Get the TableBar
        restTableBarMockMvc.perform(get("/api/table-bars/{id}", TableBar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(TableBar.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTableBar() throws Exception {
        // Get the TableBar
        restTableBarMockMvc.perform(get("/api/table-bars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTableBar() throws Exception {
        // Initialize the database
        tableBarRepository.saveAndFlush(TableBar);
        int databaseSizeBeforeUpdate = tableBarRepository.findAll().size();

        // Update the TableBar
        TableBar updatedTableBar = tableBarRepository.findOne(TableBar.getId());
        // Disconnect from session so that the updates on updatedTableBar are not directly saved in db
        em.detach(updatedTableBar);

        restTableBarMockMvc.perform(put("/api/table-bars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTableBar)))
            .andExpect(status().isOk());

        // Validate the TableBar in the database
        List<TableBar> TableBarList = tableBarRepository.findAll();
        assertThat(TableBarList).hasSize(databaseSizeBeforeUpdate);
        TableBar testTableBar = TableBarList.get(TableBarList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingTableBar() throws Exception {
        int databaseSizeBeforeUpdate = tableBarRepository.findAll().size();

        // Create the TableBar

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTableBarMockMvc.perform(put("/api/table-bars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(TableBar)))
            .andExpect(status().isCreated());

        // Validate the TableBar in the database
        List<TableBar> TableBarList = tableBarRepository.findAll();
        assertThat(TableBarList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTableBar() throws Exception {
        // Initialize the database
        tableBarRepository.saveAndFlush(TableBar);
        int databaseSizeBeforeDelete = tableBarRepository.findAll().size();

        // Get the TableBar
        restTableBarMockMvc.perform(delete("/api/table-bars/{id}", TableBar.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TableBar> TableBarList = tableBarRepository.findAll();
        assertThat(TableBarList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TableBar.class);
        TableBar TableBar1 = new TableBar();
        TableBar1.setId(1L);
        TableBar TableBar2 = new TableBar();
        TableBar2.setId(TableBar1.getId());
        assertThat(TableBar1).isEqualTo(TableBar2);
        TableBar2.setId(2L);
        assertThat(TableBar1).isNotEqualTo(TableBar2);
        TableBar1.setId(null);
        assertThat(TableBar1).isNotEqualTo(TableBar2);
    }
}
