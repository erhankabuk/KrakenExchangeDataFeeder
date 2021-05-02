package com.erhankabuk.KrakenExchangeDataFeeder;

import com.erhankabuk.KrakenExchangeDataFeeder.ServiceLayer.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KrakenExchangeDataFeederApplication implements CommandLineRunner {
@Autowired
ServiceLayer serviceLayer;
	public static void main(String[] args) {
		SpringApplication.run(KrakenExchangeDataFeederApplication.class, args).close();
	}

	@Override
	public void run(String... args) throws Exception {
	serviceLayer.checkDatabaseIsUpToDate();
	}

}
