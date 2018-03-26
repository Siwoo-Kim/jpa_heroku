package com.siwoo.application.domain.views;

import lombok.Getter;
import lombok.ToString;

import static com.siwoo.application.domain.User.Status;

@Getter @ToString
public class UserSummary {

    private String nickname;
    private String email;
    private int point;
    private Status status;

    public UserSummary(String nickname, String email, int point, Status status) {
        this.nickname = nickname;
        this.email = email;
        this.point = point;
        this.status = status;
    }
}
