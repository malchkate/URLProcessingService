package com.segmento.demoService.logic;

import com.segmento.demoService.UrlContainer;
import com.segmento.demoService.builder.SecondLevelDomainBuilder;
import com.segmento.demoService.utils.UrlUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AddUrlOperation {
    private final SecondLevelDomainBuilder secondLevelDomainBuilder;
    private final UrlContainer urlContainer;
    private final UrlUtils urlUtils;

    public boolean addUrl(String stringUri) {
        Optional<String> optionalStringURL = urlUtils.validateURL(stringUri);
        if (optionalStringURL.isPresent()) {
            String secondLevelDomain = secondLevelDomainBuilder.build(optionalStringURL.get());
            urlContainer.addToContainer(secondLevelDomain);
            return true;
        } else{
            return false;
        }
    }
}
