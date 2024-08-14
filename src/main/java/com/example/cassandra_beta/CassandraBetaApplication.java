package com.example.cassandra_beta;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CassandraBetaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CassandraBetaApplication.class, args);
		try (CqlSession session = CqlSession.builder().build()) {
			System.out.println("Connected to Cassandra!");
		}
	}

}
