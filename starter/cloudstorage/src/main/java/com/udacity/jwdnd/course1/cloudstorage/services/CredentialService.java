package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private CredentialsMapper credentialsMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialsMapper credentialsMapper, EncryptionService encryptionService) {
        this.credentialsMapper = credentialsMapper;
        this.encryptionService = encryptionService;
    }

    public int saveCredential(Credential credential) {
        SecureRandom secureRandom = new SecureRandom();
        byte [] salt = new byte[16];
        secureRandom.nextBytes(salt);
        String encodedKey = Base64.getEncoder().encodeToString(salt);
        credential.setKey(encodedKey);
        credential.setPassword(encryptionService.encryptValue(credential.getPassword(), encodedKey));
        if (credential.getCredentialId() != null && credentialsMapper.getCredentialById(credential.getCredentialId()) != null) {
            //credentials exist. Update operation
            return credentialsMapper.updateCredential(credential);
        }
        return credentialsMapper.insertCredential(credential);
    }

    public int deleteCredential(int credentialId) {
        return credentialsMapper.deleteCredential(credentialId);
    }

    public List<Credential> getCredentialsByUser(int userId) {
        return credentialsMapper.getCredentials(userId);
    }
}
