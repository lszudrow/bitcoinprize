package com.ubs.bitcoin.prize.server.controller;

import com.ubs.bitcoin.prize.server.dto.AlertConfiguration;
import com.ubs.bitcoin.prize.server.exception.IllegalCurrencyPairException;
import com.ubs.bitcoin.prize.server.service.AlertsConfigurationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping( "/api" )
public class AlertsConfigurationController
{
    private static final Logger logger = LoggerFactory.getLogger( AlertsConfigurationController.class );
    private AlertsConfigurationService alertsConfigurationService;


    @Autowired
    AlertsConfigurationController( AlertsConfigurationService alertsConfigurationService )
    {
        this.alertsConfigurationService = alertsConfigurationService;
    }


    @PostMapping( value = "/alertConfiguration", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<AlertConfiguration> addAlertConfiguration( @RequestBody AlertConfiguration alertConfiguration )
    {
        Optional<AlertConfiguration> saved;
        try
        {
            saved = alertsConfigurationService.add( alertConfiguration );
        }
        catch( IllegalCurrencyPairException e )
        {
            logger.trace( e.getMessage(), e );
            return new ResponseEntity<>( HttpStatus.CONFLICT );
        }
        return saved.isPresent() ? new ResponseEntity<>( saved.get(), HttpStatus.CREATED ) : new ResponseEntity<>( HttpStatus.CONFLICT );
    }


    @DeleteMapping( value = "/alertConfiguration" )
    public void deleteAlertConfiguration( @RequestBody AlertConfiguration alertConfiguration )
    {
        alertsConfigurationService.delete( alertConfiguration );
    }
}
