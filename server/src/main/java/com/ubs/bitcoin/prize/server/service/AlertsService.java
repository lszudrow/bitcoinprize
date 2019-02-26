package com.ubs.bitcoin.prize.server.service;

import com.ubs.bitcoin.prize.server.dto.Alert;
import com.ubs.bitcoin.prize.server.repository.AlertsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.Optional;


@Service
public class AlertsService
{
    private AlertsRepository repository;


    @Autowired
    AlertsService( AlertsRepository alertsRepository )
    {
        this.repository = alertsRepository;
    }


    public void add( Alert alert )
    {
        repository.save( alert );
    }


    public Flux<Alert> getAllReactive()
    {
        return repository.findAll();
    }


    public BigDecimal getPreviousPrice( String currencyPair )
    {
        Optional<Alert> alert = repository.findById( currencyPair )
                                          .blockOptional();
        return new BigDecimal( alert.isPresent() ? alert.get()
                                                        .getLimit() : "0" );
    }
}
