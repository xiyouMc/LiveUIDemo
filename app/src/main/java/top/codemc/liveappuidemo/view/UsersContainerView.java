package top.codemc.liveappuidemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;

import java.lang.reflect.Constructor;

import top.codemc.liveappuidemo.R;
import top.codemc.liveappuidemo.widget.UserContainerLayoutManager;

/**
 * Created by xiyoumc on 16/8/5.
 */

public class UsersContainerView extends RecyclerView {

    private static final String TAG = "UsersContainerView";

    private static final Class<?>[] sConstructorSignature = new Class[]{
            Context.class, AttributeSet.class};

    final Object[] sConstructorArgs = new Object[2];

    public UsersContainerView(Context context) {
        this(context, null);
    }

    public UsersContainerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UsersContainerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        final TypedArray a =
                context.obtainStyledAttributes(attrs, R.styleable.usercontainer_UserContainerView, defStyle, 0);
        final String name = a.getString(R.styleable.usercontainer_UserContainerView_usercontainer_layoutManager);
        if (!TextUtils.isEmpty(name)) {
            loadLayoutManagerFromName(context, attrs, name);
        }
        a.recycle();
    }

    private void loadLayoutManagerFromName(Context context, AttributeSet attrs, String name) {
        final String packageName = context.getPackageName();
        name = packageName + ".widget." + name;
        try {
            Class<? extends UserContainerLayoutManager> clazz =
                    context.getClassLoader().loadClass(name).asSubclass(UserContainerLayoutManager.class);

            Constructor<? extends UserContainerLayoutManager> constructor =
                    clazz.getConstructor(sConstructorSignature);

            sConstructorArgs[0] = context;
            sConstructorArgs[1] = attrs;

            setLayoutManager(constructor.newInstance(sConstructorArgs));
        } catch (Exception e) {
            throw new IllegalStateException("Could not load UserContainerLayoutManager from " +
                    "class: " + name, e);
        }
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
    }

    public UserContainerLayoutManager.Orientation getOrientation() {
        UserContainerLayoutManager layout = (UserContainerLayoutManager) getLayoutManager();
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
