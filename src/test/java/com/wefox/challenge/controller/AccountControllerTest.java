package com.wefox.challenge.controller;

import com.wefox.challenge.model.dto.AccountDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration(exclude= SecurityAutoConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AccountControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private AccountDto accountDto;

    @Before
    public void setUp(){
        accountDto = new AccountDto(null, "test", "test@test.com", 23, "address");
    }

    @Test
    public void createAccount_ok(){

        ResponseEntity<Long> response = testRestTemplate.exchange("/account/create",
                HttpMethod.POST, new HttpEntity<>(accountDto), Long.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() > 1);
    }

    @Test
    public void createAccount_badRequest_wrongEmail(){

        accountDto.setEmail("testError");

        ResponseEntity<Void> response = testRestTemplate.exchange("/account/create",
                HttpMethod.POST, new HttpEntity<>(accountDto), Void.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void updateAccount_ok(){

        assertEquals(accountDto.getName(), "test");

        Long id = testRestTemplate.exchange("/account/create",
                HttpMethod.POST, new HttpEntity<>(accountDto), Long.class).getBody();

        accountDto.setId(id);
        accountDto.setName("update");

        ResponseEntity<AccountDto> response = testRestTemplate.exchange("/account/update",
                HttpMethod.POST, new HttpEntity<>(accountDto), AccountDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody().getId(), id);
        assertTrue(!response.getBody().getName().equalsIgnoreCase("test"));
    }

    @Test
    public void updateAccount_notFound(){
        ResponseEntity<Void> response = testRestTemplate.exchange("/account/update",
                HttpMethod.POST, new HttpEntity<>(accountDto), Void.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getAllAccounts_ok(){
        testRestTemplate.exchange("/account/create",
                HttpMethod.POST, new HttpEntity<>(accountDto), Long.class);


        ResponseEntity<List<AccountDto>> response = testRestTemplate.exchange("/account/all",
                HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<AccountDto>>() {
                });

        assertFalse(response.getBody().isEmpty());
    }

    @Test
    public void getAccountById_ok(){
        Long testAccount = testRestTemplate.exchange("/account/create",
                HttpMethod.POST, new HttpEntity<>(accountDto), Long.class).getBody();


        ResponseEntity<AccountDto> response = testRestTemplate.exchange("/account/" + testAccount,
                HttpMethod.GET, HttpEntity.EMPTY, AccountDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testAccount, response.getBody().getId());
    }

    @Test
    public void getAccountById_notFound(){
        ResponseEntity<AccountDto> response = testRestTemplate.exchange("/account/" + -1L,
                HttpMethod.GET, HttpEntity.EMPTY, AccountDto.class);


        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getAccountByEmail_ok(){

        accountDto.setEmail("test@" + UUID.randomUUID() + ".com");

        testRestTemplate.exchange("/account/create",
                HttpMethod.POST, new HttpEntity<>(accountDto), Long.class);


        ResponseEntity<AccountDto> response = testRestTemplate.exchange("/account/search/" + accountDto.getEmail(),
                HttpMethod.GET, HttpEntity.EMPTY, AccountDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accountDto.getEmail(), response.getBody().getEmail());
    }

    @Test
    public void getAccountByEmail_notFound(){
        ResponseEntity<AccountDto> response = testRestTemplate.exchange("/account/search/testEmail",
                HttpMethod.GET, HttpEntity.EMPTY, AccountDto.class);


        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }



    @Test
    public void deleteAccount_ok(){
        Long id = testRestTemplate.exchange("/account/create",
                HttpMethod.POST, new HttpEntity<>(accountDto), Long.class).getBody();


        ResponseEntity<Void> response = testRestTemplate.exchange("/account/" + id,
                HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void deleteAccount_notFound(){
        ResponseEntity<AccountDto> response = testRestTemplate.exchange("/account/-1",
                HttpMethod.DELETE, HttpEntity.EMPTY, AccountDto.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
