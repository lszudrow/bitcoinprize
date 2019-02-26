package com.ubs.bitcoin.prize.server.service;

import com.ubs.bitcoin.prize.server.dto.AlertConfiguration;
import com.ubs.bitcoin.prize.server.exception.IllegalCurrencyPairException;
import com.ubs.bitcoin.prize.server.repository.AlertsConfigurationRepository;
import org.knowm.xchange.currency.CurrencyPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;


@Service
public class AlertsConfigurationService
{
    private AlertsConfigurationRepository alertsConfigurationRepository;


    @Autowired
    AlertsConfigurationService( AlertsConfigurationRepository alertsConfigurationRepository )
    {
        this.alertsConfigurationRepository = alertsConfigurationRepository;
    }


    public Optional<AlertConfiguration> add( AlertConfiguration alertConfiguration ) throws IllegalCurrencyPairException
    {
        checkValidityOfCurrencyPair( alertConfiguration );
        alertsConfigurationRepository.save( alertConfiguration );
        return alertsConfigurationRepository.findById( alertConfiguration )
                                            .blockOptional();
    }


    public Optional<Void> delete( AlertConfiguration alertConfiguration )
    {
        return alertsConfigurationRepository.deleteById( alertConfiguration )
                                            .blockOptional();
    }


    public Stream<AlertConfiguration> getAlertsConfigurations()
    {
        return alertsConfigurationRepository.findAll()
                                            .toStream();
    }


    private void checkValidityOfCurrencyPair( AlertConfiguration alertConfiguration ) throws IllegalCurrencyPairException
    {
        try
        {
            new CurrencyPair( alertConfiguration.getCurrencyPair()
                                                .replace( "-", "/" ) );
        }
        catch( IllegalArgumentException e )
        {
            throw new IllegalCurrencyPairException( e );
        }
    }
}
