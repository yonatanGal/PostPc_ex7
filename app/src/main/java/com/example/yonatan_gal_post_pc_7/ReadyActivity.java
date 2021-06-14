package com.example.yonatan_gal_post_pc_7;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

public class ReadyActivity extends AppCompatActivity {
    SharedPreferences sp;
    FirebaseFirestore db;
    Button gotItButton;
    Order order;
    MyApp app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ready_activity);
        app = new MyApp();
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();
        Intent incomeIntent = getIntent();
        this.order = (Order) incomeIntent.getSerializableExtra("order");
        this.gotItButton = findViewById(R.id.ready_activity_got_it_button);

        gotItButton.setOnClickListener(v ->
        {

            sp.edit().putString("order_id", null).apply();
            order.setStatus("done");
            db.collection("orders").document(order.getId()).update("status","done");
            Intent newIntent = new Intent(this, MainActivity.class);
            startActivity(newIntent);
            finish();
        });
    }
}
