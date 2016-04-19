import Services.SpringConfig;
import Services.TestContentProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import libraryprojectmodel.Author;
import libraryprojectmodel.Book;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by aldm on 18.04.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@WebAppConfiguration
@Transactional
public class AppControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private String successJsonTrue;

    private String successJsonFalse;

    private String jsonBook;

    private String jsonBookResult;

    @Resource
    private TestContentProducer testContentProducer;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        this.successJsonTrue = "{\"success\":true}";
        this.successJsonFalse = "{\"success\":false}";
        Map<String,String> books = new HashMap<>();
        books.put("myBook","Unknown");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        this.jsonBook = ow.writeValueAsString(books);
        this.jsonBookResult = "[{\"id\":1,\"title\":\"myBook\",\"releaseYear\":1461069936812,\"authors\":[{\"id\":1,\"name\":\"Unknown\",\"books\":null}]}]";
    }

    @Test
    public void bookNotExists() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/existbook?title=123&author=456")).andExpect(status().isOk()).andReturn();
        assertEquals(result.getResponse().getContentAsString(),successJsonFalse);
    }

    @Test
    public void bookExists() throws Exception {
        Author author = testContentProducer.getAuthor();
        Book book = testContentProducer.getBook(author);
        MvcResult result = this.mockMvc.perform(get("/existbook?title=myBook&author=Unknown")).andExpect(status().isOk()).andReturn();
        assertEquals(result.getResponse().getContentAsString(),successJsonTrue);
    }

    @Test
    public void getBooks() throws Exception {
        Author author = testContentProducer.getAuthor();
        Book book = testContentProducer.getBook(author);
        MvcResult result = this.mockMvc.perform(post("/getbooks").contentType(MediaType.APPLICATION_JSON).content(jsonBook)).andDo(print()).andExpect(status().isOk()).andReturn();
        assertEquals(result.getResponse().getContentAsString(),jsonBookResult);
    }

    // incorrect REDO.
    @Test
    public void registerUser() throws Exception {
        /*this.mockServer.expect(requestTo("/register?email=dmitryaleshkevich@gmail.com&login=aldm&password=fhvfutljy")).andRespond(withSuccess(this.successJsonTrue, MediaType.APPLICATION_JSON));
        String isRegistered = this.restTemplate.getForObject("/register?email=dmitryaleshkevich@gmail.com&login=aldm&password=fhvfutljy",String.class);
        assertEquals(isRegistered,successJsonTrue);
        this.mockServer.verify();*/
    }

    @Test
    public void rentBooks() throws Exception {

    }

    @Test
    public void pushBooks() throws Exception {

    }

}