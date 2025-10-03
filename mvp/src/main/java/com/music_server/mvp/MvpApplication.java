package com.music_server.mvp;

import javax.sql.DataSource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import lombok.extern.java.Log;

@SpringBootApplication
@Log
public class MvpApplication implements CommandLineRunner{

	private final DataSource dataSource;

	public MvpApplication(final DataSource dataSource) {this.dataSource = dataSource;}
	public static void main(String[] args) {
		SpringApplication.run(MvpApplication.class, args);
	}

	@Override
	public void run(final String... args) {
		System.out.println("DataSource: " + dataSource.toString());
		final JdbcTemplate restTemplate = new JdbcTemplate(dataSource);
		restTemplate.execute("select 1");

	}

}
