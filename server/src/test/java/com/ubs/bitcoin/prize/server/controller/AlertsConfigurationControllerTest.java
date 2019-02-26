package com.ubs.bitcoin.prize.server.controller;

import com.ubs.bitcoin.prize.server.TestPrizeServer;
import com.ubs.bitcoin.prize.server.dto.AlertConfiguration;
import com.ubs.bitcoin.prize.server.repository.AlertsConfigurationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static java.lang.Boolean.FALSE;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith( SpringExtension.class )
@SpringBootTest( classes = TestPrizeServer.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT )
@DirtiesContext
class AlertsConfigurationControllerTest
{
    @Autowired
    private AlertsConfigurationController alertsConfigurationController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private AlertsConfigurationRepository alertsConfigurationRepository;


    @AfterEach
    void tearDown()
    {
        alertsConfigurationRepository.deleteAll();
    }


    @DisplayName( "Should be loaded into context" )
    @Test
    void context()
    {
        assertThat( alertsConfigurationController ).isNotNull();
    }


    @DisplayName( "Should return HttpStatus 200" )
    @Test
    void shouldHaveResource()
    {
        AlertConfiguration alertConfiguration = new AlertConfiguration( CURRENCY_PAIR, LIMIT );
        ResponseEntity<AlertConfiguration> response =
            testRestTemplate.postForEntity( ALERT_CONFIGURATION_URL, alertConfiguration, AlertConfiguration.class );

        assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.CREATED );
    }


    @DisplayName( "Should return new alert configuration in response if added to service" )
    @Test
    void shouldAddAlertConfiguration()
    {
        AlertConfiguration alertConfiguration = new AlertConfiguration( CURRENCY_PAIR, LIMIT );
        ResponseEntity<AlertConfiguration> response =
            testRestTemplate.postForEntity( ALERT_CONFIGURATION_URL, alertConfiguration, AlertConfiguration.class );

        assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.CREATED );
        assertThat( response.getBody() ).isEqualToComparingFieldByField( alertConfiguration );
    }


    @DisplayName( "Should add new alert configuration to service" )
    @Test
    void shouldAddAlertConfigurationToRepository()
    {
        AlertConfiguration alertConfiguration = new AlertConfiguration( CURRENCY_PAIR, LIMIT );
        ResponseEntity<AlertConfiguration> response =
            testRestTemplate.postForEntity( ALERT_CONFIGURATION_URL, alertConfiguration, AlertConfiguration.class );

        assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.CREATED );
        assertThat( response.getBody() ).isEqualToComparingFieldByField( alertConfiguration );
        assertThat( alertsConfigurationRepository.findById( alertConfiguration )
                                                 .block() ).isEqualToComparingFieldByField( alertConfiguration );
    }


    @DisplayName( "Should delete existing alert configuration from service" )
    @Test
    void shouldDeleteAlert()
    {
        AlertConfiguration alertConfiguration = new AlertConfiguration( CURRENCY_PAIR, LIMIT );
        testRestTemplate.postForEntity( ALERT_CONFIGURATION_URL, alertConfiguration, AlertConfiguration.class );
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add( "Accept", MediaType.APPLICATION_JSON_VALUE );
        HttpEntity<AlertConfiguration> requestEntity = new HttpEntity<>( alertConfiguration, httpHeaders );
        testRestTemplate.exchange( ALERT_CONFIGURATION_URL, HttpMethod.DELETE, requestEntity, AlertConfiguration.class );

        assertThat( alertsConfigurationRepository.existsById( alertConfiguration )
                                                 .block() ).isEqualTo( FALSE );
    }


    private static final String ALERT_CONFIGURATION_URL = "http://localhost:8090/api/alertConfiguration";
    private static final String CURRENCY_PAIR = "BTC-USD";
    private static final String LIMIT = "500";
}