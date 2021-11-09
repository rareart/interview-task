package ru.sberbank.interview.task.integration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.sberbank.interview.task.Application;
import ru.sberbank.interview.task.dao.repository.EntityRepository;
import ru.sberbank.interview.task.utils.preloader.Preload;
import ru.sberbank.interview.task.utils.preloader.PreloadWithRandomData;

import java.text.ParseException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@ActiveProfiles("local")
public class ServiceControllerTest {

    private final MockMvc mockMvc;
    private static final AtomicBoolean initCheck = new AtomicBoolean(false);

    @Autowired
    public ServiceControllerTest(MockMvc mockMvc,
                                 EntityRepository entityRepository) {
        this.mockMvc = mockMvc;
        Preload preload = new PreloadWithRandomData(entityRepository);
        try {
            //protection of the loader calls
            //from multiple instances of a test-class object:
            if (ServiceControllerTest.initCheck
                    .compareAndSet(false, true)){
                preload.execute();
            }
        } catch (ParseException e) {
            throw new RuntimeException(
                    "ServiceControllerTest: embedded preloader fatal error", e);
        }
    }

    @Test
    public void getEntitiesWithHeaderTest() throws Exception {
        String ids = "1,5,7";
        mockMvc.perform(get("/interview-task")
                .header("Entities-List", ids))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].code").value(111))
                .andExpect(jsonPath("$[1].code").value(444))
                .andExpect(jsonPath("$[2].code").value(333));
    }

    @Test
    public void getEntitiesWithHeaderExceptionTest() throws Exception {
        String ids = "1,5,7,500";
        mockMvc.perform(get("/interview-task")
                        .header("Entities-List", ids))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error")
                        .value("Some ids are missing in the database"))
                .andExpect(jsonPath("$.message").value("[500]"));
    }

    @Test
    public void getEntitiesWithQueryTest() throws Exception {
        String code = "222";
        String sysname = "green";
        mockMvc.perform(get("/interview-task")
                        .param("code", code)
                        .param("sysname", sysname))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].entityId").value(2))
                .andExpect(jsonPath("$[0].description")
                        .value("some description2"));
    }

    @Test
    public void getAllEntitiesWithoutQueryTest() throws Exception {
        mockMvc.perform(get("/interview-task"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(8)));
    }

    @Test
    public void getEntitiesByPath() throws Exception {
        mockMvc.perform(get("/interview-task/red"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.items.*", hasSize(3)))
                .andExpect(jsonPath("$.unread").value(2));
    }

    @Test
    public void getEntitiesByPathWithoutUnread() throws Exception {
        mockMvc.perform(get("/interview-task/orange"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.items.*", hasSize(0)))
                .andExpect(jsonPath("$.unread").value(0));
    }

    @Test
    public void getEntitiesByWrongPath() throws Exception {
        mockMvc.perform(get("/interview-task/wrongpath"))
                .andExpect(status().isNotFound())
                .andExpect(content().bytes(new byte[0]));
    }
}
