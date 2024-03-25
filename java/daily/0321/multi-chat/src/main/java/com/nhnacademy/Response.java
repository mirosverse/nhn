package com.nhnacademy;

public class Response {
    private int id;
    private Type type;
    private String response;
    private String client_id;
    
    public Response(int id, Type type, String response, String client_id) {
        this.id = id;
        this.type = type;
        this.response = response;
        this.client_id = client_id;
    }

    public int getId() {
        return id;
    }
    public Type getType() {
        return type;
    }
    public String getResponse() {
        return response;
    }
    public String getClient_id() {
        return client_id;
    }


}
    

