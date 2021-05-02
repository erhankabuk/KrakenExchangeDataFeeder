package com.erhankabuk.KrakenExchangeDataFeeder.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Embeddable;
import java.util.List;

@Embeddable
public class Result{
    @JsonProperty("XXBTZUSD")
    public List<List<Object>> XXBTZUSD;
    @JsonProperty("last")
    public String last;
}
