package com.bluelead.probchat.UI;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.bluelead.probchat.DataConverters.JSONParser;
import com.bluelead.probchat.Models.Message;
import com.bluelead.probchat.Models.Type;
import com.bluelead.probchat.R;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class ChannelActivity extends AppCompatActivity {
    private Type mTypeSelected;
    private final Context CONTEXT = ChannelActivity.this;
    private boolean mConnectionOpen;
    private String mServerResponse;
    private ArrayList<Message> mLatestClientMessages;
    private ArrayList<Message> mLatestServerMessages;
    private WebSocketAsync webSocketAsync;
    private LinearLayout mLoadingLinearLayout, mContentLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);

        mLoadingLinearLayout = (LinearLayout) findViewById(R.id.channel_loading_ly);
        mContentLinearLayout = (LinearLayout) findViewById(R.id.channel_content_ly);

        mTypeSelected = getIntent().getParcelableExtra("PAR_KEY");
        System.out.println(mTypeSelected);

        webSocketAsync = new WebSocketAsync();
        webSocketAsync.execute();

        webSocketAsync.sendMessage(JSONParser.typeToJson(mTypeSelected));
        System.out.println(JSONParser.typeToJson(mTypeSelected));

    }

    private void sendMsg(Message message) {
        webSocketAsync.sendMessage(JSONParser.messageToJson(message));
        mLatestClientMessages.add(message);
    }

    private Message getLatestServerMessage() {
        return mLatestServerMessages.get(mLatestServerMessages.size() - 1);
    }

    private Message getLatestClientMessage() {
        return mLatestClientMessages.get(mLatestClientMessages.size() - 1);
    }

    private void showContent() {
        mLoadingLinearLayout.setVisibility(View.INVISIBLE);
        mContentLinearLayout.setVisibility(View.VISIBLE);
    }

    private void showLoading() {
        mLoadingLinearLayout.setVisibility(View.VISIBLE);
        mContentLinearLayout.setVisibility(View.INVISIBLE);
    }

    public class WebSocketAsync extends AsyncTask<Void, Void, Void> {
        private WebSocketClient mWebSocketClient;
        @Override
        protected Void doInBackground(Void... params) {
            connectWebSocket();

            return null;
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

                    mConnectionOpen = true;

                }

                @Override
                public void onMessage(String s) {
                    final String message = s;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mServerResponse = message;
                            JSONObject object = null;
                            try {
                                object = (JSONObject) new JSONTokener(message).nextValue();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            System.out.println(object);

                            try {
                                if(object.getString("action").equals("message")) {
                                    mLatestServerMessages = JSONParser.messagesFromJson(mServerResponse);
                                    // display the message
                                    System.out.println(getLatestServerMessage().getMessage());
                                }
                                else if(object.getString("action").equals("match")) {
                                    showContent();
                                }
                                else {
                                    showLoading();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    System.out.println("Websocket Closed " + s);

                    mConnectionOpen = false;
                }

                @Override
                public void onError(Exception e) {
                    System.out.println("Websocket Error " + e.getMessage());

                    mConnectionOpen = false;
                }
            };

            mWebSocketClient.connect();
        }

        private void sendMessage(final String message) {
            if(mConnectionOpen) {
                mWebSocketClient.send(message);
            }
            else {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sendMessage(message);
                    }
                }, 100);
            }
        }
    }


}
