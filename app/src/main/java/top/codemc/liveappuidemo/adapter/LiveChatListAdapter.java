package top.codemc.liveappuidemo.adapter;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import top.codemc.liveappuidemo.R;
import top.codemc.liveappuidemo.model.MessageModel;

public class LiveChatListAdapter extends BaseAdapter {

    private static final String TAG = "LiveChatListAdapter";

    private List<MessageModel> uiMessageList = new ArrayList<>();

    public void addMessage(MessageModel message) {
        uiMessageList.add(message);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return uiMessageList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView position = " + position + ", convertView = " + convertView);
        ViewHolder holder;
        final MessageModel data = uiMessageList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_message_item, null);
            holder = new ViewHolder();
            holder.username = (TextView)convertView.findViewById(R.id.rc_username);
            holder.content = (TextView)convertView.findViewById(R.id.rc_content);
            holder.username.setText(data.userName);
            holder.content.setText(data.content);
            convertView.setTag(holder);
        }

        return convertView;
    }

    private class ViewHolder {
        TextView username;
        TextView content;
    }

}
