package com.simbir.chat.controller;

import com.simbir.chat.exception.AccountAlreadyExistException;
import com.simbir.chat.exception.AccountNotFoundException;
import com.simbir.chat.model.Account;
import com.simbir.chat.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/ban/{account-id}")
    public ResponseEntity banAccount(@PathVariable("account-id") Long id) {
        try {
            return ResponseEntity.ok(accountService.banAccount(id));
        } catch (AccountNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/unban/{account-id}")
    public ResponseEntity unbanAccount(@PathVariable("account-id") Long id) {
        try {
            return ResponseEntity.ok(accountService.unbanAccount(id));
        } catch (AccountNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/moderator/assign/{account-id}")
    public ResponseEntity assignModerator(@PathVariable("account-id") Long id) {
        try {
            return ResponseEntity.ok(accountService.assignModerator(id));
        } catch (AccountNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/moderator/remove/{account-id}")
    public ResponseEntity removeModerator(@PathVariable("account-id") Long id) {
        try {
            return ResponseEntity.ok(accountService.removeModerator(id));
        } catch (AccountNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/registration")
    public ResponseEntity registration(@RequestBody Account account) {
        try {
            accountService.registration(account);
            return ResponseEntity.ok("Account " + account.getFirstName() + " was successfully added");
        } catch (AccountAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @GetMapping(value = "/authorization")
    public ResponseEntity authorization(@RequestBody Account account) {
        try {
            if (accountService.authorize(account)) {
                return ResponseEntity.ok().body("Successful authorization!");
            } else {
                return ResponseEntity.badRequest().body("Name or login is wrong");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error authorization");
        }
    }

    @GetMapping(value = "{account-id}")
    public ResponseEntity getOneUser(@PathVariable("account-id") Long id) {
        try {
            return ResponseEntity.ok(accountService.getOne(id));
        } catch (AccountNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(accountService.delete(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }
}
