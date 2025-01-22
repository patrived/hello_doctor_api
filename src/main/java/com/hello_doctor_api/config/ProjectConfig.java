package com.hello_doctor_api.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ProjectConfig {

    @Bean
    Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
           "cloud_name", "df6mwogpk",
           "api_key", "151953384384847",
           "api_secret", "VDsl8bkGdwEtkIXj7gHPDWsTLWI",
           "secure", true
        ));
    }


}
