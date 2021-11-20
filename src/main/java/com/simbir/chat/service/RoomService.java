package com.simbir.chat.service;

import com.simbir.chat.dto.AccountDto;
import com.simbir.chat.exception.AccountNotFoundException;
import com.simbir.chat.exception.RoomAlreadyExistException;
import com.simbir.chat.model.Account;
import com.simbir.chat.model.Message;
import com.simbir.chat.model.Room;
import com.simbir.chat.repository.AccountRepo;
import com.simbir.chat.repository.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private RoomRepo roomRepo;

    @Autowired
    private AccountRepo accountRepo;

    public void send(Message message) {
        Room room = message.getRoom();
        List<Message> messageList = room.getMessageList();
        messageList.add(message);
        room.setMessageList(messageList);
        roomRepo.save(room);
    }

    public void createDefaultRoom(Room room) throws RoomAlreadyExistException {
        if (roomRepo.findByAccessLevel().isEmpty()) {
            roomRepo.save(room);
        } else {
            throw new RoomAlreadyExistException("Room named " + room.getTitle() + " already exists");
        }
    }

    public void createPrivateRoom(Room room) {
        roomRepo.save(room);
    }

    public AccountDto addNewAccount(Long roomId, Long accountId) throws AccountNotFoundException {
        Optional<Account> accountOptional = accountRepo.findById(accountId);
        Optional<Room> roomOptional = roomRepo.findById(roomId);
        if (accountOptional.isPresent() && roomOptional.isPresent()) {
            Account account = accountOptional.get();
            List<Room> roomList = account.getRooms();
            roomList.add(roomOptional.get());
            account.setRooms(roomList);
            Room room = roomOptional.get();
            List<Account> accountList = room.getAccounts();
            accountList.add(account);
            room.setAccounts(accountList);
            roomRepo.save(room);
            return AccountDto.from(account);
        } else {
            throw new AccountNotFoundException("Account's id " + accountId + " not found");
        }
    }
}
