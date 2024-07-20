package com.web.garimaElectrical.config;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;

@Configuration
public class paypalConfig {
	@Value("${paypal.clientId}")
    private String clientId;

    @Value("${paypal.clientSecret}")
    private String clientSecret;

    @Value("${paypal.mode}")
    private String mode;
    
    @Bean
     Map<String, String> paypalSdkConfig() {
        Map<String, String> configMap = new HashMap<>();
        configMap.put("mode", "sandbox"); // or "live" for production
        return configMap;
    }

    @Bean
     OAuthTokenCredential getauthTokenCredential() {
        return new OAuthTokenCredential("AQHIkvyOtMMzzq4iioc4KIgnK858hA_ukyLNXQg4-u8TNdwMNO_P4B-QlhSTa_oUlW_96j2Cv3gSlE1m", "EHitttTSKJN4DSuyNK3LqNFTN4nHNJq6EaNnJrUT1PilK5GsG4NkHheB-JCzdBCuBbVMvm32RqtBkv44", paypalSdkConfig());
    }

    @Bean
     APIContext apiContext() throws PayPalRESTException {
    	String accessToken = getauthTokenCredential().getAccessToken();
        @SuppressWarnings("deprecation")
		APIContext context = new APIContext(accessToken);
        context.setConfigurationMap(paypalSdkConfig());
        return context;
    }
   
}
