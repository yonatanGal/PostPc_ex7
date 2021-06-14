package com.example.yonatan_gal_post_pc_7;
import android.widget.EditText;
import android.widget.Switch;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;
import org.robolectric.annotation.LooperMode;
import org.robolectric.shadow.api.Shadow;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import static org.junit.Assert.*;

//@Config(manifest=Config.NONE)
@Config(sdk = 29)
@LooperMode(LooperMode.Mode.PAUSED)
@RunWith(RobolectricTestRunner.class)
public class NewOrderTest extends TestCase{

    @Test
    public void when_NewOrderActivityStarts_then_defaultParamsAreShown()
    {
        NewOrderActivity newOrderActivity = Robolectric.buildActivity(NewOrderActivity.class).create().visible().get();
        EditText name = newOrderActivity.findViewById(R.id.new_order_costumer_name);
        assertEquals(name.getText().toString(), "");

    }
}
