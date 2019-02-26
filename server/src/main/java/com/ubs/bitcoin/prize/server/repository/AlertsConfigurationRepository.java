package com.ubs.bitcoin.prize.server.repository;

import com.ubs.bitcoin.prize.server.dto.AlertConfiguration;
import org.reactivestreams.Publisher;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Repository
public class AlertsConfigurationRepository
    implements ReactiveCrudRepository<AlertConfiguration, AlertConfiguration>
{
    private Map<AlertConfiguration, AlertConfiguration> repository = new ConcurrentHashMap<>();


    @Override
    public <S extends AlertConfiguration> Mono<S> save( S s )
    {
        try
        {
            repository.put( s, s );
        }
        catch( Exception e )
        {
            return Mono.error( e );
        }
        return (Mono<S>)Mono.justOrEmpty( repository.get( s ) );
    }


    @Override
    public <S extends AlertConfiguration> Flux<S> saveAll( Iterable<S> iterable )
    {
        return null;
    }


    @Override
    public <S extends AlertConfiguration> Flux<S> saveAll( Publisher<S> publisher )
    {
        return null;
    }


    @Override
    public Mono<AlertConfiguration> findById( AlertConfiguration alertConfiguration )
    {
        return Mono.justOrEmpty( repository.get( alertConfiguration ) );
    }


    @Override
    public Mono<AlertConfiguration> findById( Publisher<AlertConfiguration> publisher )
    {
        return null;
    }


    @Override
    public Mono<Boolean> existsById( AlertConfiguration alertConfiguration )
    {
        return Mono.just( repository.containsKey( alertConfiguration ) );
    }


    @Override
    public Mono<Boolean> existsById( Publisher<AlertConfiguration> publisher )
    {
        return null;
    }


    @Override
    public Flux<AlertConfiguration> findAll()
    {
        return Flux.fromIterable( repository.values() );
    }


    @Override
    public Flux<AlertConfiguration> findAllById( Iterable<AlertConfiguration> iterable )
    {
        return null;
    }


    @Override
    public Flux<AlertConfiguration> findAllById( Publisher<AlertConfiguration> publisher )
    {
        return null;
    }


    @Override
    public Mono<Long> count()
    {
        return null;
    }


    @Override
    public Mono<Void> deleteById( AlertConfiguration alertConfiguration )
    {
        try
        {
            repository.remove( alertConfiguration );
        }
        catch( Exception e )
        {
            return Mono.error( e );
        }
        return Mono.empty();
    }


    @Override
    public Mono<Void> deleteById( Publisher<AlertConfiguration> publisher )
    {
        return null;
    }


    @Override
    public Mono<Void> delete( AlertConfiguration alertConfiguration )
    {
        return null;
    }


    @Override
    public Mono<Void> deleteAll( Iterable<? extends AlertConfiguration> iterable )
    {
        return null;
    }


    @Override
    public Mono<Void> deleteAll( Publisher<? extends AlertConfiguration> publisher )
    {
        return null;
    }


    @Override
    public Mono<Void> deleteAll()
    {
        repository.clear();
        return Mono.empty();
    }
}
