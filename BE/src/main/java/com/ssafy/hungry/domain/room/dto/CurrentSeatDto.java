package com.ssafy.hungry.domain.room.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrentSeatDto implements Comparable<CurrentSeatDto> {

    private int team;
    private int seatNumber;
    private int userId;
    private String nickname;
    private String profileImgUrl;
    private int state;

    @JsonProperty("ready")
    private boolean isReady;

    @Override
    public int compareTo(CurrentSeatDto o) {
        return Integer.compare(this.seatNumber, o.seatNumber);
    }
}
