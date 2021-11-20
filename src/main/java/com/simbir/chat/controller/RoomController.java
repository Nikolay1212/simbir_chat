package com.simbir.chat.controller;

import com.simbir.chat.exception.AccountNotFoundException;
import com.simbir.chat.exception.RoomAlreadyExistException;
import com.simbir.chat.model.Room;
import com.simbir.chat.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping(value = "/create/default")
    public ResponseEntity createDefaultRoom(@RequestBody Room room) {
        try {
            roomService.createDefaultRoom(room);
            return ResponseEntity.ok().body("Room " + room.getTitle() + " successfully added!");
        } catch (RoomAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/create/private")
    public ResponseEntity createPrivateRoom(@RequestBody Room room) {
        roomService.createPrivateRoom(room);
        return ResponseEntity.ok().body("Private Room " + room.getTitle() + " successfully added!");
    }

    @GetMapping(value = "/add/{account_id}/{room_id}")
    public ResponseEntity addNewAccount(@PathVariable("account_id") Long accountId, @PathVariable("room_id") Long roomId) throws AccountNotFoundException, AccountNotFoundException {
        return ResponseEntity.ok(roomService.addNewAccount(roomId, accountId));
    }
}
