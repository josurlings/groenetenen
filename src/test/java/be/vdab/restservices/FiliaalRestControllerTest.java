package be.vdab.restservices;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.WebApplicationContext;

import be.vdab.entities.Filiaal;
import be.vdab.repositories.TestRepositoriesConfig;
import be.vdab.restclients.RestClientsConfig;
import be.vdab.services.FiliaalService;
import be.vdab.services.ServicesConfig;
import be.vdab.valueobjects.Adres;
import be.vdab.web.ControllersConfig;
import datasource.TestDataSourceConfig;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { TestDataSourceConfig.class, 
TestRepositoriesConfig.class, ServicesConfig.class,
ControllersConfig.class, RestControllersConfig.class, RestClientsConfig.class})
@WebAppConfiguration 
@Transactional 
public class FiliaalRestControllerTest
{
@Autowired
private WebApplicationContext context; 
@Autowired
private FiliaalService filiaalService; 
private Filiaal filiaal;
private MockMvc mvc; 

@Before
public void before() 
{
filiaal = new Filiaal("naam", true, BigDecimal.TEN, LocalDate.now(),
new Adres("straat", "huisNr", 1000, "gemeente"));
filiaalService.create(filiaal); 
mvc = MockMvcBuilders.webAppContextSetup(context).build(); 
}

@Test
public void filiaalLezenDatNietBestaat() throws Exception 
{
mvc.perform(get("/filialen/-666") 
.accept(MediaType.APPLICATION_XML)) 
.andExpect(status().isNotFound()); 
}

@Test
public void filiaalDatBestaatLezenInXMLFormaat() throws Exception
{
mvc.perform(get("/filialen/" + filiaal.getId()) 
.accept(MediaType.APPLICATION_XML))
.andExpect(status().isOk()) 
.andExpect(content()
.contentTypeCompatibleWith(MediaType.APPLICATION_XML)) 
.andExpect(xpath("/filiaalResource/filiaal/id") 
.string(String.valueOf(filiaal.getId()))); 
}

@Test
public void filiaalDatBestaatLezenInJSONFormaat() throws Exception 
{
mvc.perform(get("/filialen/" + filiaal.getId())
.accept(MediaType.APPLICATION_JSON))
.andExpect(status().isOk())
.andExpect(content()
.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
.andExpect(jsonPath("$.filiaal.id").value((int) filiaal.getId())); 
}

@Test
public void correctFiliaalToevoegen() throws Exception
{
String nieuwCorrectFiliaal = FileCopyUtils.copyToString( 
new InputStreamReader(context.getResource(
"classpath:nieuwCorrectFiliaal.xml").getInputStream()));
mvc.perform(post("/filialen") 
.contentType(MediaType.APPLICATION_XML) 
.content(nieuwCorrectFiliaal)) 
.andExpect(status().isCreated()); 
}

@Test
public void filiaalMetFoutToevoegen() throws Exception 
{
String nieuwFiliaalMetFout = FileCopyUtils.copyToString(
new InputStreamReader(context.getResource(
"classpath:nieuwFiliaalMetFout.xml").getInputStream()));
mvc.perform(post("/filialen")
.contentType(MediaType.APPLICATION_XML)
.content(nieuwFiliaalMetFout))
.andExpect(status().isBadRequest()); 
}
}