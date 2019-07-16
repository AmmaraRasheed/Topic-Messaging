package com.example.sendtotopc;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    EditText title,message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Message");
        title=findViewById(R.id.title);
        message=findViewById(R.id.message);
    }
    public void Subscribe(View view){
        final Button btn=findViewById(R.id.subscribe);
        FirebaseMessaging.getInstance().subscribeToTopic("notification").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Error "+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }else{
                    btn.setBackgroundColor(Color.TRANSPARENT);
                    btn.setText("subscribed");
                    btn.setTextColor(getResources().getColor(R.color.colorAccent));
                    btn.setPadding(10,5,10,5);
                }

            }
        });
    }
    public void sendMessage(View view){
        databaseReference.push().setValue(new Message(title.getText().toString(),message.getText().toString())).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"send",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
