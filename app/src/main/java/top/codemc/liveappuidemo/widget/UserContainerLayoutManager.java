package top.codemc.liveappuidemo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

import top.codemc.liveappuidemo.R;
import top.codemc.liveappuidemo.core.ItemSelectionSupport;

/**
 * Created by xiyoumc on 16/8/6.
 */
public abstract class UserContainerLayoutManager extends RecyclerView.LayoutManager {

    private SavedState mPendingSavedState = null;
    private int mPendingScrollPosition = RecyclerView.NO_POSITION;
    private int mPendingScrollOffset = 0;

    private RecyclerView mRecyclerView;

    public static enum Orientation {
        HORIZONTAL,
        Orientation, VERTICAL
    }

    public static enum Direction {
        START,
        Direction, END
    }

    private boolean mIsVertical = true;

    public UserContainerLayoutManager(Orientation orientation) {
        mIsVertical = (orientation == Orientation.VERTICAL);
    }

    public UserContainerLayoutManager(Context context, AttributeSet attrs, int defStyle) {
        final TypedArray a =
                context.obtainStyledAttributes(attrs, R.styleable.usercontainer_layoutManager, defStyle, 0);
        final int indexCount = a.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            final int attr = a.getIndex(i);
            if (attr == R.styleable.usercontainer_layoutManager_android_orientation) {
                final int orientation = a.getInt(attr, -1);
                if (orientation >= 0) {
                    setOrientation(Orientation.values()[orientation]);
                }
            }
        }
    }

    public void setOrientation(Orientation orientation) {
        final boolean isVertical = (orientation == Orientation.VERTICAL);
        if (this.mIsVertical == isVertical) {
            return;
        }

        this.mIsVertical = isVertical;
        requestLayout();
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        if (mIsVertical) {
            return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        } else {
            return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.MATCH_PARENT);
        }
    }

    public Orientation getOrientation() {
        return (mIsVertical ? Orientation.VERTICAL : Orientation.HORIZONTAL);
    }

    public int getFirstVisiblePosition() {
        if (getChildCount() == 0) {
            return 0;
        }

        return getPosition(getChildAt(0));
    }

    public int getLastVisiblePosition() {
        final int childCount = getChildCount();
        if (childCount == 0) {
            return 0;
        }

        return getPosition(getChildAt(childCount - 1));
    }

    protected int getPendingScrollPosition() {
        if (mPendingSavedState != null) {
            return mPendingSavedState.anchorItemPosition;
        }

        return mPendingScrollPosition;
    }

    private Bundle getPendingItemSelectionState() {
        if (mPendingSavedState != null) {
            return mPendingSavedState.itemSelectionState;
        }

        return null;
    }

    protected int getPendingScrollOffset() {
        if (mPendingSavedState != null) {
            return 0;
        }

        return mPendingScrollOffset;
    }

    protected abstract void measureChild(View child, Direction direction);

    protected abstract void layoutChild(View child, Direction direction);

    protected void detachChild(View child, Direction direction) {
        // Do nothing by default.
    }

    protected void onLayoutScrapList(RecyclerView.Recycler recycler, RecyclerView.State state) {
        final int childCount = getChildCount();
        if (childCount == 0 || state.isPreLayout() || !supportsPredictiveItemAnimations()) {
            return;
        }

        final List<RecyclerView.ViewHolder> scrapList = recycler.getScrapList();
        fillFromScrapList(scrapList, Direction.START);
        fillFromScrapList(scrapList, Direction.END);
    }

    private void fillFromScrapList(List<RecyclerView.ViewHolder> scrapList, Direction direction) {
        final int firstPosition = getFirstVisiblePosition();

        int position;
        if (direction == Direction.END) {
            position = firstPosition + getChildCount();
        } else {
            position = firstPosition - 1;
        }

        View scrapChild;
        while ((scrapChild = findNextScrapView(scrapList, direction, position)) != null) {
            setupChild(scrapChild, direction);
            position += (direction == Direction.END ? 1 : -1);
        }
    }

    private static View findNextScrapView(List<RecyclerView.ViewHolder> scrapList, Direction direction,
                                          int position) {
        final int scrapCount = scrapList.size();

        RecyclerView.ViewHolder closest = null;
        int closestDistance = Integer.MAX_VALUE;

        for (int i = 0; i < scrapCount; i++) {
            final RecyclerView.ViewHolder holder = scrapList.get(i);

            final int distance = holder.getPosition() - position;
            if ((distance < 0 && direction == Direction.END) ||
                    (distance > 0 && direction == Direction.START)) {
                continue;
            }

            final int absDistance = Math.abs(distance);
            if (absDistance < closestDistance) {
                closest = holder;
                closestDistance = absDistance;

                if (distance == 0) {
                    break;
                }
            }
        }

        if (closest != null) {
            return closest.itemView;
        }

        return null;
    }

    private void setupChild(View child, Direction direction) {
        final ItemSelectionSupport itemSelection = ItemSelectionSupport.from(mRecyclerView);
        if (itemSelection != null) {
            final int position = getPosition(child);
            itemSelection.setViewChecked(child, itemSelection.isItemChecked(position));
        }

        measureChild(child, direction);
        layoutChild(child, direction);
    }

    protected abstract boolean canAddMoreViews(Direction direction, int limit);

    protected void setPendingScrollPositionWithOffset(int position, int offset) {
        mPendingScrollPosition = position;
        mPendingScrollOffset = offset;
    }

    protected int getAnchorItemPosition(RecyclerView.State state) {
        final int itemCount = state.getItemCount();

        int pendingPosition = getPendingScrollPosition();
        if (pendingPosition != RecyclerView.NO_POSITION) {
            if (pendingPosition < 0 || pendingPosition >= itemCount) {
                pendingPosition = RecyclerView.NO_POSITION;
            }
        }

        if (pendingPosition != RecyclerView.NO_POSITION) {
            return pendingPosition;
        } else if (getChildCount() > 0) {
            return findFirstValidChildPosition(itemCount);
        } else {
            return 0;
        }
    }

    private int findFirstValidChildPosition(int itemCount) {
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View view = getChildAt(i);
            final int position = getPosition(view);
            if (position >= 0 && position < itemCount) {
                return position;
            }
        }

        return 0;
    }

    protected int getChildStart(View child) {
        return (mIsVertical ? getDecoratedTop(child) : getDecoratedLeft(child));
    }

    protected static class SavedState implements Parcelable {
        protected static final SavedState EMPTY_STATE = new SavedState();

        private final Parcelable superState;
        private int anchorItemPosition;
        private Bundle itemSelectionState;

        private SavedState() {
            superState = null;
        }

        protected SavedState(Parcelable superState) {
            if (superState == null) {
                throw new IllegalArgumentException("superState must not be null");
            }

            this.superState = (superState != EMPTY_STATE ? superState : null);
        }

        protected SavedState(Parcel in) {
            this.superState = EMPTY_STATE;
            anchorItemPosition = in.readInt();
            itemSelectionState = in.readParcelable(getClass().getClassLoader());
        }

        public Parcelable getSuperState() {
            return superState;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            out.writeInt(anchorItemPosition);
            out.writeParcelable(itemSelectionState, flags);
        }

        public static final Parcelable.Creator<SavedState> CREATOR
                = new Parcelable.Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
    @Override
    public void onAttachedToWindow(RecyclerView view) {
        super.onAttachedToWindow(view);
        mRecyclerView = view;
    }

    @Override
    public void onDetachedFromWindow(RecyclerView view, RecyclerView.Recycler recycler) {
        super.onDetachedFromWindow(view, recycler);
        mRecyclerView = null;
    }

}
