package com.bdurdu.mysocket;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bdurdu.mysocket.Data.ServerData;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {

    public static String username;
    public static String room;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onCreateUserName();
    }

    private void onCreateUserName() {
        new MaterialDialog.Builder(this)
                .title("Tanimlama")
                .content("Kullanici Adi Gir")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("Nickname", null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        username = input.toString();
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        onCreateRoomName();
                    }
                }).show();
    }

    private void onCreateRoomName() {
        new MaterialDialog.Builder(this)
                .title("Tanimlama")
                .content("Room")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("Room Number", null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        room = input.toString().trim();
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        onCreateView();
                    }
                }).show();
    }

    private void onCreateView() {
        Fragment fragment = new ChatFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_fragement, fragment)
                .addToBackStack(null)
                .commit();
    }

}
