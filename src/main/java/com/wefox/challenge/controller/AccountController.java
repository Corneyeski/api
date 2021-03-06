package com.wefox.challenge.controller;

import com.wefox.challenge.model.dto.AccountDto;
import com.wefox.challenge.service.AccountService;
import com.wefox.challenge.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public Long createAccount(@RequestBody @Valid AccountDto account){
        return accountService.createAccount(account);
    }

    @PostMapping("/update")
    public AccountDto updateAccount(@RequestBody @Valid AccountDto account){
        return accountService.updateAccount(account);
    }

    @GetMapping("/all")
    public List<AccountDto> allAccounts(){
        return accountService.findAllAccounts();
    }

    @GetMapping("/{id}")
    public AccountDto findAccountById(@PathVariable long id){
        return accountService.findAccountById(id);
    }

    @GetMapping("/search/{email}")
    public AccountDto findAccountByEmail(@PathVariable String email){
        return accountService.findAccountByEmail(email);
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable long id){
        accountService.deleteAccountById(id);
    }
}
