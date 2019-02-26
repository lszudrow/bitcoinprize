package com.ubs.bitcoin.prize.server;

import com.ubs.bitcoin.prize.server.client.ExcludeFromTests;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;


@SpringBootApplication( exclude = { DataSourceAutoConfiguration.class } )
@ComponentScan( excludeFilters = @ComponentScan.Filter( type = FilterType.ANNOTATION, value = ExcludeFromTests.class ) )
public class TestPrizeServer
{
    public static void main( String[] args )
    {
        SpringApplication.run( TestPrizeServer.class, args );
    }
}
