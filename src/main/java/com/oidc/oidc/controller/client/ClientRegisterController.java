package com.oidc.oidc.controller.client;

import com.oidc.oidc.service.interfaces.client.ClientRegisterService;
import com.oidc.oidc.service.interfaces.tools.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 晋晨曦
 */
@RestController
public class ClientRegisterController {

    final ClientRegisterService clientRegisterService;
    final CredentialService credentialService;

    public ClientRegisterController(ClientRegisterService clientRegisterService, CredentialService credentialService) {
        this.clientRegisterService = clientRegisterService;
        this.credentialService = credentialService;
    }

    @PostMapping("/client/register/")
    public Map<String, String> getClientRegister(@RequestParam Map<String, String> mapParams) {
        String clientEmail = mapParams.get("clientEmail");
        String clientRedirectionUrl = mapParams.get("clientRedirectionUrl");
        Map<String, String> idPassword = credentialService.generateCredentials();
        String clientName = idPassword.get("id");
        String clientPassword = idPassword.get("password");
        return clientRegisterService.getClientRigister(clientName, clientPassword, clientRedirectionUrl, clientEmail);
    }

    @GetMapping("/client/confirm")
    public Map<String, String> confirmUserAccount(@RequestParam("token") String clientConfirmationToken) {
        return clientRegisterService.confirmClientAccount(clientConfirmationToken);
    }
}
