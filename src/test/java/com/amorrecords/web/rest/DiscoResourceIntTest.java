package com.amorrecords.web.rest;

import com.amorrecords.AmorrecordsApp;

import com.amorrecords.domain.Disco;
import com.amorrecords.repository.DiscoRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.amorrecords.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.amorrecords.domain.enumeration.Condition;
import com.amorrecords.domain.enumeration.DiscoType;
/**
 * Test class for the DiscoResource REST controller.
 *
 * @see DiscoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AmorrecordsApp.class)
public class DiscoResourceIntTest {

    private static final String DEFAULT_QUERY = "AAAAAAAAAA";
    private static final String UPDATED_QUERY = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_RELEASE_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_RELEASE_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CREDIT = "AAAAAAAAAA";
    private static final String UPDATED_CREDIT = "BBBBBBBBBB";

    private static final String DEFAULT_ARTIST = "AAAAAAAAAA";
    private static final String UPDATED_ARTIST = "BBBBBBBBBB";

    private static final String DEFAULT_ANV = "AAAAAAAAAA";
    private static final String UPDATED_ANV = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_GENRE = "AAAAAAAAAA";
    private static final String UPDATED_GENRE = "BBBBBBBBBB";

    private static final String DEFAULT_STYLE = "AAAAAAAAAA";
    private static final String UPDATED_STYLE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_YEAR = "BBBBBBBBBB";

    private static final String DEFAULT_FORMAT = "AAAAAAAAAA";
    private static final String UPDATED_FORMAT = "BBBBBBBBBB";

    private static final String DEFAULT_CATNO = "AAAAAAAAAA";
    private static final String UPDATED_CATNO = "BBBBBBBBBB";

    private static final String DEFAULT_BAR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BAR_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TRACK = "AAAAAAAAAA";
    private static final String UPDATED_TRACK = "BBBBBBBBBB";

    private static final String DEFAULT_SUBMITTER = "AAAAAAAAAA";
    private static final String UPDATED_SUBMITTER = "BBBBBBBBBB";

    private static final String DEFAULT_CONTRIBUTOR = "AAAAAAAAAA";
    private static final String UPDATED_CONTRIBUTOR = "BBBBBBBBBB";

    private static final Condition DEFAULT_CONDITION = Condition.MINT;
    private static final Condition UPDATED_CONDITION = Condition.NEAR_MINT;

    private static final DiscoType DEFAULT_DISCO_TYPE = DiscoType.NEW;
    private static final DiscoType UPDATED_DISCO_TYPE = DiscoType.USED;

    private static final Instant DEFAULT_DATE_PURCHASE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_PURCHASE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_SALE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_SALE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_COVER = 1D;
    private static final Double UPDATED_COVER = 2D;

    private static final Double DEFAULT_DETAIL = 1D;
    private static final Double UPDATED_DETAIL = 2D;

    private static final Double DEFAULT_PRICE_PURCHASE = 1D;
    private static final Double UPDATED_PRICE_PURCHASE = 2D;

    private static final Double DEFAULT_PRICE_SALE = 1D;
    private static final Double UPDATED_PRICE_SALE = 2D;

    private static final Double DEFAULT_PRICE_SALE_STORE = 1D;
    private static final Double UPDATED_PRICE_SALE_STORE = 2D;

    private static final Double DEFAULT_PRICE_SALE_THIRD_PARTY = 1D;
    private static final Double UPDATED_PRICE_SALE_THIRD_PARTY = 2D;

    private static final Double DEFAULT_PRICE_SALE_IN_REAL = 1D;
    private static final Double UPDATED_PRICE_SALE_IN_REAL = 2D;

    @Autowired
    private DiscoRepository discoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDiscoMockMvc;

    private Disco disco;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DiscoResource discoResource = new DiscoResource(discoRepository);
        this.restDiscoMockMvc = MockMvcBuilders.standaloneSetup(discoResource)
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
    public static Disco createEntity(EntityManager em) {
        Disco disco = new Disco()
            .query(DEFAULT_QUERY)
            .type(DEFAULT_TYPE)
            .title(DEFAULT_TITLE)
            .releaseTitle(DEFAULT_RELEASE_TITLE)
            .credit(DEFAULT_CREDIT)
            .artist(DEFAULT_ARTIST)
            .anv(DEFAULT_ANV)
            .label(DEFAULT_LABEL)
            .genre(DEFAULT_GENRE)
            .style(DEFAULT_STYLE)
            .country(DEFAULT_COUNTRY)
            .year(DEFAULT_YEAR)
            .format(DEFAULT_FORMAT)
            .catno(DEFAULT_CATNO)
            .barCode(DEFAULT_BAR_CODE)
            .track(DEFAULT_TRACK)
            .submitter(DEFAULT_SUBMITTER)
            .contributor(DEFAULT_CONTRIBUTOR)
            .condition(DEFAULT_CONDITION)
            .discoType(DEFAULT_DISCO_TYPE)
            .datePurchase(DEFAULT_DATE_PURCHASE)
            .dateSale(DEFAULT_DATE_SALE)
            .cover(DEFAULT_COVER)
            .detail(DEFAULT_DETAIL)
            .pricePurchase(DEFAULT_PRICE_PURCHASE)
            .priceSale(DEFAULT_PRICE_SALE)
            .priceSaleStore(DEFAULT_PRICE_SALE_STORE)
            .priceSaleThirdParty(DEFAULT_PRICE_SALE_THIRD_PARTY)
            .priceSaleInReal(DEFAULT_PRICE_SALE_IN_REAL);
        return disco;
    }

    @Before
    public void initTest() {
        disco = createEntity(em);
    }

    @Test
    @Transactional
    public void createDisco() throws Exception {
        int databaseSizeBeforeCreate = discoRepository.findAll().size();

        // Create the Disco
        restDiscoMockMvc.perform(post("/api/discos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(disco)))
            .andExpect(status().isCreated());

        // Validate the Disco in the database
        List<Disco> discoList = discoRepository.findAll();
        assertThat(discoList).hasSize(databaseSizeBeforeCreate + 1);
        Disco testDisco = discoList.get(discoList.size() - 1);
        assertThat(testDisco.getQuery()).isEqualTo(DEFAULT_QUERY);
        assertThat(testDisco.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDisco.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testDisco.getReleaseTitle()).isEqualTo(DEFAULT_RELEASE_TITLE);
        assertThat(testDisco.getCredit()).isEqualTo(DEFAULT_CREDIT);
        assertThat(testDisco.getArtist()).isEqualTo(DEFAULT_ARTIST);
        assertThat(testDisco.getAnv()).isEqualTo(DEFAULT_ANV);
        assertThat(testDisco.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testDisco.getGenre()).isEqualTo(DEFAULT_GENRE);
        assertThat(testDisco.getStyle()).isEqualTo(DEFAULT_STYLE);
        assertThat(testDisco.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testDisco.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testDisco.getFormat()).isEqualTo(DEFAULT_FORMAT);
        assertThat(testDisco.getCatno()).isEqualTo(DEFAULT_CATNO);
        assertThat(testDisco.getBarCode()).isEqualTo(DEFAULT_BAR_CODE);
        assertThat(testDisco.getTrack()).isEqualTo(DEFAULT_TRACK);
        assertThat(testDisco.getSubmitter()).isEqualTo(DEFAULT_SUBMITTER);
        assertThat(testDisco.getContributor()).isEqualTo(DEFAULT_CONTRIBUTOR);
        assertThat(testDisco.getCondition()).isEqualTo(DEFAULT_CONDITION);
        assertThat(testDisco.getDiscoType()).isEqualTo(DEFAULT_DISCO_TYPE);
        assertThat(testDisco.getDatePurchase()).isEqualTo(DEFAULT_DATE_PURCHASE);
        assertThat(testDisco.getDateSale()).isEqualTo(DEFAULT_DATE_SALE);
        assertThat(testDisco.getCover()).isEqualTo(DEFAULT_COVER);
        assertThat(testDisco.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testDisco.getPricePurchase()).isEqualTo(DEFAULT_PRICE_PURCHASE);
        assertThat(testDisco.getPriceSale()).isEqualTo(DEFAULT_PRICE_SALE);
        assertThat(testDisco.getPriceSaleStore()).isEqualTo(DEFAULT_PRICE_SALE_STORE);
        assertThat(testDisco.getPriceSaleThirdParty()).isEqualTo(DEFAULT_PRICE_SALE_THIRD_PARTY);
        assertThat(testDisco.getPriceSaleInReal()).isEqualTo(DEFAULT_PRICE_SALE_IN_REAL);
    }

    @Test
    @Transactional
    public void createDiscoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = discoRepository.findAll().size();

        // Create the Disco with an existing ID
        disco.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiscoMockMvc.perform(post("/api/discos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(disco)))
            .andExpect(status().isBadRequest());

        // Validate the Disco in the database
        List<Disco> discoList = discoRepository.findAll();
        assertThat(discoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDiscos() throws Exception {
        // Initialize the database
        discoRepository.saveAndFlush(disco);

        // Get all the discoList
        restDiscoMockMvc.perform(get("/api/discos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(disco.getId().intValue())))
            .andExpect(jsonPath("$.[*].query").value(hasItem(DEFAULT_QUERY.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].releaseTitle").value(hasItem(DEFAULT_RELEASE_TITLE.toString())))
            .andExpect(jsonPath("$.[*].credit").value(hasItem(DEFAULT_CREDIT.toString())))
            .andExpect(jsonPath("$.[*].artist").value(hasItem(DEFAULT_ARTIST.toString())))
            .andExpect(jsonPath("$.[*].anv").value(hasItem(DEFAULT_ANV.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].genre").value(hasItem(DEFAULT_GENRE.toString())))
            .andExpect(jsonPath("$.[*].style").value(hasItem(DEFAULT_STYLE.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR.toString())))
            .andExpect(jsonPath("$.[*].format").value(hasItem(DEFAULT_FORMAT.toString())))
            .andExpect(jsonPath("$.[*].catno").value(hasItem(DEFAULT_CATNO.toString())))
            .andExpect(jsonPath("$.[*].barCode").value(hasItem(DEFAULT_BAR_CODE.toString())))
            .andExpect(jsonPath("$.[*].track").value(hasItem(DEFAULT_TRACK.toString())))
            .andExpect(jsonPath("$.[*].submitter").value(hasItem(DEFAULT_SUBMITTER.toString())))
            .andExpect(jsonPath("$.[*].contributor").value(hasItem(DEFAULT_CONTRIBUTOR.toString())))
            .andExpect(jsonPath("$.[*].condition").value(hasItem(DEFAULT_CONDITION.toString())))
            .andExpect(jsonPath("$.[*].discoType").value(hasItem(DEFAULT_DISCO_TYPE.toString())))
            .andExpect(jsonPath("$.[*].datePurchase").value(hasItem(DEFAULT_DATE_PURCHASE.toString())))
            .andExpect(jsonPath("$.[*].dateSale").value(hasItem(DEFAULT_DATE_SALE.toString())))
            .andExpect(jsonPath("$.[*].cover").value(hasItem(DEFAULT_COVER.doubleValue())))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.doubleValue())))
            .andExpect(jsonPath("$.[*].pricePurchase").value(hasItem(DEFAULT_PRICE_PURCHASE.doubleValue())))
            .andExpect(jsonPath("$.[*].priceSale").value(hasItem(DEFAULT_PRICE_SALE.doubleValue())))
            .andExpect(jsonPath("$.[*].priceSaleStore").value(hasItem(DEFAULT_PRICE_SALE_STORE.doubleValue())))
            .andExpect(jsonPath("$.[*].priceSaleThirdParty").value(hasItem(DEFAULT_PRICE_SALE_THIRD_PARTY.doubleValue())))
            .andExpect(jsonPath("$.[*].priceSaleInReal").value(hasItem(DEFAULT_PRICE_SALE_IN_REAL.doubleValue())));
    }

    @Test
    @Transactional
    public void getDisco() throws Exception {
        // Initialize the database
        discoRepository.saveAndFlush(disco);

        // Get the disco
        restDiscoMockMvc.perform(get("/api/discos/{id}", disco.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(disco.getId().intValue()))
            .andExpect(jsonPath("$.query").value(DEFAULT_QUERY.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.releaseTitle").value(DEFAULT_RELEASE_TITLE.toString()))
            .andExpect(jsonPath("$.credit").value(DEFAULT_CREDIT.toString()))
            .andExpect(jsonPath("$.artist").value(DEFAULT_ARTIST.toString()))
            .andExpect(jsonPath("$.anv").value(DEFAULT_ANV.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.genre").value(DEFAULT_GENRE.toString()))
            .andExpect(jsonPath("$.style").value(DEFAULT_STYLE.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR.toString()))
            .andExpect(jsonPath("$.format").value(DEFAULT_FORMAT.toString()))
            .andExpect(jsonPath("$.catno").value(DEFAULT_CATNO.toString()))
            .andExpect(jsonPath("$.barCode").value(DEFAULT_BAR_CODE.toString()))
            .andExpect(jsonPath("$.track").value(DEFAULT_TRACK.toString()))
            .andExpect(jsonPath("$.submitter").value(DEFAULT_SUBMITTER.toString()))
            .andExpect(jsonPath("$.contributor").value(DEFAULT_CONTRIBUTOR.toString()))
            .andExpect(jsonPath("$.condition").value(DEFAULT_CONDITION.toString()))
            .andExpect(jsonPath("$.discoType").value(DEFAULT_DISCO_TYPE.toString()))
            .andExpect(jsonPath("$.datePurchase").value(DEFAULT_DATE_PURCHASE.toString()))
            .andExpect(jsonPath("$.dateSale").value(DEFAULT_DATE_SALE.toString()))
            .andExpect(jsonPath("$.cover").value(DEFAULT_COVER.doubleValue()))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL.doubleValue()))
            .andExpect(jsonPath("$.pricePurchase").value(DEFAULT_PRICE_PURCHASE.doubleValue()))
            .andExpect(jsonPath("$.priceSale").value(DEFAULT_PRICE_SALE.doubleValue()))
            .andExpect(jsonPath("$.priceSaleStore").value(DEFAULT_PRICE_SALE_STORE.doubleValue()))
            .andExpect(jsonPath("$.priceSaleThirdParty").value(DEFAULT_PRICE_SALE_THIRD_PARTY.doubleValue()))
            .andExpect(jsonPath("$.priceSaleInReal").value(DEFAULT_PRICE_SALE_IN_REAL.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDisco() throws Exception {
        // Get the disco
        restDiscoMockMvc.perform(get("/api/discos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDisco() throws Exception {
        // Initialize the database
        discoRepository.saveAndFlush(disco);
        int databaseSizeBeforeUpdate = discoRepository.findAll().size();

        // Update the disco
        Disco updatedDisco = discoRepository.findOne(disco.getId());
        // Disconnect from session so that the updates on updatedDisco are not directly saved in db
        em.detach(updatedDisco);
        updatedDisco
            .query(UPDATED_QUERY)
            .type(UPDATED_TYPE)
            .title(UPDATED_TITLE)
            .releaseTitle(UPDATED_RELEASE_TITLE)
            .credit(UPDATED_CREDIT)
            .artist(UPDATED_ARTIST)
            .anv(UPDATED_ANV)
            .label(UPDATED_LABEL)
            .genre(UPDATED_GENRE)
            .style(UPDATED_STYLE)
            .country(UPDATED_COUNTRY)
            .year(UPDATED_YEAR)
            .format(UPDATED_FORMAT)
            .catno(UPDATED_CATNO)
            .barCode(UPDATED_BAR_CODE)
            .track(UPDATED_TRACK)
            .submitter(UPDATED_SUBMITTER)
            .contributor(UPDATED_CONTRIBUTOR)
            .condition(UPDATED_CONDITION)
            .discoType(UPDATED_DISCO_TYPE)
            .datePurchase(UPDATED_DATE_PURCHASE)
            .dateSale(UPDATED_DATE_SALE)
            .cover(UPDATED_COVER)
            .detail(UPDATED_DETAIL)
            .pricePurchase(UPDATED_PRICE_PURCHASE)
            .priceSale(UPDATED_PRICE_SALE)
            .priceSaleStore(UPDATED_PRICE_SALE_STORE)
            .priceSaleThirdParty(UPDATED_PRICE_SALE_THIRD_PARTY)
            .priceSaleInReal(UPDATED_PRICE_SALE_IN_REAL);

        restDiscoMockMvc.perform(put("/api/discos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDisco)))
            .andExpect(status().isOk());

        // Validate the Disco in the database
        List<Disco> discoList = discoRepository.findAll();
        assertThat(discoList).hasSize(databaseSizeBeforeUpdate);
        Disco testDisco = discoList.get(discoList.size() - 1);
        assertThat(testDisco.getQuery()).isEqualTo(UPDATED_QUERY);
        assertThat(testDisco.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDisco.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testDisco.getReleaseTitle()).isEqualTo(UPDATED_RELEASE_TITLE);
        assertThat(testDisco.getCredit()).isEqualTo(UPDATED_CREDIT);
        assertThat(testDisco.getArtist()).isEqualTo(UPDATED_ARTIST);
        assertThat(testDisco.getAnv()).isEqualTo(UPDATED_ANV);
        assertThat(testDisco.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testDisco.getGenre()).isEqualTo(UPDATED_GENRE);
        assertThat(testDisco.getStyle()).isEqualTo(UPDATED_STYLE);
        assertThat(testDisco.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testDisco.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testDisco.getFormat()).isEqualTo(UPDATED_FORMAT);
        assertThat(testDisco.getCatno()).isEqualTo(UPDATED_CATNO);
        assertThat(testDisco.getBarCode()).isEqualTo(UPDATED_BAR_CODE);
        assertThat(testDisco.getTrack()).isEqualTo(UPDATED_TRACK);
        assertThat(testDisco.getSubmitter()).isEqualTo(UPDATED_SUBMITTER);
        assertThat(testDisco.getContributor()).isEqualTo(UPDATED_CONTRIBUTOR);
        assertThat(testDisco.getCondition()).isEqualTo(UPDATED_CONDITION);
        assertThat(testDisco.getDiscoType()).isEqualTo(UPDATED_DISCO_TYPE);
        assertThat(testDisco.getDatePurchase()).isEqualTo(UPDATED_DATE_PURCHASE);
        assertThat(testDisco.getDateSale()).isEqualTo(UPDATED_DATE_SALE);
        assertThat(testDisco.getCover()).isEqualTo(UPDATED_COVER);
        assertThat(testDisco.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testDisco.getPricePurchase()).isEqualTo(UPDATED_PRICE_PURCHASE);
        assertThat(testDisco.getPriceSale()).isEqualTo(UPDATED_PRICE_SALE);
        assertThat(testDisco.getPriceSaleStore()).isEqualTo(UPDATED_PRICE_SALE_STORE);
        assertThat(testDisco.getPriceSaleThirdParty()).isEqualTo(UPDATED_PRICE_SALE_THIRD_PARTY);
        assertThat(testDisco.getPriceSaleInReal()).isEqualTo(UPDATED_PRICE_SALE_IN_REAL);
    }

    @Test
    @Transactional
    public void updateNonExistingDisco() throws Exception {
        int databaseSizeBeforeUpdate = discoRepository.findAll().size();

        // Create the Disco

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDiscoMockMvc.perform(put("/api/discos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(disco)))
            .andExpect(status().isCreated());

        // Validate the Disco in the database
        List<Disco> discoList = discoRepository.findAll();
        assertThat(discoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDisco() throws Exception {
        // Initialize the database
        discoRepository.saveAndFlush(disco);
        int databaseSizeBeforeDelete = discoRepository.findAll().size();

        // Get the disco
        restDiscoMockMvc.perform(delete("/api/discos/{id}", disco.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Disco> discoList = discoRepository.findAll();
        assertThat(discoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Disco.class);
        Disco disco1 = new Disco();
        disco1.setId(1L);
        Disco disco2 = new Disco();
        disco2.setId(disco1.getId());
        assertThat(disco1).isEqualTo(disco2);
        disco2.setId(2L);
        assertThat(disco1).isNotEqualTo(disco2);
        disco1.setId(null);
        assertThat(disco1).isNotEqualTo(disco2);
    }
}
