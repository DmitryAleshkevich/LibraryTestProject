import Config.SpringConfig;
import Utils.TestContentProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import Model.Author;
import Model.Book;
import Model.Card;
import Model.Library;
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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

    private static final String successJsonTrue = "{\"success\":true}";

    private static final String successJsonFalse = "{\"success\":false}";

    private String jsonBook;

    private static final String jsonBookResult = "[{\"title\":\"myBook\",\"authors\":\"Unknown \",\"id\":4,\"releaseYear\":1461069936812}]";

    private static final String jsonRegistration = "{\"email\":\"dmitryaleshkevich@gmail.com\",\"login\":\"aldm\",\"password\":\"fhvfutljy\"}";

    private static final String jsonRent;

    private static final String newjsonBookResult = "[{\"id\":1,\"title\":\"myBook\",\"releaseYear\":\"2016-06-14T21:00:00.000Z\",\"authors\":\"Unknown\"}]";

    //{"books":[{"Id":"3","Title":"Hyperion","Authors":"Simmons","Release Date":"1966-06-08"}],"date":"2016-06-14T21:00:00.000Z","login":"aldm","password":"123"}

    static {
        jsonRent = "{\"books\":" + newjsonBookResult + ",\"date\":\"2016-06-14T21:00:00.000Z\",\"login\":\"aldm\",\"password\":\"fhvfutljy\"}";
    }

    @Resource
    private TestContentProducer testContentProducer;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        Map<String,String> books = new HashMap<>();
        books.put("myBook","Unknown");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        this.jsonBook = ow.writeValueAsString(books);
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
        MvcResult result = this.mockMvc.perform(post("/getbooks").contentType(MediaType.APPLICATION_JSON).content(this.jsonBook)).andDo(print()).andExpect(status().isOk()).andReturn();
        assertEquals(result.getResponse().getContentAsString(),jsonBookResult);
    }

    @Test
    public void registerUser() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(jsonRegistration)).andDo(print()).andExpect(status().isOk()).andReturn();
        assertEquals(result.getResponse().getContentAsString(),successJsonTrue);
    }

    @Test
    public void rentBooks() throws Exception {
        Author author = testContentProducer.getAuthor();
        Book book = testContentProducer.getBook(author);
        Set<Book> books = new HashSet<>();
        books.add(book);
        Card card = testContentProducer.getNewCard();
        Library library = testContentProducer.getLibrary(book);
        MvcResult result = this.mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(jsonRegistration)).andDo(print()).andExpect(status().isOk()).andReturn();
        MvcResult res = this.mockMvc.perform(post("/rentbooks").contentType(MediaType.APPLICATION_JSON).content(jsonRent)).andDo(print()).andExpect(status().isOk()).andReturn();
        assertEquals(res.getResponse().getContentAsString(),successJsonTrue);
        testContentProducer.DeleteAll(author, book, card, library);
    }

}