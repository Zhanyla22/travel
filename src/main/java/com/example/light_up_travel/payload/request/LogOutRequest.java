package com.example.light_up_travel.payload.request;

import javax.validation.constraints.NotNull;

public class LogOutRequest {
    @NotNull(message = "User id can't be null")
    private Long userId;

    public Long getUserId() {
        return this.userId;
    }
}
