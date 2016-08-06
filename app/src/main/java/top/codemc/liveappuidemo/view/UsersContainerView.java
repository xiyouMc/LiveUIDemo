package top.codemc.liveappuidemo.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import top.codemc.liveappuidemo.widget.UserContainerLayoutManager;

/**
 * Created by xiyoumc on 16/8/5.
 */

public class UsersContainerView extends RecyclerView {

    private static final String TAG = "UsersContainerView";

    public UsersContainerView(Context context) {
        super(context);
    }

    public UsersContainerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UsersContainerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void loadLayoutManagerFromName(Context context, AttributeSet attrs, String name) {
        final String packageName = context.getPackageName();
        name = packageName + ".widge." + name;

    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
    }

    public UserContainerLayoutManager.Orientation getOrientation(){
        UserContainerLayoutManager layout = (UserContainerLayoutManager)getLayoutManager();
        return layout.getOrientation();
    }
    public void setOrientation(UserContainerLayoutManager.Orientation orientation) {
        UserContainerLayoutManager layout = (UserContainerLayoutManager) getLayoutManager();
        layout.setOrientation(orientation);
    }

    public int getFirstVisiblePosition() {
        UserContainerLayoutManager layout = (UserContainerLayoutManager) getLayoutManager();
        return layout.getFirstVisiblePosition();
    }

    public int getLastVisiblePosition() {
        UserContainerLayoutManager layout = (UserContainerLayoutManager) getLayoutManager();
        return layout.getLastVisiblePosition();
    }
}
