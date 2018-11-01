package com.segmento.demoService.utils;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UrlUtils {

    public Optional<String> validateURL(String uriString){
        String urlString = uriString.substring(5);
        if(urlString.matches("(http(s)?://.)?(www\\.)?[-a-zA-Z0-9@:%._+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_+.~#?&/=]*)")){
            return Optional.of(urlString);
        }
        return Optional.empty();
    }
}
