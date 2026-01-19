package com.example.bankcards.service.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
@ConfigurationProperties(prefix = "security.pan")
public class PanSecurityProperties {
    
    private String hmacKey;
    private String aesKey;
}
