package com.erhankabuk.KrakenExchangeDataFeeder.Model;

import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

public class Root{
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    public List<Object> error;
    @Embedded
    public Result result;

    public Root(List<Object> error, Result result) {
        this.error = error;
        this.result = result;
    }

    public Root() {

    }

    public List<Object> getError() {
        return error;
    }

    public void setError(List<Object> error) {
        this.error = error;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
