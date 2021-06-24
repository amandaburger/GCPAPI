package com.csr.prac;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.FileWriter;
import java.io.IOException;

@RestController
@SpringBootApplication
public class Application {
	private static final Logger log = LoggerFactory.getLogger(Application.class);
	private CountryRepository repository;
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


	@Autowired
	RestTemplate restTemplate;

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner demo(CountryRepository repo) {
		repository=repo;
		return (args) -> {
			Country[] countries = restTemplate.getForObject(
					"https://restcountries.eu/rest/v2/all",
					Country[].class);
			for (int i = 0; i < countries.length; i++) {
				repository.save(countries[i]);
			}
			//repository.save(countries[1]);
			log.info("Customers found with findAll():");
			log.info("-------------------------------");
			for (Country country : repository.findAll()) {
				log.info(country.toString());
			}
			log.info("");
		};

	}

	@RequestMapping("/countries2")
	public ResponseEntity<String> handle() throws IOException {
		Iterable<Country> countries = repository.findAll();
		String jsonObject = new Gson().toJson(countries);
		FileWriter myWriter = new FileWriter("countries.json");
		myWriter.write(jsonObject);
		myWriter.close();
		return new ResponseEntity<String>(jsonObject, HttpStatus.OK);


	}



}
