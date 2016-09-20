package top.codemc.liveappuidemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class ArrayListFragment extends Fragment {

    int number;
    static GiftListAdapter adapter;
    GridView mGridView;

    static ArrayListFragment newInstance(int number) {
        ArrayListFragment f = new ArrayListFragment();
        Log.d("ArrayListFragment", "newInstance");
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("number", number);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("ArrayListFragment", "onCreate");
        number = getArguments() != null ? getArguments().getInt("number") : 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("ArrayListFragment", "onCreate");
        View view = inflater.inflate(R.layout.fragment_pager_list, container, false);
        mGridView = (GridView) view.findViewById(R.id.myList);
        mGridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long id) {
                Toast.makeText(getActivity(), GirdViewActivity.CHESSES.get(position), Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: 9/12/16 分页处理数据
        Log.d("ArrayListFragment", "onCreate");
        adapter = new GiftListAdapter(getActivity());
        GiftItem giftItem = new GiftItem();
        giftItem.setDesc("aaa");
        giftItem.setGiftIconUrl("fasdfsdf");
        giftItem.setGiftName("游艇");
        adapter.addItem(giftItem);
        mGridView.setAdapter(adapter);
    }
}
