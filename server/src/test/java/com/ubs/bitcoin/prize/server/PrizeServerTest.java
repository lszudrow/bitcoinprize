package com.ubs.bitcoin.prize.server;

import com.ubs.bitcoin.prize.server.dto.Alert;
import com.ubs.bitcoin.prize.server.service.AlertsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;


@ExtendWith( SpringExtension.class )
@SpringBootTest( classes = TestPrizeServer.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT )
@DirtiesContext
class PrizeServerTest
{
    @Autowired
    AlertsService alertsService;


    @DisplayName( "Should provide stream of two alerts added previously to service" )
    @Test
    void shouldProvideStreamOfAlerts()
    {
        WebTestClient client = WebTestClient.bindToServer()
                                            .baseUrl( "http://localhost:8090/api" )
                                            .defaultHeader( "Accept", MediaType.APPLICATION_STREAM_JSON_VALUE )
                                            .build();

        Alert firstAlert = new Alert( "0.02", "BTC-USD", Long.toString( System.nanoTime() ) );
        Alert secondAlert = new Alert( "0.01", "BTC-EUR", Long.toString( System.nanoTime() ) );
        alertsService.add( firstAlert );
        alertsService.add( secondAlert );
        FluxExchangeResult<Alert> result = client.get()
                                                 .uri( "/alerts" )
                                                 .accept( TEXT_EVENT_STREAM )
                                                 .exchange()
                                                 .expectStatus()
                                                 .isOk()
                                                 .returnResult( Alert.class );

        Flux<Alert> alertFlux = result.getResponseBody();
        assertThat( alertFlux.toStream() ).contains( firstAlert, secondAlert );
    }
}