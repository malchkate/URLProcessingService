package com.segmento.demoService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DemoServiceApplicationTests {
	@Autowired
	private MockMvc mockMvc;
    @Autowired
    private UrlContainer urlContainer;

	@Test
	public void addCorrectUrlGetRequestShouldReturnStatusOk() throws Exception {
        mockMvc.perform(get("/add/foo.livejournal.com/post234.html?a=b"))
                .andExpect(status().isOk());
	}

    @Test
    public void addIncorrectUrlGetRequestShouldReturnTryAgainString() throws Exception {
        mockMvc.perform(get("/add/m/post234.html?a=b"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void requestTopNDomainsIfNIsTooBigShouldReturnStatusOk() throws Exception {
        mockMvc.perform(get("/top/10"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("[")));
    }

    @Test
    public void requestTopNDomainsShouldReturnCorrectList() throws Exception {
	    urlContainer.addToContainer("livejournal.com");
        mockMvc.perform(get("/top/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("[\"livejournal.com\"]"));
    }
}
