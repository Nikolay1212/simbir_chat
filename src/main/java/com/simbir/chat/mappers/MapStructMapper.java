package com.simbir.chat.mappers;

import com.simbir.chat.dto.AccountDto;
import com.simbir.chat.dto.MessageDto;
import com.simbir.chat.model.Account;
import com.simbir.chat.model.Message;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapStructMapper {
    AccountDto accountToAccountDto(Account account);
    MessageDto messageToMessageDto(Message message);
}
