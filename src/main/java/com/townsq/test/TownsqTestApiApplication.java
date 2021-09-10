package com.townsq.test;

import java.io.FileNotFoundException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import com.townsq.test.domain.database.PreparaDatabase;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class TownsqTestApiApplication {

	public static void main(String[] args) throws FileNotFoundException {
		SpringApplication.run(TownsqTestApiApplication.class, args);
		PreparaDatabase database = new PreparaDatabase();
		database.preparaDatabase();
		System.out.println(database.listarPermissoesPorEmail("joao.costa@gmail.com"));

	}

}
