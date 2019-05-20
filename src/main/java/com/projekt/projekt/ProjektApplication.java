package com.projekt.projekt;

import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class ProjektApplication {

	private static final Logger log =  LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(ProjektApplication.class, args);

	}



	@Bean
	public CommandLineRunner demo(UzytkownikRepository repozytorium, KlientRepository repozytorium2){
	return (args) ->{
        Klient k = new Klient(2,3,null);
        Klient k2 = new Klient(2,3,null);
        Klient k3 = new Klient(2,3,null);
        repozytorium2.save(k);
        repozytorium2.save(k2);
        repozytorium2.save(k3);
	    Uzytkownik u = new Uzytkownik("adam", "qwerty", null, "ADMIN");
        Uzytkownik u1 = new Uzytkownik("adam2", "qwerty", null, "USER");
        Uzytkownik u2 = new Uzytkownik("adam3", "qwerty", new ArrayList<>(Arrays.asList(k2,k3)), "USER");
		repozytorium.save(u);
        repozytorium.save(u1);
        repozytorium.save(u2);


	};
	}


}
