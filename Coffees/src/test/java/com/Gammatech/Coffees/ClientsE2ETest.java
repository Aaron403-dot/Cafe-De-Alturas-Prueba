/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit3TestClass.java to edit this template
 */

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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import com.Gammatech.Coffees.Controllers.AuthRequest;
import com.Gammatech.Coffees.Controllers.AuthResponse;
import com.Gammatech.Coffees.Entities.Clients;

/**
 *
 * @author Aaron
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts = "/user_creation.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class ClientsE2ETest {
    
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void AdminCreateClient(){
        //Preparación
        Clients client = new Clients();
        client.setName("TestClient");
        client.setEmail("spiff@reanu.uk");
        client.setSurName("Kebbs");
        client.setPhone("199899233");
        client.setDirection("Nonsense Street 123, London, UK");
        String url = "http://localhost:" + port + "/clients";
        String token = getTokenForAdminUser();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity<Clients> httpRequest = new HttpEntity<>(client, headers);

        //Action
        ResponseEntity<Clients> response = restTemplate.postForEntity(url, httpRequest, Clients.class);
        //Verificación
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
        assertEquals(client.getName(), response.getBody().getName());
    }




    private String getTokenForAdminUser() {
        // Preparación
        AuthRequest authRequest = new AuthRequest("AdminUser", "Admin123");
        String url = "http://localhost:" + port + "/auth/login";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<AuthRequest> httpRequest = new HttpEntity<>(authRequest, headers);

        // Acción
        ResponseEntity<AuthResponse> response = restTemplate.postForEntity(url, httpRequest, AuthResponse.class);


        return Objects.requireNonNull(response.getBody()).getToken();
    }

    private String getTokenForAverageUser() {
        // Preparación
        AuthRequest authRequest = new AuthRequest("NormOfTheNorth", "PIssLemings");
        String url = "http://localhost:" + port + "/auth/login";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<AuthRequest> httpRequest = new HttpEntity<>(authRequest, headers);

        // Acción
        ResponseEntity<AuthResponse> response = restTemplate.postForEntity(url, httpRequest, AuthResponse.class);


        return Objects.requireNonNull(response.getBody()).getToken();
    }


}
