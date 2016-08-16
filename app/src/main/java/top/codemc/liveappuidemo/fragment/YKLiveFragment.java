package top.codemc.liveappuidemo.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import top.codemc.liveappuidemo.R;
import top.codemc.liveappuidemo.adapter.LayoutAdapter;
import top.codemc.liveappuidemo.adapter.LiveChatListAdapter;
import top.codemc.liveappuidemo.model.MessageModel;
import top.codemc.liveappuidemo.model.UsersContainerModel;
import top.codemc.liveappuidemo.view.RvSpaceItemDecoration;

/**
 * Created by xiyoumc on 16/8/5.
 */
public class YKLiveFragment extends Fragment implements LayoutAdapter.OnRecyclerViewListener, View.OnTouchListener,
        View.OnLayoutChangeListener {

    private static final String TAG = "McLiveFragment";

    private RelativeLayout root_view;
    private ListView liveChatListView;
    private LiveChatListAdapter liveChatListAdapter;
    private RelativeLayout below_msg_rl;
    private LinearLayout live_below_ln;
    private EditText msg_edit_text;
    private Button msg_send_bt;
    private int screenHeight = 0;
    private int keyHeight = 0;
    private RecyclerView usersView;
    private LayoutAdapter userContainerAdapter;
    private ImageView img_room_creator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_live, container, false);
    }

    @Override
    public void onViewCreated(View rootView, Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);
        final Activity activity = getActivity();
        screenHeight = activity.getWindowManager().getDefaultDisplay().getHeight();
        keyHeight = screenHeight / 3;
        initView(rootView);
        initViewData();
    }

    private void initView(View rootView) {
        root_view = (RelativeLayout) rootView.findViewById(R.id.root_view);

        img_room_creator = (ImageView) rootView.findViewById(R.id.img_room_creator);
        //top view
        usersView = (RecyclerView) rootView.findViewById(R.id.listview_users);
        //msg view
        liveChatListView = (ListView) rootView.findViewById(R.id.live_chatlist);
        //bollow view
        live_below_ln = (LinearLayout) rootView.findViewById(R.id.live_bollow_ln);
        ImageView live_send_msg = (ImageView) rootView.findViewById(R.id.live_send_msg);
        ImageView live_send_gift = (ImageView) rootView.findViewById(R.id.live_send_gift);
        ImageView live_share = (ImageView) rootView.findViewById(R.id.live_share);
        below_msg_rl = (RelativeLayout) rootView.findViewById(R.id.send_rl);
        msg_edit_text = (EditText) rootView.findViewById(R.id.live_msg);
        msg_send_bt = (Button) rootView.findViewById(R.id.live_send);
        live_send_msg.setOnTouchListener(this);
        live_send_gift.setOnTouchListener(this);
        live_share.setOnTouchListener(this);
        msg_send_bt.setOnTouchListener(this);
    }

    private void initViewData() {
        //userContainer
        usersView.setHasFixedSize(true);
        usersView.setLongClickable(false);
        configRecyclerView(getActivity(), usersView);
        //msg adapter
        liveChatListAdapter = new LiveChatListAdapter();
        liveChatListView.setAdapter(liveChatListAdapter);

        //test userContainer
        configUserContainer();
        img_room_creator.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.live_user));
    }

    private void configRecyclerView(Activity activity, RecyclerView usersView) {
        Resources resources = getResources();
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        usersView.setLayoutManager(layoutManager);
        userContainerAdapter = new LayoutAdapter(activity);
        userContainerAdapter.setOnRecyclerViewListener(this);
        usersView.setAdapter(userContainerAdapter);
        userContainerAdapter.notifyDataSetChanged();
        int spacingInPixels = resources.getDimensionPixelOffset(R.dimen.space);
        usersView.addItemDecoration(new RvSpaceItemDecoration(spacingInPixels));
    }

    private void configUserContainer() {
        TypedArray userResArray = getResources().obtainTypedArray(R.array.test_users);
        for (int index = 0; index < userResArray.length(); index++) {
            userContainerAdapter.addItem(index, new UsersContainerModel.Builder()
                    .userProtrait(BitmapFactory.decodeResource(getResources(), userResArray.getResourceId(index, -1))).build());
        }
        userContainerAdapter.notifyDataSetChanged();
        userResArray.recycle();
    }

    @Override
    public void onItemClick(int position) {
        Log.d(TAG, "position:" + position);
    }

    @Override
    public boolean onItemLongClick(int positon) {
        return false;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int id = v.getId();

        switch (id) {
            case R.id.live_send_msg:
                live_below_ln.setVisibility(View.GONE);
                below_msg_rl.setVisibility(View.VISIBLE);
                msg_edit_text.requestFocus();
                final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
                return true;
            case R.id.live_send_gift:
                return true;
            case R.id.live_share:
                return true;
            case R.id.live_send:
                msg_edit_text.requestFocus();
                String text = msg_edit_text.getText().toString();
                if (text.isEmpty()) {
                    return false;
                }
                MessageModel messageModel = new MessageModel.Builder().userName("xiyouMc:")
                        .content("Github:https://www.github.com/xiyouMc" + " content:" + text).build();
                liveChatListAdapter.addMessage(messageModel);
                liveChatListAdapter.notifyDataSetChanged();
                msg_edit_text.setText("");
                return true;
        }
        return false;
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
            below_msg_rl.setVisibility(View.GONE);
            live_below_ln.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        root_view.addOnLayoutChangeListener(this);
    }
}
