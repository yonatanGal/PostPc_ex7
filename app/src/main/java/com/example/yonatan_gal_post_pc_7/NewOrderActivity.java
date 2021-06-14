package com.example.yonatan_gal_post_pc_7;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class NewOrderActivity extends AppCompatActivity {

    SharedPreferences sp;
    FirebaseFirestore db;

    EditText costumerName;
    EditText comment;
    CheckBox hummus;
    CheckBox thaini;
    EditText pickles;
    Button placeOrder;
    Order order;
    MyApp app;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_order_activity);
        app = new MyApp();
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        db = FirebaseFirestore.getInstance();

        this.costumerName = findViewById(R.id.new_order_costumer_name);
        this.comment = findViewById(R.id.new_order_comment);
        this.hummus = findViewById(R.id.new_order_hummus_checkbox);
        this.thaini = findViewById(R.id.new_order_thaini_checkbox);
        this.pickles = findViewById(R.id.new_order_pickles);
        this.placeOrder = findViewById(R.id.new_order_place_order);

        this.order = new Order();

        placeOrder.setOnClickListener(v ->
        {
            String name = costumerName.getText().toString();
            if (name.equals(""))
            {
                costumerName.setError("Error: name cannot be empty");
                return;
            }
            String pickleAmount = this.pickles.getText().toString();
            if (pickleAmount.equals("") || !TextUtils.isDigitsOnly(pickleAmount))
            {
                pickleAmount = "0";
            }
            order.setCostumerName(name);
            order.setPickles(Integer.parseInt(pickleAmount));
            order.setHummus(this.hummus.isChecked());
            order.setThaini(this.thaini.isChecked());
            order.setComment(this.comment.getText().toString());
            order.setStatus(Constants.WAITING);

            db.collection("orders").document(order.getId()).set(order).addOnSuccessListener(
                    document ->
                    {
                        sp.edit().putString("order_id", order.getId()).apply();
                        Intent intent = new Intent(this, EditOrderActivity.class);
                        intent.putExtra("order", order);
                        startActivity(intent);
                        finish();
                    }
            ).addOnFailureListener(
                    failure ->
                    {
                        Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
                    }
            );




        });

    }
}
