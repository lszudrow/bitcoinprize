package com.ubs.bitcoin.prize.server.exception;

public class IllegalCurrencyPairException
    extends Exception
{
    public IllegalCurrencyPairException( Exception e )
    {
        super( e );
    }
}
