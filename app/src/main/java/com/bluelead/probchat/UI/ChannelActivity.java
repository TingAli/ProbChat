package com.bluelead.probchat.UI;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.bluelead.probchat.R;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class ChannelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
    }

    public class WebSocketAsync extends AsyncTask<Void, Void, Void> {
        private WebSocketClient mWebSocketClient;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            connectWebSocket();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //Should try to send message here. Use interface for callback if it doesn't work.
        }

        private void connectWebSocket() {
            URI uri;
            try {
                uri = new URI("ws://b116.ml:8080");
            } catch (URISyntaxException e) {
                e.printStackTrace();
                return;
            }

            mWebSocketClient = new WebSocketClient(uri) {

                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    System.out.println("Websocket Opened");
                    mWebSocketClient.send("Hello from " + Build.MANUFACTURER + " " + Build.MODEL);
                }

                @Override
                public void onMessage(String s) {
                    final String message = s;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView textView = (TextView)findViewById(R.id.response_tv);
                            textView.setText(textView.getText() + "\n" + message);
                        }
                    });
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    System.out.println("Websocket Closed " + s);
                }

                @Override
                public void onError(Exception e) {
                    System.out.println("Websocket Error " + e.getMessage());
                }
            };

            mWebSocketClient.connect();
        }

        private void sendMessage(String message) {
            mWebSocketClient.send(message);
        }
    }


}
