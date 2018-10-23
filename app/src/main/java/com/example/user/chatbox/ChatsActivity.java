package com.example.user.chatbox;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ChatsActivity extends AppCompatActivity {

    private EditText messageText;
    private ImageButton sendButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mReferenceSend;
    private DatabaseReference mReferenceReceive;

    private Map map;
    private GenericTypeIndicator<Map<String, String>> genericTypeIndicator;
    private LinearLayout layout;
    private ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);

        layout = findViewById(R.id.layout1);
        scrollView = findViewById(R.id.scrollView);
        messageText = findViewById(R.id.messageText);
        // recyclerView = findViewById(R.id.recyclerView);
        sendButton = findViewById(R.id.sendImage);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(RecyclerViewAdapter.name1);

        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        map = new HashMap();
        genericTypeIndicator = new GenericTypeIndicator<Map<String, String>>() {
        };

        mAuth = FirebaseAuth.getInstance();
        mReferenceSend = FirebaseDatabase.getInstance().getReference("Messages")
                .child(MainActivity.name + "_" + RecyclerViewAdapter.name1);

        mReferenceReceive = FirebaseDatabase.getInstance().getReference("Messages")
                .child(RecyclerViewAdapter.name1 + "_" + MainActivity.name);

        retrieveMsg();
        init();

    }


    private void init() {

        messageText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String ch = s.toString().trim();
                if (ch.equals("")) {

                    sendButton.setVisibility(View.INVISIBLE);

                } else {

                    sendButton.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void send(View view) {
        String msg = messageText.getText().toString().trim();

        Map<String, String> map = new HashMap<>();
        map.put("message", msg);
        map.put("user", MainActivity.name);

        mReferenceSend.push().setValue(map);
        mReferenceReceive.push().setValue(map);
        messageText.setText("");

        retrieveMsg();

    }


    private void retrieveMsg() {


        mReferenceSend.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // msg.clear();


                map.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    map = ds.getValue(genericTypeIndicator);

                    String message = map.get("message").toString();
                    String user = map.get("user").toString();

                    if (user.equals(MainActivity.name)) {

                        Log.i("_msg", message);
                        Log.i("_user", user);
                        addMessageBox(message, 1);


                    } else {

                        addMessageBox(message, 2);
                    }

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void addMessageBox(String message, int type) {
        TextView textView = new TextView(ChatsActivity.this);
        textView.setText(message);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);


        if (type == 1) {
            lp.setMargins(0, 8, 8, 8);
            lp.gravity = Gravity.END;

            textView.setLayoutParams(lp);
            textView.setTextSize(18f);
            textView.setBackgroundResource(R.drawable.send_bg);
        } else {
            lp.setMargins(8, 8, 0, 8);
            lp.gravity = Gravity.START;

            textView.setLayoutParams(lp);
            textView.setTextSize(18f);
            textView.setBackgroundResource(R.drawable.receive_bg);
        }

        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }

}
