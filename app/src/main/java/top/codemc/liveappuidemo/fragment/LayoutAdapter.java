package top.codemc.liveappuidemo.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import top.codemc.liveappuidemo.R;
import top.codemc.liveappuidemo.model.UsersContainerModel;

public class LayoutAdapter extends RecyclerView.Adapter<LayoutAdapter.LiveUserListViewHolder> {
    private static final int COUNT = 100;
    private final Context mContext;
    private final List<UsersContainerModel> userContainerItems;
    private int mCurrentItemId = 0;

    public static class LiveUserListViewHolder extends RecyclerView.ViewHolder {
        public final ImageView user_portrait;
        public final ImageView img_user_type;

        public LiveUserListViewHolder(View view) {
            super(view);
            user_portrait = (ImageView) view.findViewById(R.id.user_portrait);
            img_user_type = (ImageView) view.findViewById(R.id.img_user_type);
        }
    }

    public LayoutAdapter(Context context) {
        mContext = context;
        userContainerItems = new ArrayList<UsersContainerModel>(COUNT);

    }

    public void addItem(int position, UsersContainerModel entry) {
//        final int id = mCurrentItemId++;
        userContainerItems.add(position, entry);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        userContainerItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public LiveUserListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.listview_users_item, parent, false);
        return new LiveUserListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LiveUserListViewHolder holder, int position) {
        UsersContainerModel usersContainerModel = userContainerItems.get(position);
        holder.user_portrait.setImageBitmap(usersContainerModel.userPortrait);
        holder.img_user_type.setImageBitmap(usersContainerModel.userType);
//        boolean isVertical = (mRecyclerView.getOrientation() == UserContainerLayoutManager.Orientation.VERTICAL);
//        final View itemView = holder.itemView;
//
//        final int itemId = mItems.get(position);
    }

    @Override
    public int getItemCount() {
        return userContainerItems.size();
    }
}
