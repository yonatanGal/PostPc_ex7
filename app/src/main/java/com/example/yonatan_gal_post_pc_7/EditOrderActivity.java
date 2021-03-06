package com.example.yonatan_gal_post_pc_7;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

public class EditOrderActivity extends AppCompatActivity {
    SharedPreferences sp;
    FirebaseFirestore db;

    EditText comment;
    CheckBox hummus;
    CheckBox thaini;
    EditText pickles;
    TextView text;
    Button changeOrder;
    Button deleteOrder;
    Order order;
    ListenerRegistration status;
    MyApp app;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_order_activity);
        app = new MyApp();
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();

        this.comment = findViewById(R.id.edit_order_comment);
        this.hummus = findViewById(R.id.edit_order_hummus_checkbox);
        this.thaini = findViewById(R.id.edit_order_thaini_checkbox);
        this.pickles = findViewById(R.id.edit_order_pickles);
        this.changeOrder = findViewById(R.id.edit_order_change_order);
        this.text = findViewById(R.id.edit_order_text);
        this.deleteOrder = findViewById(R.id.edit_order_delete_button);

        Intent incomeIntent = getIntent();
        this.order = (Order) incomeIntent.getSerializableExtra("order");
        String cur_text = this.text.getText().toString();
        String name = this.order.getCostumerName();
        this.text.setText(name + ", " + cur_text);
        this.hummus.setChecked(order.isHummus());
        this.thaini.setChecked(order.isThaini());
        this.comment.setText(order.getComment());
        this.pickles.setText(Integer.toString(order.getPickles()));


        changeOrder.setOnClickListener(v ->
        {
            String pickleAmount = this.pickles.getText().toString();
            if (pickleAmount.equals("") || !TextUtils.isDigitsOnly(pickleAmount)) {
                pickleAmount = "0";
            }
            order.setPickles(Integer.parseInt(pickleAmount));
            order.setHummus(this.hummus.isChecked());
            order.setThaini(this.thaini.isChecked());
            order.setComment(this.comment.getText().toString());
            this.pickles.setText(String.valueOf(order.getPickles()));

            db.collection("orders").document(order.getId()).update("hummus", order.isHummus());
            db.collection("orders").document(order.getId()).update("thaini", order.isThaini());
            db.collection("orders").document(order.getId()).update("pickles", order.getPickles());
            db.collection("orders").document(order.getId()).update("comment", order.getComment());
        });

        deleteOrder.setOnClickListener(v ->
                db.collection("orders").document(order.getId()).delete().
                        addOnSuccessListener(v2 ->
                        {
                            Intent newActivityIntent = new Intent(this, MainActivity.class);
                            startActivity(newActivityIntent);
                            finish();
                        }).addOnFailureListener(error ->
                        Toast.makeText(this, "Error deleting order!", Toast.LENGTH_SHORT).show()));

        status = db.collection("orders").document(order.getId()).
                addSnapshotListener((value, error) ->
                {
                    if (!value.exists() || value == null)
                    {
                        sp.edit().putString("order_id", null).apply();
                        Intent editIntent = new Intent(this, MainActivity.class);
                        startActivity(editIntent);
                        finish();
                    }
                    else
                    {
                        Order order = value.toObject(Order.class);
                        String curStatus = order.getStatus();
                        if (curStatus.equals(Constants.DONE))
                        {
                            Intent editIntent = new Intent(this, MainActivity.class);
                            editIntent.putExtra("order", order);
                            startActivity(editIntent);
                            finish();
                        }
                        else if (curStatus.equals(Constants.IN_PROGRESS))
                        {
                            Intent inProgressIntent = new Intent(this, InProgressActivity.class);
                            inProgressIntent.putExtra("order", order);
                            startActivity(inProgressIntent);
                            finish();
                        }

                        else if (curStatus.equals(Constants.READY))
                        {
                            Intent readyIntent = new Intent(this, ReadyActivity.class);
                            startActivity(readyIntent);
                            finish();
                        }
                    }
                });

        };

    }

