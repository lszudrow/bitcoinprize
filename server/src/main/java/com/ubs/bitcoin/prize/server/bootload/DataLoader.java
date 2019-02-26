package com.ubs.bitcoin.prize.server.bootload;

import com.ubs.bitcoin.prize.server.dto.AlertConfiguration;
import com.ubs.bitcoin.prize.server.exception.IllegalCurrencyPairException;
import com.ubs.bitcoin.prize.server.service.AlertsConfigurationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


@Component
public class DataLoader
    implements ApplicationListener<ContextRefreshedEvent>
{
    private static final Logger logger = LoggerFactory.getLogger( DataLoader.class );
    @Autowired
    private AlertsConfigurationService alertsConfigurationService;


    @Override
    public void onApplicationEvent( ContextRefreshedEvent contextRefreshedEvent )
    {
        logger.info( "Starting data loading" );
        try
        {
            alertsConfigurationService.add( new AlertConfiguration( "BTC-EUR", "0.01" ) );
            alertsConfigurationService.add( new AlertConfiguration( "BTC-USD", "0.01" ) );
        }
        catch( IllegalCurrencyPairException e )
        {
            logger.trace( e.getMessage(), e );
        }
    }
}