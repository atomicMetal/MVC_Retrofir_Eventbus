package com.metalsack.retrobus.events;

public class ApiErrorWithMessageEvent {

    private final String requestTag;
    private final String resultMsgUser;

    public ApiErrorWithMessageEvent(String requestTag, String resultMsgUser) {
        this.requestTag = requestTag;
        this.resultMsgUser = resultMsgUser;
    }

    public String getRequestTag() {
        return requestTag;
    }

    public String getResultMsgUser() {
        return resultMsgUser;
    }
}
