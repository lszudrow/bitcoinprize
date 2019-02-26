package com.ubs.bitcoin.prize.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Alert
{
    private String limit;
    private String currencyPair;
    private String timestamp;
}
