package com.nhnacademy;

public class Message {
    private int id;
    private Type type;
    private String target_id;
    private String message;

    public Message(int id, Type type, String target_id, String message) {
        this.id = id;
        this.type = type;
        this.target_id = target_id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public String getTarget_id() {
        return target_id;
    }

    public String getMessage() {
        return message;
    }

}
