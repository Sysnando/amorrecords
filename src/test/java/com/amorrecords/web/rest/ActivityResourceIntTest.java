package com.amorrecords.web.rest;

import com.amorrecords.AmorrecordsApp;

import com.amorrecords.domain.Activity;
import com.amorrecords.repository.ActivityRepository;
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

import com.amorrecords.domain.enumeration.TypePayment;
import com.amorrecords.domain.enumeration.Operation;
/**
 * Test class for the ActivityResource REST controller.
 *
 * @see ActivityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AmorrecordsApp.class)
public class ActivityResourceIntTest {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DUE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DUE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final Double DEFAULT_AMOUNT_UNIT = 1D;
    private static final Double UPDATED_AMOUNT_UNIT = 2D;

    private static final Double DEFAULT_AMOUNT_TOTAL = 1D;
    private static final Double UPDATED_AMOUNT_TOTAL = 2D;

    private static final String DEFAULT_INVOICE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_NUMBER = "BBBBBBBBBB";

    private static final TypePayment DEFAULT_PAYMENT_TYPE = TypePayment.NU;
    private static final TypePayment UPDATED_PAYMENT_TYPE = TypePayment.CC;

    private static final Operation DEFAULT_OPERATION = Operation.IN;
    private static final Operation UPDATED_OPERATION = Operation.OUT;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restActivityMockMvc;

    private Activity activity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActivityResource activityResource = new ActivityResource(activityRepository);
        this.restActivityMockMvc = MockMvcBuilders.standaloneSetup(activityResource)
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
    public static Activity createEntity(EntityManager em) {
        Activity activity = new Activity()
            .date(DEFAULT_DATE)
            .dueDate(DEFAULT_DUE_DATE)
            .reference(DEFAULT_REFERENCE)
            .description(DEFAULT_DESCRIPTION)
            .quantity(DEFAULT_QUANTITY)
            .amountUnit(DEFAULT_AMOUNT_UNIT)
            .amountTotal(DEFAULT_AMOUNT_TOTAL)
            .invoiceNumber(DEFAULT_INVOICE_NUMBER)
            .paymentType(DEFAULT_PAYMENT_TYPE)
            .operation(DEFAULT_OPERATION);
        return activity;
    }

    @Before
    public void initTest() {
        activity = createEntity(em);
    }

    @Test
    @Transactional
    public void createActivity() throws Exception {
        int databaseSizeBeforeCreate = activityRepository.findAll().size();

        // Create the Activity
        restActivityMockMvc.perform(post("/api/activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activity)))
            .andExpect(status().isCreated());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeCreate + 1);
        Activity testActivity = activityList.get(activityList.size() - 1);
        assertThat(testActivity.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testActivity.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testActivity.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testActivity.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testActivity.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testActivity.getAmountUnit()).isEqualTo(DEFAULT_AMOUNT_UNIT);
        assertThat(testActivity.getAmountTotal()).isEqualTo(DEFAULT_AMOUNT_TOTAL);
        assertThat(testActivity.getInvoiceNumber()).isEqualTo(DEFAULT_INVOICE_NUMBER);
        assertThat(testActivity.getPaymentType()).isEqualTo(DEFAULT_PAYMENT_TYPE);
        assertThat(testActivity.getOperation()).isEqualTo(DEFAULT_OPERATION);
    }

    @Test
    @Transactional
    public void createActivityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = activityRepository.findAll().size();

        // Create the Activity with an existing ID
        activity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActivityMockMvc.perform(post("/api/activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activity)))
            .andExpect(status().isBadRequest());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllActivities() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList
        restActivityMockMvc.perform(get("/api/activities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activity.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].amountUnit").value(hasItem(DEFAULT_AMOUNT_UNIT.doubleValue())))
            .andExpect(jsonPath("$.[*].amountTotal").value(hasItem(DEFAULT_AMOUNT_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].invoiceNumber").value(hasItem(DEFAULT_INVOICE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].paymentType").value(hasItem(DEFAULT_PAYMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].operation").value(hasItem(DEFAULT_OPERATION.toString())));
    }

    @Test
    @Transactional
    public void getActivity() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get the activity
        restActivityMockMvc.perform(get("/api/activities/{id}", activity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(activity.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.amountUnit").value(DEFAULT_AMOUNT_UNIT.doubleValue()))
            .andExpect(jsonPath("$.amountTotal").value(DEFAULT_AMOUNT_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.invoiceNumber").value(DEFAULT_INVOICE_NUMBER.toString()))
            .andExpect(jsonPath("$.paymentType").value(DEFAULT_PAYMENT_TYPE.toString()))
            .andExpect(jsonPath("$.operation").value(DEFAULT_OPERATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingActivity() throws Exception {
        // Get the activity
        restActivityMockMvc.perform(get("/api/activities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActivity() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);
        int databaseSizeBeforeUpdate = activityRepository.findAll().size();

        // Update the activity
        Activity updatedActivity = activityRepository.findOne(activity.getId());
        // Disconnect from session so that the updates on updatedActivity are not directly saved in db
        em.detach(updatedActivity);
        updatedActivity
            .date(UPDATED_DATE)
            .dueDate(UPDATED_DUE_DATE)
            .reference(UPDATED_REFERENCE)
            .description(UPDATED_DESCRIPTION)
            .quantity(UPDATED_QUANTITY)
            .amountUnit(UPDATED_AMOUNT_UNIT)
            .amountTotal(UPDATED_AMOUNT_TOTAL)
            .invoiceNumber(UPDATED_INVOICE_NUMBER)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .operation(UPDATED_OPERATION);

        restActivityMockMvc.perform(put("/api/activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedActivity)))
            .andExpect(status().isOk());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
        Activity testActivity = activityList.get(activityList.size() - 1);
        assertThat(testActivity.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testActivity.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testActivity.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testActivity.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testActivity.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testActivity.getAmountUnit()).isEqualTo(UPDATED_AMOUNT_UNIT);
        assertThat(testActivity.getAmountTotal()).isEqualTo(UPDATED_AMOUNT_TOTAL);
        assertThat(testActivity.getInvoiceNumber()).isEqualTo(UPDATED_INVOICE_NUMBER);
        assertThat(testActivity.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testActivity.getOperation()).isEqualTo(UPDATED_OPERATION);
    }

    @Test
    @Transactional
    public void updateNonExistingActivity() throws Exception {
        int databaseSizeBeforeUpdate = activityRepository.findAll().size();

        // Create the Activity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restActivityMockMvc.perform(put("/api/activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activity)))
            .andExpect(status().isCreated());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteActivity() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);
        int databaseSizeBeforeDelete = activityRepository.findAll().size();

        // Get the activity
        restActivityMockMvc.perform(delete("/api/activities/{id}", activity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Activity.class);
        Activity activity1 = new Activity();
        activity1.setId(1L);
        Activity activity2 = new Activity();
        activity2.setId(activity1.getId());
        assertThat(activity1).isEqualTo(activity2);
        activity2.setId(2L);
        assertThat(activity1).isNotEqualTo(activity2);
        activity1.setId(null);
        assertThat(activity1).isNotEqualTo(activity2);
    }
}
