package com.oidc.oidc.service.impl.tools;

import com.oidc.oidc.service.interfaces.tools.UrlValidationService;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.net.MalformedURLException;

/**
 * @author 晋晨曦
 */
@Service
public class UrlValidationServiceImpl implements UrlValidationService {
    @Override
    public boolean isValidHttpOrHttpsUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            return "http".equals(url.getProtocol()) || "https".equals(url.getProtocol());
        } catch (MalformedURLException e) {
            return false;
        }
    }
}
