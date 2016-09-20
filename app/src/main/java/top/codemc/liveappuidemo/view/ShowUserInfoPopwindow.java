package top.codemc.liveappuidemo.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Created by xiyoumc on 9/8/16.
 */
public class ShowUserInfoPopwindow extends PopupWindow implements View.OnClickListener{

    private Context context;
    private LayoutInflater mLayoutInflater;
    private int layoutId;
    private View view;
    public ShowUserInfoPopwindow(Context context,int layoutId){
        this.context = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.layoutId = layoutId;
        init();
    }

    private void init(){
        view = mLayoutInflater.inflate(layoutId,null);
        setContentView(view);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setFocusable(true);
        setOutsideTouchable(true);
    }

    @Override
    public void onClick(View v) {

    }

    public View getPopWindowView() {
        // TODO Auto-generated method stub
        return view;
    }
}
