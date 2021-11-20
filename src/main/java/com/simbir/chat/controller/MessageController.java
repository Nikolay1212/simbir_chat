package com.simbir.chat.controller;

import com.simbir.chat.dto.MessageDto;
import com.simbir.chat.service.AccountService;
import com.simbir.chat.service.MessageService;
import com.simbir.chat.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping(value = "/send/{message_id}")
    public ResponseEntity send(@PathVariable("message_id") Long messageId) {
        return ResponseEntity.ok(messageService.send(messageId));
    }

    @GetMapping(value = "/receive")
    public ResponseEntity receive(@RequestBody MessageDto messageDto) {
        return ResponseEntity.ok(messageService.receive(messageDto));
    }

    @DeleteMapping(value = "/delete/{message_id}")
    public ResponseEntity delete(@PathVariable("message_id") Long messageId) {
        messageService.delete(messageId);
        return ResponseEntity.ok("Message deleted");
    }
}
