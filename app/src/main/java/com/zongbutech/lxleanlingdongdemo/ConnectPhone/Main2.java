package com.zongbutech.lxleanlingdongdemo.ConnectPhone;

import android.os.Looper;

import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.koushikdutta.async.http.server.HttpServerRequestCallback;
import com.zongbutech.lxleanlingdongdemo.InPutInfoAndOutofInfo.TestOutPutInfo;

/**
 * Created by seanzhou on 3/14/17.
 */

public class Main2 {
    static Looper looper;

    public static void main(String[] args) {
        AsyncHttpServer httpServer = new AsyncHttpServer() {
            @Override
            protected boolean onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
                return super.onRequest(request, response);
            }
        };

        Looper.prepare();
        looper = Looper.myLooper();
        System.out.println(">>> DroidCast main entry");
        if (TestOutPutInfo.instance != null && TestOutPutInfo.instance.getEditString() != null
                && TestOutPutInfo.instance.getEditString().length() > 0) {
            System.out.println(TestOutPutInfo.instance.getEditString());
        }

        AsyncServer server = new AsyncServer();
        httpServer.get("/lx.jpg", new AnyRequestCallback());
        httpServer.listen(server, 53516);

        looper.loop();
    }

    static class AnyRequestCallback implements HttpServerRequestCallback {
        @Override
        public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
            try {
                if(TestOutPutInfo.instance ==null){
                    response.send("TestOutPutInfo.instance null");
                }else{
                    if(TestOutPutInfo.instance.getEditString() ==null){
                        response.send("TestOutPutInfo.instance.getEditString()null");
                    }else{
                        response.send(TestOutPutInfo.instance.getEditString());
                    }
                }

//                if (TestOutPutInfo.instance != null && TestOutPutInfo.instance.getEditString() != null
//                        && TestOutPutInfo.instance.getEditString().length() > 0) {
//                    response.send(TestOutPutInfo.instance.getEditString());
//                }else{
//                    response.send("Lx");
//                }

            } catch (Exception e) {
                response.code(500);
                response.send(e.toString());
            }
        }
    }
}
