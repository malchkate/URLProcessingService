package com.segmento.demoService.controller;

import com.segmento.demoService.logic.AddUrlOperation;
import com.segmento.demoService.logic.GetTopNDomainsOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UrlRestController {
    private final AddUrlOperation addUrlOperation;
    private final GetTopNDomainsOperation getTopNDomainsOperation;

    @RequestMapping(method = RequestMethod.GET, value = "/add/**")
    public ResponseEntity<String> addUrl(HttpServletRequest request){
        String uri = request.getRequestURI();
        if(addUrlOperation.addUrl(uri)){
            return new ResponseEntity<>("URL was added", HttpStatus.OK);
        } else{
            return new ResponseEntity<>("URL is NOT valid, please try again.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/top/{n}")
    public List<String> getNMostFrequentSecondLevelDomains(@PathVariable int n){
        return getTopNDomainsOperation.getNMostFrequentSecondLevelDomainsFromContainer(n);
    }
}
