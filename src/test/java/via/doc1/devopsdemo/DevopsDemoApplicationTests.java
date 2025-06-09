package via.doc1.devopsdemo;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class DevopsDemoApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() {}

    @Test
    void testMainMethod() {
        Mockito.mockStatic(SpringApplication.class);

        String[] args = {};
        DevopsDemoApplication.main(args);

        Mockito.verify(SpringApplication.class, Mockito.times(1));
        SpringApplication.run(DevopsDemoApplication.class, args);
    }

    @Test

    public void testIndexEndpoint() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("<body><h1>DevOps and Cloud</h1> "
                        + "<h2>Dockerizing Spring Boot Backend Application.</h2>"
                        + "<p>With Docker, we can containerize SEP4 back-end and front-end applications.</p></body>"));
    }

}
