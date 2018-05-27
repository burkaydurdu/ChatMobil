package com.bdurdu.mysocket;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bdurdu.mysocket.Data.ServerData;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;


public class ChatFragment extends Fragment {

    private TextView messageBox;
    private EditText inputMessage;
    private Button sendMessage;
    private Socket mSocket;

    private View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_chat, container, false);

        onCreateView();
        connectionSocketIo();
        return v;
    }

    private void onCreateView() {
        messageBox = v.findViewById(R.id.message_box);
        inputMessage = v.findViewById(R.id.input_message);
        sendMessage = v.findViewById(R.id.send_message);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }
    private void connectionSocketIo() {
        try {
            mSocket = IO.socket(ServerData.SERVER_ADDRESS);
            mSocket.connect();
            mSocket.emit("room", MainActivity.room);
            mSocket.on("chat", onNewMessage);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage() {
        String message = inputMessage.getText().toString().trim();

        if(TextUtils.isEmpty(message)) {
            return;
        }

        inputMessage.setText("");

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", MainActivity.username != null ? MainActivity.username : "who");
            jsonObject.put("message", message);
            jsonObject.put("room", MainActivity.room);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mSocket.emit("chat",  jsonObject);
    }


    private void addMessage(String username, String message) {
        messageBox.setText(String.format("%s\n%s : %s", messageBox.getText().toString(), username, message));
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    Log.d("gelen data", data.toString());
                    String username;
                    String message;
                    try {
                        username = data.getString("username");
                        message = data.getString("message");
                    } catch (JSONException e) {
                        return;
                    }
                    addMessage(username, message);
                }
            });
        }
    };
}
