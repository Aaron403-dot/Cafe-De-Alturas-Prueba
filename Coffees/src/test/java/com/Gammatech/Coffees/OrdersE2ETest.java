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
import com.Gammatech.Coffees.Entities.Orders;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts = "/user_creation.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class OrdersE2ETest {
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
    public void AdminCanCreateOrder() {
        Orders order = new Orders();
        order.setClientId(1L);
        order.setTotalValue(10.5);
        order.setState("PENDIENTE");
        String url = "http://localhost:" + port + "/orders";
        String token = getTokenForAdminUser();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity<Orders> httpRequest = new HttpEntity<>(order, headers);
        ResponseEntity<Orders> response = restTemplate.postForEntity(url, httpRequest, Orders.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
        assertEquals(order.getState(), response.getBody().getState());
    }

    @Test
    public void AdminCanReadOrder() {
        // Primero crea una orden
        Orders order = new Orders();
        order.setClientId(1L);
        order.setTotalValue(20.0);
        order.setState("EN_PROCESO");
        String createUrl = "http://localhost:" + port + "/orders";
        String token = getTokenForAdminUser();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity<Orders> createRequest = new HttpEntity<>(order, headers);
        ResponseEntity<Orders> createResponse = restTemplate.postForEntity(createUrl, createRequest, Orders.class);
        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        assertNotNull(createResponse.getBody());
        Long createdId = createResponse.getBody().getId();

        // Ahora lee la orden creada
        String url = "http://localhost:" + port + "/orders/" + createdId;
        headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> httpRequest = new HttpEntity<>(headers);
        ResponseEntity<Orders> response = restTemplate.exchange(url, HttpMethod.GET, httpRequest, Orders.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(createdId, response.getBody().getId());
    }
}
