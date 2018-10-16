package com.amorrecords.web.rest;

import com.amorrecords.AmorrecordsApp;

import com.amorrecords.domain.Table_bar;
import com.amorrecords.repository.Table_barRepository;
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
 * Test class for the Table_barResource REST controller.
 *
 * @see Table_barResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AmorrecordsApp.class)
public class Table_barResourceIntTest {

    @Autowired
    private Table_barRepository table_barRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTable_barMockMvc;

    private Table_bar table_bar;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Table_barResource table_barResource = new Table_barResource(table_barRepository);
        this.restTable_barMockMvc = MockMvcBuilders.standaloneSetup(table_barResource)
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
    public static Table_bar createEntity(EntityManager em) {
        Table_bar table_bar = new Table_bar();
        return table_bar;
    }

    @Before
    public void initTest() {
        table_bar = createEntity(em);
    }

    @Test
    @Transactional
    public void createTable_bar() throws Exception {
        int databaseSizeBeforeCreate = table_barRepository.findAll().size();

        // Create the Table_bar
        restTable_barMockMvc.perform(post("/api/table-bars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(table_bar)))
            .andExpect(status().isCreated());

        // Validate the Table_bar in the database
        List<Table_bar> table_barList = table_barRepository.findAll();
        assertThat(table_barList).hasSize(databaseSizeBeforeCreate + 1);
        Table_bar testTable_bar = table_barList.get(table_barList.size() - 1);
    }

    @Test
    @Transactional
    public void createTable_barWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = table_barRepository.findAll().size();

        // Create the Table_bar with an existing ID
        table_bar.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTable_barMockMvc.perform(post("/api/table-bars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(table_bar)))
            .andExpect(status().isBadRequest());

        // Validate the Table_bar in the database
        List<Table_bar> table_barList = table_barRepository.findAll();
        assertThat(table_barList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTable_bars() throws Exception {
        // Initialize the database
        table_barRepository.saveAndFlush(table_bar);

        // Get all the table_barList
        restTable_barMockMvc.perform(get("/api/table-bars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(table_bar.getId().intValue())));
    }

    @Test
    @Transactional
    public void getTable_bar() throws Exception {
        // Initialize the database
        table_barRepository.saveAndFlush(table_bar);

        // Get the table_bar
        restTable_barMockMvc.perform(get("/api/table-bars/{id}", table_bar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(table_bar.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTable_bar() throws Exception {
        // Get the table_bar
        restTable_barMockMvc.perform(get("/api/table-bars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTable_bar() throws Exception {
        // Initialize the database
        table_barRepository.saveAndFlush(table_bar);
        int databaseSizeBeforeUpdate = table_barRepository.findAll().size();

        // Update the table_bar
        Table_bar updatedTable_bar = table_barRepository.findOne(table_bar.getId());
        // Disconnect from session so that the updates on updatedTable_bar are not directly saved in db
        em.detach(updatedTable_bar);

        restTable_barMockMvc.perform(put("/api/table-bars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTable_bar)))
            .andExpect(status().isOk());

        // Validate the Table_bar in the database
        List<Table_bar> table_barList = table_barRepository.findAll();
        assertThat(table_barList).hasSize(databaseSizeBeforeUpdate);
        Table_bar testTable_bar = table_barList.get(table_barList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingTable_bar() throws Exception {
        int databaseSizeBeforeUpdate = table_barRepository.findAll().size();

        // Create the Table_bar

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTable_barMockMvc.perform(put("/api/table-bars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(table_bar)))
            .andExpect(status().isCreated());

        // Validate the Table_bar in the database
        List<Table_bar> table_barList = table_barRepository.findAll();
        assertThat(table_barList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTable_bar() throws Exception {
        // Initialize the database
        table_barRepository.saveAndFlush(table_bar);
        int databaseSizeBeforeDelete = table_barRepository.findAll().size();

        // Get the table_bar
        restTable_barMockMvc.perform(delete("/api/table-bars/{id}", table_bar.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Table_bar> table_barList = table_barRepository.findAll();
        assertThat(table_barList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Table_bar.class);
        Table_bar table_bar1 = new Table_bar();
        table_bar1.setId(1L);
        Table_bar table_bar2 = new Table_bar();
        table_bar2.setId(table_bar1.getId());
        assertThat(table_bar1).isEqualTo(table_bar2);
        table_bar2.setId(2L);
        assertThat(table_bar1).isNotEqualTo(table_bar2);
        table_bar1.setId(null);
        assertThat(table_bar1).isNotEqualTo(table_bar2);
    }
}
