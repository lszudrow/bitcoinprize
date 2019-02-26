package com.ubs.bitcoin.prize.server.client;

import com.ubs.bitcoin.prize.server.dto.Alert;
import com.ubs.bitcoin.prize.server.dto.AlertConfiguration;
import com.ubs.bitcoin.prize.server.service.AlertsConfigurationService;
import com.ubs.bitcoin.prize.server.service.AlertsService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.math.BigDecimal;


class XChangeClient
    implements Client
{
    private static final Logger logger = LoggerFactory.getLogger( XChangeClient.class );
    private AlertsService alertsService;
    private AlertsConfigurationService alertsConfigurationService;
    private MarketDataService marketDataService;


    XChangeClient( AlertsService alertsService, AlertsConfigurationService alertsConfigurationService, MarketDataService marketDataService )
    {
        this.alertsService = alertsService;
        this.alertsConfigurationService = alertsConfigurationService;
        this.marketDataService = marketDataService;
    }


    @Override
    @Scheduled( fixedRate = 2000L )
    public void fetchData()
    {
        alertsConfigurationService.getAlertsConfigurations()
                                  .forEach( this::checkLimit );
    }


    private void checkLimit( AlertConfiguration alertConfiguration )
    {
        Ticker ticker;
        BigDecimal previous = alertsService.getPreviousPrice( alertConfiguration.getCurrencyPair() );

        try
        {
            ticker = marketDataService.getTicker( new CurrencyPair( alertConfiguration.getCurrencyPair()
                                                                                      .replace( "-", "/" ) ) );
            if( previous.subtract( ticker.getLast() )
                        .abs()
                        .compareTo( new BigDecimal( alertConfiguration.getLimit() ) ) > 0 )
            {
                alertsService.add( new Alert(
                    String.valueOf( alertConfiguration.getLimit() ), ticker.getCurrencyPair()
                                                                           .toString(), ticker.getTimestamp()
                                                                                              .toString() ) );
            }
        }
        catch( IOException e )
        {
            logger.error( e.getMessage(), e );
        }
    }
}
