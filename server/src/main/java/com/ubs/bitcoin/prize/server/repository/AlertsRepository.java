package com.ubs.bitcoin.prize.server.repository;

import com.ubs.bitcoin.prize.server.dto.Alert;
import org.reactivestreams.Publisher;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Repository
public class AlertsRepository
    implements ReactiveCrudRepository<Alert, String>
{
    private Map<String, Alert> repository = new ConcurrentHashMap<>();


    @Override
    public <S extends Alert> Mono<S> save( S s )
    {
        Alert put = repository.put( s.getTimestamp(), s );
        return (Mono<S>)Mono.just( put == null ? s : put );
    }


    @Override
    public <S extends Alert> Flux<S> saveAll( Iterable<S> iterable )
    {
        return null;
    }


    @Override
    public <S extends Alert> Flux<S> saveAll( Publisher<S> publisher )
    {
        return null;
    }


    @Override
    public Mono<Alert> findById( String s )
    {
        return Mono.justOrEmpty( repository.get( s ) );
    }


    @Override
    public Mono<Alert> findById( Publisher<String> publisher )
    {
        return null;
    }


    @Override
    public Mono<Boolean> existsById( String s )
    {
        return null;
    }


    @Override
    public Mono<Boolean> existsById( Publisher<String> publisher )
    {
        return null;
    }


    @Override
    public Flux<Alert> findAll()
    {
        return Flux.fromIterable( repository.values() );
    }


    @Override
    public Flux<Alert> findAllById( Iterable<String> iterable )
    {
        return null;
    }


    @Override
    public Flux<Alert> findAllById( Publisher<String> publisher )
    {
        return null;
    }


    @Override
    public Mono<Long> count()
    {
        return null;
    }


    @Override
    public Mono<Void> deleteById( String s )
    {
        return null;
    }


    @Override
    public Mono<Void> deleteById( Publisher<String> publisher )
    {
        return null;
    }


    @Override
    public Mono<Void> delete( Alert alert )
    {
        return null;
    }


    @Override
    public Mono<Void> deleteAll( Iterable<? extends Alert> iterable )
    {
        return null;
    }


    @Override
    public Mono<Void> deleteAll( Publisher<? extends Alert> publisher )
    {
        return null;
    }


    @Override
    public Mono<Void> deleteAll()
    {
        return null;
    }
}
