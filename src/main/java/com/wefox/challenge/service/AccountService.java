package com.wefox.challenge.service;

import com.wefox.challenge.model.Account;
import com.wefox.challenge.model.dto.AccountDto;
import com.wefox.challenge.repository.AccountRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Long createAccount(AccountDto accountDto){

        Account account = new Account();
        BeanUtils.copyProperties(accountDto, account);

        accountRepository.save(account);

        return account.getId();
    }

    public void updateAccount(AccountDto accountDto){
        Account account = accountRepository.findById(accountDto.getId()).orElseThrow(IllegalArgumentException::new);
        BeanUtils.copyProperties(accountDto, account);

        accountRepository.save(account);
    }

    public List<AccountDto> findAllAccounts(){
        return accountRepository.findAll().stream().map(account -> {
            AccountDto dto = new AccountDto();
            BeanUtils.copyProperties(account, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    public AccountDto findAccountById(Long id){
        AccountDto dto = new AccountDto();
        BeanUtils.copyProperties(accountRepository.findById(id).orElseThrow(IllegalArgumentException::new), dto);
        return dto;
    }

    public AccountDto findAccountByEmail(String email){
        AccountDto dto = new AccountDto();
        BeanUtils.copyProperties(accountRepository.findAccountByEmail(email).orElseThrow(IllegalArgumentException::new), dto);
        return dto;
    }

    public void deleteAccountById(Long id){
        Account account = accountRepository.findById(id).orElseThrow(IllegalAccessError::new);
        accountRepository.delete(account);
    }
}
