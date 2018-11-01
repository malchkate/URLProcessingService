package com.segmento.demoService.builder;

import org.springframework.stereotype.Component;

@Component
public class SecondLevelDomainBuilder {
    public String build(String urlString){
        if (urlString.matches("(http(s)?://.)(www\\.)?[-a-zA-Z0-9@:%._+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_+.~#?&/=]*)")) {
            urlString = urlString.split("/")[2];
        } else{
            urlString= urlString.split("/")[0];
        }
        String[] arrayOfDomainsParts = urlString.split("\\.");
        int length = arrayOfDomainsParts.length;
        return arrayOfDomainsParts[length - 2] + "." + arrayOfDomainsParts[length - 1];
    }
}
