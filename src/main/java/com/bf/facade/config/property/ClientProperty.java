package com.bf.facade.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "client")
public class ClientProperty {
    private String urlBase;
    private String urlRut;
    private String urlName;
    private String urlNationality;
    private String urlBirthDate;

    public String getUrl(String url, String complement) {
        return urlBase.concat(complement);
    }
}

