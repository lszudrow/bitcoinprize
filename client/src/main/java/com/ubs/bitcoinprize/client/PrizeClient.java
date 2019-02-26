package com.ubs.bitcoinprize.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;


@SpringBootApplication
class PrizeClient
{
    @Value( "${prize.server.uri}" )
    private String serverUri;


    public static void main( String[] args )
    {
        SpringApplication.run( PrizeClient.class, args );
    }


    @Bean
    WebClient client()
    {
        return WebClient.create( serverUri );
    }


    @Bean
    public CommandLineRunner commandLineRunner( WebClient client )
    {
        return args -> client.get()
                             .uri( "/api/alerts" )
                             .accept( MediaType.TEXT_EVENT_STREAM )
                             .exchange()
                             .doOnNext( clientResponse -> clientResponse.bodyToFlux( String.class ) )
                             .flatMapMany( clientResponse -> clientResponse.bodyToFlux( String.class ) )
                             .subscribe( System.out::println );
    }
}
