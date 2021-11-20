package com.simbir.chat.dto;

import com.simbir.chat.model.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDto {

    private Long id;
    private String content;
    private Long sender_id;
    private Long room_id;

    public static MessageDto from(Message message) {
        return MessageDto.builder()
                .id(message.getId())
                .content(message.getContent())
                .sender_id(message.getAccount().getId())
                .room_id(message.getRoom().getId())
                .build();
    }
}
