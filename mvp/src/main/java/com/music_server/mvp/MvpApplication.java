package com.music_server.mvp;

import javax.sql.DataSource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class MvpApplication{
	public static void main(String[] args) {
		SpringApplication.run(MvpApplication.class, args);
	}

}
