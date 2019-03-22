package com.framework;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.jwt.crypto.sign.Signer;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args){
        String verifierKey = new RandomValueStringGenerator().generate();
        Signer signer = new MacSigner(verifierKey);
        SpringApplication.run(DemoApplication.class,args);
    }


}
