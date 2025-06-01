package com.Gammatech.Coffees;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * * Clase principal de la aplicación Coffees.
 * * Esta clase inicia la aplicación Spring Boot y configura el codificador de contraseñas.
 * @author Aaron
 */
@SpringBootApplication
public class CoffeesApplication {

	public static void main(String[] args) {SpringApplication.run(CoffeesApplication.class, args);}
	
	/**
	 * * Configura un codificador de contraseñas BCrypt.
	 * * Este codificador se utiliza para encriptar las contraseñas de los usuarios.
	 * @return un objeto PasswordEncoder configurado con BCrypt
	 */

	@Bean
	public PasswordEncoder passwordEncoder(){
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder;
	}

}
