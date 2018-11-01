package com.segmento.demoService;

import com.segmento.demoService.builder.SecondLevelDomainBuilder;
import com.segmento.demoService.utils.UrlUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UtilsTests {
    @Autowired
    private UrlContainer urlContainer;
    @Autowired
    private UrlUtils urlUtils;
    @Autowired
    private SecondLevelDomainBuilder secondLevelDomainBuilder;

    @Test
    public void validatorTestCorrectValidation(){
        Optional<String> optionalUrl = urlUtils.validateURL("/add/https://foo.livejournal.com/post234.html");
        assertTrue(optionalUrl.isPresent());
        assertEquals("https://foo.livejournal.com/post234.html", optionalUrl.get());
    }

    @Test
    public void validatorTestFailedValidation(){
        Optional<String> optionalUrl = urlUtils.validateURL("/add/htps://foo.livejournal.com/post234.html");
        assertFalse(optionalUrl.isPresent());
    }

    @Test
    public void builderOfSecondLevelDomainIsWorkingCorrectly() {
        String actualDomain = secondLevelDomainBuilder.build("https://foo.livejournal.com/post234.html");
        assertEquals("livejournal.com", actualDomain);
    }

    @Test
    public void addToContainerMethodGetTopNDomainsShouldReturnNonemptyList(){
        urlContainer.addToContainer("livejournal.com");
        assertFalse(urlContainer.getDataFromContainer(1).isEmpty());
    }
}
