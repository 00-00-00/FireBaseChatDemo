package com.ground0.model;

public class MessageThread {

    private Long id;

    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    private MessageThread() {
    }

    public MessageThread(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
