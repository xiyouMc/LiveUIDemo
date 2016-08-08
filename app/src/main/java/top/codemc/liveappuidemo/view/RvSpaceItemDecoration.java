package top.codemc.liveappuidemo.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by xiyoumc on 16/8/8.
 */
public class RvSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int devideSpace;

    public RvSpaceItemDecoration(int space) {
        this.devideSpace = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildPosition(view) != 0) {
            outRect.left = devideSpace;
        }
    }
}
