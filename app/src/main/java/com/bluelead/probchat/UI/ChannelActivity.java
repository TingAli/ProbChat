package com.bluelead.probchat.UI;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bluelead.probchat.Adapters.ChatAdapter;
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

public class ChannelActivity extends AppCompatActivity implements ChatAdapter.ListItemClickListener {
    private Type mTypeSelected;
    private final Context CONTEXT = ChannelActivity.this;
    private boolean mConnectionOpen;
    private String mServerResponse;
    private ArrayList<Message> mAllMessages;
    private ArrayList<Message> mMessagesReceived;
    private WebSocketAsync webSocketAsync;
    private LinearLayout mLoadingLinearLayout, mContentLinearLayout;
    private RecyclerView mChatRoomRecyclerView;
    private EditText mMessageToSend;
    private Button mSendMessageButton;
    private LinearLayoutManager mLayoutManager;
    private ChatAdapter mChatAdapter;
    private int numberOfItems = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);

        mLoadingLinearLayout = (LinearLayout) findViewById(R.id.channel_loading_ly);
        mContentLinearLayout = (LinearLayout) findViewById(R.id.channel_content_ly);
        mChatRoomRecyclerView = (RecyclerView) findViewById(R.id.messages_rv);
        mMessageToSend = (EditText) findViewById(R.id.messageToSend_et);
        mSendMessageButton = (Button) findViewById(R.id.sendMessage_btn);

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if(view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        mLayoutManager = new LinearLayoutManager(CONTEXT);
        mLayoutManager.setStackFromEnd(true);
        mLayoutManager.setReverseLayout(true);
        mChatRoomRecyclerView.setLayoutManager(mLayoutManager);

        mAllMessages = new ArrayList<Message>();

        mSendMessageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Message messageToSend = new Message(mMessageToSend.getText().toString(), false);
                messageToSend.setUuid();
                messageToSend.setAction("message");
                sendMsg(messageToSend);
            }
        });

        mTypeSelected = getIntent().getParcelableExtra("PAR_KEY");
        System.out.println(mTypeSelected);

        webSocketAsync = new WebSocketAsync();
        webSocketAsync.execute();

        webSocketAsync.sendMessage(JSONParser.typeToJson(mTypeSelected));
        System.out.println(JSONParser.typeToJson(mTypeSelected));

    }

    private void sendMsg(Message message) {
        message.setIsIncomingMessage(false);
        webSocketAsync.sendMessage(JSONParser.messageToJson(message));
        mAllMessages.add(message);
        mChatAdapter = new ChatAdapter(CONTEXT, numberOfItems,
                ChannelActivity.this, mAllMessages);
        mChatRoomRecyclerView.setAdapter(mChatAdapter);
    }

    private void showContent() {

        mChatAdapter = new ChatAdapter(CONTEXT, numberOfItems, this, mAllMessages);
        mChatRoomRecyclerView.setAdapter(mChatAdapter);

        mLoadingLinearLayout.setVisibility(View.INVISIBLE);
        mContentLinearLayout.setVisibility(View.VISIBLE);
    }

    private void showLoading() {
        mLoadingLinearLayout.setVisibility(View.VISIBLE);
        mContentLinearLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        System.out.println("Clicked: " + clickedItemIndex);
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
                                    mMessagesReceived = JSONParser.messagesFromJson(mServerResponse);
                                    for(Message messageReceived : mMessagesReceived) {
                                        messageReceived.setIsIncomingMessage(true);
                                        messageReceived.setUuid();
                                        mAllMessages.add(messageReceived);
                                    }
                                    mChatAdapter = new ChatAdapter(CONTEXT, numberOfItems,
                                            ChannelActivity.this, mAllMessages);
                                    mChatRoomRecyclerView.setAdapter(mChatAdapter);
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

    @Override
    protected void onDestroy() {
        webSocketAsync.mWebSocketClient.close();
        super.onDestroy();
    }
}
