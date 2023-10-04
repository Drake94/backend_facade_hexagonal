package com.bf.facade.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "card")
public class CardProperty {
    private String urlBase;
    private String urlCardNumber;
    private String urlType;
    private String urlDescriptionType;
    private String urlStatus;
    private String urlDescriptionStatus;

    public String getUrl(String url, String complement) {
        return urlBase.concat(complement);
    }
}
