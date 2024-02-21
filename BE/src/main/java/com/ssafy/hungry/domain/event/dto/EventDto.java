package com.ssafy.hungry.domain.event.dto;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Data
@ToString
public class EventDto {
    private String fromUserEmail;
    private String toUserEmail;
    private String eventCategory;
    private String eventAction;
    private String message;
}
