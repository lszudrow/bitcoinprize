package com.ubs.bitcoin.prize.server.controller;

import com.ubs.bitcoin.prize.server.dto.Alert;
import com.ubs.bitcoin.prize.server.service.AlertsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;


@RestController
@RequestMapping( "/api" )
public class AlertsPublisherController
{
    private AlertsService service;


    public AlertsPublisherController( @Autowired AlertsService service )
    {
        this.service = service;
    }


    @GetMapping( produces = MediaType.TEXT_EVENT_STREAM_VALUE, value = "/alerts" )
    public Flux<Alert> alerts()
    {
        Flux<Alert> stringFlux = service.getAllReactive();
        Flux<Long> durationFlux = Flux.interval( Duration.ofSeconds( 5 ) );
        return Flux.zip( stringFlux, durationFlux )
                   .map( Tuple2::getT1 );
    }
}
