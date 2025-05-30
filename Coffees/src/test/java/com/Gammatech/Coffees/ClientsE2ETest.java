/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit3TestClass.java to edit this template
 */

package com.Gammatech.Coffees;

import java.util.ArrayList;
import java.util.List;
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
    
    @Test
    public void NonAdminCannotCreateClient() {
        // Preparación
        Clients client = new Clients();
        client.setName("UserClient");
        client.setEmail("user@notadmin.com");
        client.setSurName("User");
        client.setPhone("123456789");
        client.setDirection("Fake Street 1, City");
        String url = "http://localhost:" + port + "/clients";
        String token = getTokenForAverageUser();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity<Clients> httpRequest = new HttpEntity<>(client, headers);
    
        // Acción
        ResponseEntity<String> response = restTemplate.postForEntity(url, httpRequest, String.class);
    
        // Verificación: Debe ser FORBIDDEN o UNAUTHORIZED
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }
    
    @Test
    public void AdminCanReadExistingClient() {
        // Crear un cliente de prueba primero
        Clients client = new Clients();
        client.setName("TestClientRead");
        client.setEmail("readclient@demo.com");
        client.setSurName("Read");
        client.setPhone("111111111");
        client.setDirection("Read Street 1, City");
        String createUrl = "http://localhost:" + port + "/clients";
        String token = getTokenForAdminUser();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity<Clients> createRequest = new HttpEntity<>(client, headers);
        ResponseEntity<Clients> createResponse = restTemplate.postForEntity(createUrl, createRequest, Clients.class);
        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        assertNotNull(createResponse.getBody());
        Long createdId = createResponse.getBody().getId();

        // Ahora leer el cliente creado
        String url = "http://localhost:" + port + "/clients/" + createdId;
        headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> httpRequest = new HttpEntity<>(headers);
        ResponseEntity<Clients> response = restTemplate.exchange(url, org.springframework.http.HttpMethod.GET, httpRequest, Clients.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(createdId, response.getBody().getId());
    }
    
    @Test
    public void NonAdminCannotDeleteClient() {
        // Preparación
        String url = "http://localhost:" + port + "/clients/1";
        String token = getTokenForAverageUser();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> httpRequest = new HttpEntity<>(headers);
    
        // Acción
        ResponseEntity<String> response = restTemplate.exchange(url, org.springframework.http.HttpMethod.DELETE, httpRequest, String.class);
    
        // Verificación: Debe ser FORBIDDEN o UNAUTHORIZED
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }
    
    @Test
    public void AdminCanReadExistingClients_MultipleIds() {
        String token = getTokenForAdminUser();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        Long[] createdIds = new Long[3];
        // Crear 3 clientes de prueba
        for (int i = 0; i < 3; i++) {
            Clients client = new Clients();
            client.setName("TestClient" + i);
            client.setEmail("test" + i + "@demo.com");
            client.setSurName("Test" + i);
            client.setPhone("10000000" + i);
            client.setDirection("Street " + i);
            HttpEntity<Clients> createRequest = new HttpEntity<>(client, headers);
            ResponseEntity<Clients> createResponse = restTemplate.postForEntity("http://localhost:" + port + "/clients", createRequest, Clients.class);
            assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
            assertNotNull(createResponse.getBody());
            createdIds[i] = createResponse.getBody().getId();
        }

        // Leer los 3 clientes creados
        headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> httpRequest = new HttpEntity<>(headers);
        for (int i = 0; i < 3; i++) {
            String url = "http://localhost:" + port + "/clients/" + createdIds[i];
            ResponseEntity<Clients> response = restTemplate.exchange(url, org.springframework.http.HttpMethod.GET, httpRequest, Clients.class);
            assertEquals(HttpStatus.OK, response.getStatusCode(), "Status for id=" + createdIds[i]);
            assertNotNull(response.getBody(), "Body for id=" + createdIds[i]);
            assertEquals(createdIds[i], response.getBody().getId(), "Id match for id=" + createdIds[i]);
        }
    }

    @Test
    public void AdminCanUpdateClient() {
        // Crear un cliente de prueba
        Clients client = new Clients();
        client.setName("ClientToUpdate");
        client.setEmail("update@demo.com");
        client.setSurName("Update");
        client.setPhone("222222222");
        client.setDirection("Update Street 2, City");
        String createUrl = "http://localhost:" + port + "/clients";
        String token = getTokenForAdminUser();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity<Clients> createRequest = new HttpEntity<>(client, headers);
        ResponseEntity<Clients> createResponse = restTemplate.postForEntity(createUrl, createRequest, Clients.class);
        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        assertNotNull(createResponse.getBody());
        Long createdId = createResponse.getBody().getId();

        // Modificar el cliente
        client.setName("ClientUpdated");
        client.setPhone("999999999");
        client.setId(createdId); // Asegurarse de que el ID se mantenga para la actualización
        String updateUrl = "http://localhost:" + port + "/clients/" + createdId;
        HttpEntity<Clients> updateRequest = new HttpEntity<>(client, headers);
        ResponseEntity<Clients> updateResponse = restTemplate.exchange(updateUrl, org.springframework.http.HttpMethod.PUT, updateRequest, Clients.class);
        System.out.println("Token admin: " + token);
        System.out.println("Update URL: " + updateUrl);
        System.out.println("Client ID: " + createdId);
        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        assertNotNull(updateResponse.getBody());
        assertEquals("ClientUpdated", updateResponse.getBody().getName());
        assertEquals("999999999", updateResponse.getBody().getPhone());
    }

    @Test
    public void AdminCanCreateAndTrackMultipleClients() {
        String token = getTokenForAdminUser();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        List<Long> createdIds = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Clients client = new Clients();
            client.setName("Client" + i);
            client.setEmail("client" + i + "@demo.com");
            client.setSurName("Surname" + i);
            client.setPhone("10000000" + i);
            client.setDirection("Street " + i);
            HttpEntity<Clients> createRequest = new HttpEntity<>(client, headers);
            ResponseEntity<Clients> createResponse = restTemplate.postForEntity("http://localhost:" + port + "/clients", createRequest, Clients.class);
            assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
            assertNotNull(createResponse.getBody());
            createdIds.add(createResponse.getBody().getId());
        }

        // Ahora puedes usar createdIds para leerlos uno por uno o hacer más pruebas
        for (Long id : createdIds) {
            String url = "http://localhost:" + port + "/clients/" + id;
            headers = new HttpHeaders();
            headers.setBearerAuth(token);
            HttpEntity<Void> httpRequest = new HttpEntity<>(headers);
            ResponseEntity<Clients> response = restTemplate.exchange(url, org.springframework.http.HttpMethod.GET, httpRequest, Clients.class);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(id, response.getBody().getId());
        }
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
        AuthRequest authRequest = new AuthRequest("NormOfTheNorth", "PIssLemmings");
        String url = "http://localhost:" + port + "/auth/login";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<AuthRequest> httpRequest = new HttpEntity<>(authRequest, headers);

        // Acción
        ResponseEntity<AuthResponse> response = restTemplate.postForEntity(url, httpRequest, AuthResponse.class);


        return Objects.requireNonNull(response.getBody()).getToken();
    }


}
