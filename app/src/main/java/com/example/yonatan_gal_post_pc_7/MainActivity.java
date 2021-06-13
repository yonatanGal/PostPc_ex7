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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        db = FirebaseFirestore.getInstance();

        currentOrderId = sp.getString("order_id", null);
        if (currentOrderId != null)
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
                            //todo: go to new activity screen
                        }
                        String status = order.getStatus();
                        if (status.equals(Constants.WAITING))
                        {
                            //todo go to edit activity screen
                        }
                        else if (status.equals(Constants.IN_PROGRESS))
                        {
                            //todo: go to in progress activity
                        }
                        else if (status.equals(Constants.READY))
                        {
                            //todo: go to ready activity
                        }
                        else
                        {
                            //todo: start new activity because status==done
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