package top.codemc.liveappuidemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by xiyoumc on 9/12/16.
 */
public class GiftListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<GiftItem> mGiftItemList;

    public GiftListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mGiftItemList = new LinkedList<>();
    }

    public void addItem(GiftItem item) {
        mGiftItemList.add(item);
        notifyDataSetChanged();
    }

    public void addAllItem(List<GiftItem> list) {
        mGiftItemList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (null != mGiftItemList) {
            return mGiftItemList.size();
        } else {
            return 0;
        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return mGiftItemList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.gird_view_item, null);
            viewHolder = new ViewHolder();
            viewHolder.giftName = (TextView) convertView.findViewById(R.id.gift_name);
            viewHolder.giftDesc = (TextView) convertView.findViewById(R.id.gift_price);
            viewHolder.giftImg = (ImageView) convertView.findViewById(R.id.gift_img);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GiftItem giftItem = mGiftItemList.get(position);
        viewHolder.giftName.setText(giftItem.getGiftName());
        viewHolder.giftDesc.setText(giftItem.getDesc());
        // TODO: 9/12/16

        return convertView;
    }

    class ViewHolder {
        public ImageView giftImg;
        public TextView giftName;
        public TextView giftDesc;
    }
}
