package com.wefox.challenge.service;

import com.wefox.challenge.model.dto.AccountDto;
import com.wefox.challenge.utils.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration(exclude= SecurityAutoConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    private AccountDto accountDto;

    @Before
    public void setUp(){
        accountDto = new AccountDto(null, "test", "test@test.com", 23, "address");
    }

    @Test
    public void addAccount(){
        Long id = accountService.createAccount(accountDto);
        assertTrue(id > 0);
    }

    @Test
    public void updateAccount(){
        accountDto.setId(accountService.createAccount(accountDto));
        accountDto.setName("update");

        assertEquals(accountService.updateAccount(accountDto).getId(), accountDto.getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateAccount_notFound(){
        accountService.updateAccount(accountDto);
    }

    @Test
    public void getAllAccounts(){
        accountService.createAccount(accountDto);

        List<AccountDto> accounts = accountService.findAllAccounts();

        assertFalse(accounts.isEmpty());
    }

    @Test
    public void findAccountById(){
        accountDto.setId(accountService.createAccount(accountDto));

        assertNotNull(accountService.findAccountById(accountDto.getId()));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findAccountById_notFound(){
        accountService.findAccountById(-1L);
    }

    @Test
    public void findAccountByEmail(){
        accountDto.setEmail("test@" + UUID.randomUUID() + ".com");
        accountDto.setId(accountService.createAccount(accountDto));

        assertNotNull(accountService.findAccountByEmail(accountDto.getEmail()));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findAccountByEmail_notFound(){
        accountService.findAccountByEmail("not fond");
    }

    @Test
    public void deleteAccount(){
        accountDto.setId(accountService.createAccount(accountDto));

        accountService.deleteAccountById(accountDto.getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteAccount_notFound(){
        accountService.deleteAccountById(-1L);
    }
}
