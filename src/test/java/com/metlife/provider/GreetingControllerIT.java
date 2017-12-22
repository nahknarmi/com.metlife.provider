package com.metlife.provider;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
public class GreetingControllerIT {
    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/");
    }

    @Test
    public void greeting() throws Exception {
        ResponseEntity<String> response = template.getForEntity(base.toString() + "greeting", String.class);
        assertThat(response.getBody(), equalTo("{\"id\":1,\"content\":\"Hello, World!\"}"));
    }

    @Test
    public void toggle() throws Exception {
        ResponseEntity<String> response = template.getForEntity(base.toString() + "toggle", String.class);
        assertThat(response.getBody(), equalTo("{\"toggle\":\"true\",\"name\":\"dev\"}"));
    }
}
