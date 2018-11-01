package com.segmento.demoService.logic;

import com.segmento.demoService.UrlContainer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GetTopNDomainsOperation {
    private final UrlContainer urlContainer;

    public List<String> getNMostFrequentSecondLevelDomainsFromContainer(int n) {
        if (n <= 0){
            return new ArrayList<>();
        }
        return urlContainer.getDataFromContainer(n);
    }
}
