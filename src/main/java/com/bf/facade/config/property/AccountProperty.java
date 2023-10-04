package com.bf.facade.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "account")
public class AccountProperty {
    private String urlBase;
    private String urlAccountNumber;
    private String urlBalance;

    public String getUrl(String url, String complement) {
        return urlBase.concat(complement);
    }

}
