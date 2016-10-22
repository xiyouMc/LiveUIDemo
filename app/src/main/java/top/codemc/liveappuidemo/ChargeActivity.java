package top.codemc.liveappuidemo;

import com.vivavideo.mobile.commonui.ActionSheetDialog;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ChargeActivity extends ActionBarActivity {

    private LinearLayout pay4;
    private ActionSheetDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.charge_activity);

        final View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.charge_channels, null);
        RelativeLayout alipayCharge = (RelativeLayout) view.findViewById(R.id.alipay_charge);
        RelativeLayout wxCharge = (RelativeLayout) view.findViewById(R.id.wx_charge);
        pay4 = (LinearLayout) findViewById(R.id.pay4);
        pay4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dialog != null) {
                    dialog.show();
                    return;
                }
                dialog = new ActionSheetDialog(ChargeActivity.this).builder();
                dialog.showCancel(false);
                dialog.addViewContent(view);
                dialog.setTitle("选择支付方式").show();
            }
        });

    }

}
