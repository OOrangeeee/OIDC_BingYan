package com.oidc.oidc.service.interfaces.client;

import java.util.Map;

/**
 * @author 晋晨曦
 */
public interface ClientRegisterService {
    Map<String,String> getClientRigister(String clientName,String clientPassword,String clientRedirectUrl,String clientEmail);

    Map<String, String> confirmClientAccount(String clientConfirmationToken);
}
