package top.codemc.liveappuidemo.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import top.codemc.liveappuidemo.R;

/**
 *
 * Created by xiyoumc on 16/8/22.
 */
public class LiveFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_live, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }

    public void initView(View view) {
        LinearLayout location_layout = (LinearLayout) view.findViewById(R.id.location_layout);
        location_layout.setOnClickListener(this);
        ImageView close_bt = (ImageView) view.findViewById(R.id.close_bt);
        close_bt.setOnClickListener(this);

        EditText edit_room_name = (EditText) view.findViewById(R.id.edit_room_name);
        edit_room_name.setOnClickListener(this);

        LinearLayout layout_topic = (LinearLayout) view.findViewById(R.id.layout_topic);
        layout_topic.setOnClickListener(this);

        Button create_live_bt = (Button) view.findViewById(R.id.create_live_bt);
        create_live_bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.location_layout == id) {

        } else if (R.id.close_bt == id) {

        } else if (R.id.edit_room_name == id) {

        } else if (R.id.layout_topic == id) {

        } else if (R.id.create_live_bt == id) {

        }
    }
}
