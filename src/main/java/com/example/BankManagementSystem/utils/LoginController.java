package com.example.BankManagementSystem.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.BankManagementSystem.client.Client;
import com.example.BankManagementSystem.client.ClientRepository;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping(path = "api/v1/auth")
public class LoginController {

    @Autowired
    private final ClientRepository clientRepository;

    public LoginController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @PostMapping(path = "/login", produces= MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> login(@RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        JSONObject object = new JSONObject();
        String[] authArr = header.split(" ");
        byte[] decodedBytes = Base64.getDecoder().decode(authArr[1]);
        String decodedString = new String(decodedBytes);
        String authEmail = decodedString.split(":")[0];
        String authPassword = decodedString.split(":")[1];

        Optional<Client> optionalClient = clientRepository.findByEmail(authEmail);

        if (optionalClient.isPresent() && secretKey.checkPassword(authPassword, optionalClient.get().getPassword())) {
            try {
                Algorithm algorithm = Algorithm.HMAC256("secret");
                String token = JWT.create()
                        .withIssuer("auth0")
                        .sign(algorithm);


                object.append("success", true);
                object.append("client", optionalClient.get());
                object.append("token", token);

                return object.toMap();
            } catch (JWTCreationException exception) {

                object.append("success", false);
                object.append("error", exception.toString());
                return object.toMap();
            }
        } else {
            object.append("success", false);
            object.append("error", "Not found in database");
            return object.toMap();
        }

    }

    @GetMapping(path = "/verify")
    public boolean verify(@RequestHeader(HttpHeaders.AUTHORIZATION) String header){
        String[] authArr = header.split(" ");
        String token = authArr[1];
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier =JWT.require(algorithm).withIssuer("auth0").build();
            DecodedJWT jwt =verifier.verify(token);
            return true;
        }catch (JWTVerificationException exception){
            return false;
        }
    }



    @GetMapping(path = "/logout")
    public void logout(){

    }

}
