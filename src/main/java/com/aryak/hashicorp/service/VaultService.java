package com.aryak.hashicorp.service;

import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultKeyValueOperations;
import org.springframework.vault.core.VaultKeyValueOperationsSupport;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponse;

import java.util.Map;

@Service
public class VaultService {

    private final VaultTemplate vaultTemplate;

    public VaultService(VaultTemplate vaultTemplate) {
        this.vaultTemplate = vaultTemplate;
    }

    public void writeSecret(String path, Map<String, String> secrets, String engine) {
        VaultKeyValueOperations operations = getOps(engine);
        operations.put(path, secrets);
    }

    public Map<String, Object> readSecret(String path, String engine) {
        VaultResponse response = getOps(engine).get(path);
        return response != null ? response.getData() : Map.of();
    }

    private VaultKeyValueOperations getOps(String engine) {
        return vaultTemplate.opsForKeyValue(engine, VaultKeyValueOperationsSupport.KeyValueBackend.KV_2);
    }
}
