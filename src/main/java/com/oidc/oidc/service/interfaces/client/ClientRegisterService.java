package com.oidc.oidc.service.interfaces.client;

import java.util.Map;

/**
 * @author 晋晨曦
 */
public interface ClientRigisterService {
    Map<String,String> getClientRigister(String clientName,String clientPassword,String clientConfirmPassword,String clientRedirectUrl);

    Map<String, String> confirmClientAccount(String clientConfirmationToken);
}
