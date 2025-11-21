package com.aryak.hashicorp;

import com.aryak.hashicorp.service.VaultService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class SpringHashicorpVaultPocApplication {

    private final VaultService vaultService;

    public SpringHashicorpVaultPocApplication(VaultService vaultService) {
        this.vaultService = vaultService;
    }

    static void main(String[] args) {
        SpringApplication.run(SpringHashicorpVaultPocApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            // do something at startup

            // load secrets in vault
            Map<String, String> secrets = new HashMap<>();
            secrets.put("s3_id", "12345");
            secrets.put("s3_secret", "ssdr42@");
            vaultService.writeSecret("s3Creds", secrets, "aryak");

            // get
            Map<String, Object> readSecrets = vaultService.readSecret("api/credentials", "aryak");
            Map<String, Object> nonExistentKey = vaultService.readSecret("nonExistentPath", "aryak");
            System.out.println("nonExistentKey" + nonExistentKey);
            System.out.println("Read Secrets: " + readSecrets);

            Map<String, Object> apiKeyInfo = vaultService.readSecret("book-service", "aryak");
            System.out.println(apiKeyInfo);
        };
    }
}
