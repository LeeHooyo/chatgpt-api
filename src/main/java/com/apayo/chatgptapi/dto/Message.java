package com.apayo.chatgptapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private String role; // 엑세스 할 수 있는 사람
    private String content; // prompt
}
