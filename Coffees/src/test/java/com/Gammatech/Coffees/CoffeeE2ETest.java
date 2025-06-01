package com.Gammatech.Coffees;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import com.Gammatech.Coffees.Controllers.AuthRequest;
import com.Gammatech.Coffees.Controllers.AuthResponse;
import com.Gammatech.Coffees.Entities.Coffee;

/**
 * pruebas de integración para la entidad Coffee
 * Estas pruebas verifican que un usuario administrador pueda crear y leer cafés.
 * Se asume que el usuario administrador ya ha sido creado en la base de datos
 * antes de ejecutar las pruebas, utilizando el script user_creation.sql.
 * * Las pruebas utilizan TestRestTemplate para realizar solicitudes HTTP a la API
 * de Coffee.
 * * Se utiliza la anotación @Sql para ejecutar un script SQL antes de las pruebas,
 * lo que permite preparar la base de datos con un usuario administrador.
 * @author Aaron
 * @version 1.0
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts = "/user_creation.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class CoffeeE2ETest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getTokenForAdminUser() {
        AuthRequest authRequest = new AuthRequest("AdminUser", "Admin123");
        String url = "http://localhost:" + port + "/auth/login";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<AuthRequest> httpRequest = new HttpEntity<>(authRequest, headers);
        ResponseEntity<AuthResponse> response = restTemplate.postForEntity(url, httpRequest, AuthResponse.class);
        return Objects.requireNonNull(response.getBody()).getToken();
    }

    @Test
    public void AdminCanCreateCoffee() {
        Coffee coffee = new Coffee();
        coffee.setName("TestCoffee");
        coffee.setDescription("Un café de prueba");
        coffee.setPrice(5.5);
        coffee.setStock(10);
        coffee.setImage("test.jpg");
        String url = "http://localhost:" + port + "/coffee";
        String token = getTokenForAdminUser();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity<Coffee> httpRequest = new HttpEntity<>(coffee, headers);
        ResponseEntity<Coffee> response = restTemplate.postForEntity(url, httpRequest, Coffee.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
        assertEquals(coffee.getName(), response.getBody().getName());
    }

    @Test
    public void AdminCanReadCoffee() {
        // Primero crea un café
        Coffee coffee = new Coffee();
        coffee.setName("ReadCoffee");
        coffee.setDescription("Café para leer");
        coffee.setPrice(7.0);
        coffee.setStock(5);
        coffee.setImage("read.jpg");
        String createUrl = "http://localhost:" + port + "/coffee";
        String token = getTokenForAdminUser();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity<Coffee> createRequest = new HttpEntity<>(coffee, headers);
        ResponseEntity<Coffee> createResponse = restTemplate.postForEntity(createUrl, createRequest, Coffee.class);
        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        assertNotNull(createResponse.getBody());
        Long createdId = createResponse.getBody().getId();

        // Ahora lee el café creado
        String url = "http://localhost:" + port + "/coffee/" + createdId;
        headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> httpRequest = new HttpEntity<>(headers);
        ResponseEntity<Coffee> response = restTemplate.exchange(url, HttpMethod.GET, httpRequest, Coffee.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(createdId, response.getBody().getId());
    }
}
