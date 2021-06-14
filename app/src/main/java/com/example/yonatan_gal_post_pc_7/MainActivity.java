package com.example.yonatan_gal_post_pc_7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.firebase.firestore.FirebaseFirestore;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db;
    SharedPreferences sp;
    String currentOrderId;
    myApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = new myApp();
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        db = FirebaseFirestore.getInstance();

        currentOrderId = sp.getString("order_id", null);
        if (currentOrderId == null)
        {
            goToActivity(NewOrderActivity.class, null);
        }

        else
        {
            db.collection("orders").document(currentOrderId).get().
                    addOnSuccessListener(result ->
                    {
                        if (result == null)
                        {
                            goToActivity(NewOrderActivity.class, null);
                        }
                        Order order = result.toObject(Order.class);
                        if (order == null)
                        {
                            goToActivity(NewOrderActivity.class, null);
                        }
                        String status = order.getStatus();
                        if (status.equals(Constants.WAITING))
                        {
                            goToActivity(EditOrderActivity.class, order);
                        }
                        else if (status.equals(Constants.IN_PROGRESS))
                        {
                            goToActivity(InProgressActivity.class, order);
                        }
                        else if (status.equals(Constants.READY))
                        {
                            goToActivity(ReadyActivity.class, order);
                        }
                        else
                        {
                            goToActivity(NewOrderActivity.class, null);
                        }
                    });
        }
    }
    public void goToActivity(Class<?> activity, @Nullable Order order)
    {
        Intent intent = new Intent(this, activity);
        intent.putExtra("order", order);
        startActivity(intent);
        finish();
    }
}