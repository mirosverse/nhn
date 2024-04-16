package com.nhnacademy;

import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;

public class DB {
    private List<Request> reqArr;
    private List<Response> resArr;
    private List<Message> mesArr;

    public DB() {
        reqArr = new LinkedList<>();
        resArr = new LinkedList<>();
        mesArr = new LinkedList<>();
    }

    public void appendResDB(Response response) {
        resArr.add(response);
        save();
    }

    public void appendReqDB(Request request) {
        reqArr.add(request);
        save();
    }

    public void appendMesDB(Message message) {
        mesArr.add(message);
        save();
    }

    public void save() {
        JSONObject object = new JSONObject();
        JSONObject pairObject = new JSONObject();

        pairObject.put("요청", reqArr);
        pairObject.put("응답", resArr);

        object.put("접속 요청", pairObject);
        object.put("메시지 전송", mesArr);

        try {
            FileWriter file = new FileWriter(
                    "src/main/java/com/nhnacademy/multi_chat_db.json");
            file.write(object.toString());
            file.flush();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
