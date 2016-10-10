package top.codemc.liveappuidemo.fragment;

import com.facebook.rebound.BaseSpringSystem;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import top.codemc.liveappuidemo.R;
import top.codemc.liveappuidemo.adapter.LayoutAdapter;
import top.codemc.liveappuidemo.adapter.LiveChatListAdapter;
import top.codemc.liveappuidemo.model.MessageModel;
import top.codemc.liveappuidemo.model.UsersContainerModel;
import top.codemc.liveappuidemo.view.RvSpaceItemDecoration;

/**
 * Created by xiyoumc on 16/8/5.
 */
public class YKLiveFragment extends Fragment implements LayoutAdapter.OnRecyclerViewListener, View.OnClickListener,
        View.OnLayoutChangeListener, View.OnTouchListener {

    private static final String TAG = "McLiveFragment";
    private final BaseSpringSystem mSpringSystem = SpringSystem.create();
    private Spring mScaleSpring;

    private RelativeLayout root_view;
    private ListView liveChatListView;
    private LiveChatListAdapter liveChatListAdapter;
    private RelativeLayout below_msg_rl;
    private LinearLayout live_below_ln, room_usernum_container;
    private EditText msg_edit_text;
    private int keyHeight = 0;
    private RecyclerView usersView;
    private LayoutAdapter userContainerAdapter;
    private ImageView img_room_creator;
    private ScrollView center_sv;
    private LinearLayout giftLinear1, giftLinear2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_live, container, false);
    }

    @Override
    public void onViewCreated(View rootView, Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);
        final Activity activity = getActivity();
        int screenHeight = activity.getWindowManager().getDefaultDisplay().getHeight();
        keyHeight = screenHeight / 3;
        initView(rootView);
        initViewData();
    }

    private void initView(View rootView) {
        root_view = (RelativeLayout) rootView.findViewById(R.id.root_view);

        //top view
        room_usernum_container = (LinearLayout) rootView.findViewById(R.id.room_usernum_container);
        img_room_creator = (ImageView) rootView.findViewById(R.id.img_room_creator);
        center_sv = (ScrollView) rootView.findViewById(R.id.center_sv);
        center_sv.setOnTouchListener(this);
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
        Button msg_send_bt = (Button) rootView.findViewById(R.id.live_send);
        live_send_msg.setOnClickListener(this);
        live_send_gift.setOnClickListener(this);
        live_share.setOnClickListener(this);
        msg_send_bt.setOnClickListener(this);

        //gift
        giftLinear1 = (LinearLayout) rootView.findViewById(R.id.gift_linear1);
        giftLinear1.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.gift_activity_open));
        giftLinear2 = (LinearLayout) rootView.findViewById(R.id.gift_linear2);
        giftLinear2.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.gift_activity_open));

        final ImageView gift_png1 = (ImageView) rootView.findViewById(R.id.gift_png1);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                giftLinear2.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.gift_close_close));
                giftLinear2.setVisibility(View.INVISIBLE);

                gift_png1.setVisibility(View.VISIBLE);
                gift_png1.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.gift_png_show));
            }
        }, 2000);

        final ImageView gift_num1 = (ImageView) rootView.findViewById(R.id.gift_num1);
        final ImageView gift_num2 = (ImageView) rootView.findViewById(R.id.gift_num2);


        mScaleSpring = mSpringSystem.createSpring().setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(86, 7)).addListener(new SimpleSpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                float value = (float) spring.getCurrentValue();
                float scale = 1f - value;
                gift_num1.setScaleX(scale);
                gift_num1.setScaleY(scale);
                gift_num2.setScaleX(scale);
            }
        });

//        while (true) {
        mScaleSpring.setEndValue(1);
        Handler handler1 = new Handler(Looper.getMainLooper());
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScaleSpring.setEndValue(0);
            }
        }, 1000);
//        }
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
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.live_send_msg:
                live_below_ln.setVisibility(View.GONE);
                below_msg_rl.setVisibility(View.VISIBLE);
                msg_edit_text.requestFocus();
                final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
                return;
            case R.id.live_send_gift:
                return;
            case R.id.live_share:
                return;
            case R.id.live_send:
                msg_edit_text.requestFocus();
                String text = msg_edit_text.getText().toString();
                if (text.isEmpty()) {
                    return;
                }
                MessageModel messageModel = new MessageModel.Builder().userName("xiyouMc:")
                        .content("Github:https://www.github.com/xiyouMc" + " content:" + text).build();
                liveChatListAdapter.addMessage(messageModel);
                liveChatListAdapter.notifyDataSetChanged();
                msg_edit_text.setText("");
                return;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int id = v.getId();
        switch (id) {
            case R.id.center_sv:
                if (MotionEvent.ACTION_UP == event.getAction()) {
                    controlViewVisiable();
                    return true;
                }
                return false;
        }
        return false;
    }

    private synchronized void controlViewVisiable() {
        int visible = liveChatListView.getVisibility() == View.INVISIBLE
                ? View.VISIBLE : View.INVISIBLE;
        Log.d(TAG, "visible" + visible);
        liveChatListView.setVisibility(visible);
        room_usernum_container.setVisibility(visible);
        usersView.setVisibility(visible);
        live_below_ln.setVisibility(visible);
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
