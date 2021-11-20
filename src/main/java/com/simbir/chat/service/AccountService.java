package com.simbir.chat.service;

import com.simbir.chat.dto.AccountDto;
import com.simbir.chat.exception.AccountAlreadyExistException;
import com.simbir.chat.exception.AccountNotFoundException;
import com.simbir.chat.model.*;
import com.simbir.chat.repository.AccountRepo;
import com.simbir.chat.repository.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private RoomRepo roomRepo;

    public Account banAccount(Long id) throws AccountNotFoundException {
        Optional<Account> accountOptional = accountRepo.findById(id);
        if (accountOptional.isEmpty()) {
            throw new AccountNotFoundException("Account id = " + id + " not found");
        }
        Account currentAccount = accountOptional.get();
        currentAccount.setState(State.BANNED);
        accountRepo.save(currentAccount);
        return currentAccount;
    }

    public Account unbanAccount(Long id) throws AccountNotFoundException {
        Optional<Account> accountOptional = accountRepo.findById(id);
        if (accountOptional.isEmpty()) {
            throw new AccountNotFoundException("Account id = " + id + " not found");
        }
        Account currentAccount = accountOptional.get();
        currentAccount.setState(State.CONFIRMED);
        accountRepo.save(currentAccount);
        return currentAccount;
    }

    public Account assignModerator(Long id) throws AccountNotFoundException {
        Optional<Account> accountOptional = accountRepo.findById(id);
        if (accountOptional.isEmpty()) {
            throw new AccountNotFoundException("Account id = " + id + " not found");
        }
        Account currentAccount = accountOptional.get();
        currentAccount.setRole(Role.MODERATOR);
        accountRepo.save(currentAccount);
        return currentAccount;
    }

    public Account removeModerator(Long id) throws AccountNotFoundException {
        Optional<Account> accountOptional = accountRepo.findById(id);
        if (accountOptional.isEmpty()) {
            throw new AccountNotFoundException("Account id = " + id + " not found");
        }
        Account currentAccount = accountOptional.get();
        currentAccount.setRole(Role.USER);
        accountRepo.save(currentAccount);
        return currentAccount;
    }

    public Account registration(Account account) throws AccountAlreadyExistException {
        if (accountRepo.findByFirstName(account.getFirstName()) != null) {
            throw new AccountAlreadyExistException("Name " + account.getFirstName() + " already exists!");
        }
        List<Room> defaultRoomList = roomRepo.findByAccessLevel();
        account.setRooms(defaultRoomList);
        return accountRepo.save(account);
    }

    public AccountDto getOne(Long id) throws AccountNotFoundException {
        Optional<Account> accountEntityOptional = accountRepo.findById(id);
        if (accountEntityOptional.isEmpty()) {
            throw new AccountNotFoundException("User not found");
        }
        //return mapStructMapper.accountToAccountDto(accountEntityOptional.get());
        return AccountDto.from(accountEntityOptional.get());
    }

    public boolean authorize(Account account) {
        Account accountRepoByFirstName = accountRepo.findByFirstName(account.getFirstName());
        if (accountRepoByFirstName != null && accountRepoByFirstName.getHashPassword().equals(account.getHashPassword())) {
            accountRepoByFirstName.setRooms(roomRepo.findByAccessLevel());
            accountRepo.save(accountRepoByFirstName);
            return true;
        }
        return false;
    }

    public Long delete(Long id) {
        accountRepo.deleteById(id);
        return id;
    }

    public void addNewMessage(Message message, Long id) throws AccountNotFoundException {
        Account account = accountRepo.findById(id).get();
        List<Message> messageList = account.getMessageList();
        messageList.add(message);
        account.setMessageList(messageList);
        accountRepo.save(account);
    }
}
