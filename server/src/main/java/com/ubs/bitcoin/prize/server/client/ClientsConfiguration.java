package com.ubs.bitcoin.prize.server.client;

import com.ubs.bitcoin.prize.server.service.AlertsConfigurationService;
import com.ubs.bitcoin.prize.server.service.AlertsService;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitstamp.BitstampExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ClientsConfiguration
{
    private AlertsService alertsService;
    private AlertsConfigurationService alertsConfigurationService;


    @Autowired
    ClientsConfiguration( AlertsService alertsService, AlertsConfigurationService alertsConfigurationService )
    {
        this.alertsService = alertsService;
        this.alertsConfigurationService = alertsConfigurationService;
    }


    @Bean
    @ExcludeFromTests
    public Client xchangeClient()
    {
        return new XChangeClient( alertsService, alertsConfigurationService,
            ExchangeFactory.INSTANCE.createExchange( BitstampExchange.class.getName() )
                                    .getMarketDataService() );
    }
}
