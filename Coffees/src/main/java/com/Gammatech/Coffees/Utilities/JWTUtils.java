/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.Gammatech.Coffees.Utilities;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.springframework.expression.ParseException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

/**
 *
 * JWTUtils es una clase que proporciona utilidades para generar, validar y extraer información de tokens JWT (JSON Web Tokens).
 * Utiliza una clave secreta para firmar y verificar los tokens, y maneja la creación de reclamaciones como el nombre de usuario y el rol del usuario.
 * Esta clase es esencial para la autenticación y autorización en aplicaciones que utilizan JWT para gestionar sesiones de usuario.
 * @author Aaron
 */
@Component
public class JWTUtils {

    private static final String SECRETKEY = "keybladekeybladekeybladekeyblade";

    private static final long EXPIRATIONTIME = 1000 * 60 * 60 * 12; // 12 horas en milisegundos

    /**
     * Genera un token JWT firmado con la clave secreta y con las reclamaciones del usuario.
     * @param userDetails Los detalles del usuario para los cuales se generará el token.
     * @return El token JWT generado como una cadena.
     */
    public String generateToken(UserDetails userDetails) {
        try {
            JWSSigner signer = new MACSigner(SECRETKEY.getBytes(StandardCharsets.UTF_8)); // Crear un firmante con la clave secreta en formato UTF-8

            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(userDetails.getUsername()) // El sujeto del token es el nombre de usuario
                    .claim("role", userDetails.getAuthorities().iterator().next().getAuthority()) // Asumiendo que el usuario tiene al menos un rol
                    .issueTime(new Date()) // Hora de emisión del token
                    .expirationTime(new Date(System.currentTimeMillis() + EXPIRATIONTIME)) // Hora de expiración del token (12 horas después de la emisión)
                    .build(); // Construcción del conjunto de reclamaciones del JWT

            SignedJWT signedJWT = new SignedJWT(
                    new JWSHeader(JWSAlgorithm.HS256), // Usando el algoritmo de firma HMAC SHA-256
                    claimsSet // Conjunto de reclamaciones del JWT
            );

            signedJWT.sign(signer);

            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException("Error al firmar el token JWT", e);
        }
    }

    /**
     * Extrae el nombre de usuario del token JWT.
     * @param token El token JWT del cual se extraerá el nombre de usuario.
     * @return El nombre de usuario extraído del token.
     */
    
    public String extractUsername(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
            return claimsSet.getSubject(); // Retorna el nombre de usuario del sujeto del token
        } catch (ParseException | java.text.ParseException e) {
            throw new RuntimeException("Error al extraer el nombre de usuario del token JWT", e);
        }
    }

    /**
     * Valida el token JWT verificando su firma, el nombre de usuario y la fecha de expiración.
     * @param token El token JWT a validar.
     * @param userDetails Los detalles del usuario para comparar con el token.
     * @return true si el token es válido, false en caso contrario.
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            SignedJWT jwt = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(SECRETKEY.getBytes(StandardCharsets.UTF_8)); // Crear un verificador con la clave secreta en formato UTF-8
            return jwt.verify(verifier) && userDetails.getUsername().equals(jwt.getJWTClaimsSet().getSubject()) && new Date().before(jwt.getJWTClaimsSet().getExpirationTime()); // Verifica la firma, el nombre de usuario y que el token no haya expirado
        } catch (JOSEException | java.text.ParseException e) {
            return false; // Si hay un error al parsear el token, se considera inválido
        }
    }
    

}
