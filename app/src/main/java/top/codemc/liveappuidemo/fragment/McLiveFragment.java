package top.codemc.liveappuidemo.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import top.codemc.liveappuidemo.R;
import top.codemc.liveappuidemo.model.UsersContainerModel;
import top.codemc.liveappuidemo.view.UsersContainerView;
import top.codemc.liveappuidemo.widget.UserContainerLayoutManager;

/**
 * Created by xiyoumc on 16/8/5.
 */
public class McLiveFragment extends Fragment {

    private UsersContainerView mUsersContainerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_live, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Activity activity = getActivity();
        mUsersContainerView = (UsersContainerView) view.findViewById(R.id.listview_users);
        mUsersContainerView.setHasFixedSize(false);
        mUsersContainerView.setLongClickable(false);
        LayoutAdapter adapter = new LayoutAdapter(activity);
        mUsersContainerView.setAdapter(adapter);
        mUsersContainerView.setOrientation(UserContainerLayoutManager.Orientation.HORIZONTAL);

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.user_img);
        UsersContainerModel model = new UsersContainerModel.Builder().userProtrait(bmp).userType(bmp).build();
        adapter.addItem(0, model);

        adapter.notifyDataSetChanged();
    }
}
