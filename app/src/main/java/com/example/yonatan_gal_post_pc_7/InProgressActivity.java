package com.example.yonatan_gal_post_pc_7;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

public class InProgressActivity extends AppCompatActivity {
    SharedPreferences sp;
    FirebaseFirestore db;
    Order order;
    MyApp app;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_progress_activity);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        db = FirebaseFirestore.getInstance();
        app = new MyApp();
        Intent incomeIntent = getIntent();
        this.order = (Order) incomeIntent.getSerializableExtra("order");


        ListenerRegistration status = db.collection("orders").document(order.getId()).
                addSnapshotListener((value, error) ->
                {
                    if (!value.exists() || value == null) {
                        sp.edit().putString("order_id", null).apply();
                        Intent editIntent = new Intent(this, MainActivity.class);
                        startActivity(editIntent);
                        finish();
                    } else {
                        Order order = value.toObject(Order.class);
                        String curStatus = order.getStatus();
                        if (curStatus.equals(Constants.DONE)) {
                            Intent editIntent = new Intent(this, MainActivity.class);
                            editIntent.putExtra("order", order);
                            startActivity(editIntent);
                            finish();
                        } else if (curStatus.equals(Constants.READY)) {
                            Intent readyIntent = new Intent(this, ReadyActivity.class);
                            readyIntent.putExtra("order", order);
                            startActivity(readyIntent);
                            finish();
                        }
                    }
                });
    }
}
