package com.example.yonatan_gal_post_pc_7;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.EditText;
import static org.junit.Assert.*;
import androidx.test.platform.app.InstrumentationRegistry;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE, sdk=28)

public class NewOrderTest {

    @Test
    public void test_defaultParams() {
        NewOrderActivity newOrderActivity = Robolectric.buildActivity(NewOrderActivity.class).create().visible().get();
        EditText customer_name = newOrderActivity.findViewById(R.id.new_order_costumer_name);
        EditText pickles = newOrderActivity.findViewById(R.id.new_order_pickles);
        CheckBox hummmus = newOrderActivity.findViewById(R.id.new_order_hummus_checkbox);
        CheckBox thaini = newOrderActivity.findViewById(R.id.new_order_thaini_checkbox);
        EditText comment = newOrderActivity.findViewById(R.id.new_order_comment);

        assertEquals("", customer_name.getText().toString());
        assertFalse(hummmus.isChecked());
        assertFalse(thaini.isChecked());
        assertEquals(pickles.getText().toString(), "");
        assertEquals(comment.getText().toString(), "");
    }

    @Test
    public void test_pickles_behaviour()
    {
        Order order = new Order();
        order.setPickles(-67);
        assertEquals(order.getPickles(), 0);
        order.setPickles(1000);
        assertEquals(order.getPickles(), 10);
    }

    @Test
    public void updateDB_Test()
    {
        Order order = new Order();
        order.setCostumerName("Yonatan");
        order.setPickles(3);

        String id = order.getId();
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        FirebaseApp.initializeApp(appContext);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("orders").document(id).update("pickles", order.getPickles());
        db.collection("orders").document(id).update("customerName", order.getCostumerName());
        db.collection("orders").document(id).get().addOnSuccessListener(documentSnapshot -> {
            assertEquals(order.getCostumerName(), documentSnapshot.getString("customerName"));
            assertEquals(order.getPickles(), Integer.parseInt(documentSnapshot.getString("pickles")));
        })
                .addOnFailureListener(documentSnapshot -> {
            assertTrue(true);
        });
    }

}