package top.codemc.liveappuidemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import java.util.List;

/**
 * �Զ���Ŀ��Դ�ֱ���򻬶���ViewPager
 *
 * @author ����
 * @version ����ʱ�䣺2015��4��1��  ����10:29:24
 */
public class VerticalViewPager extends ViewGroup {

    // ���ڻ�������
    private Scroller scroller;
    // �������ٴ����ٶȵ���
    private VelocityTracker velocityTracker;
    // ��ǰ����Ļ��ͼ
    private int curScreen;
    // Ĭ�ϵ���ʾ��ͼ
    private int defaultScreen = 2;
    // ���¼���״̬
    private static final int TOUCH_STATE_REST = 0;
    // �����϶���״̬
    private static final int TOUCH_STATE_SCROLLING = 1;
    // �����л�ҳ�����С�������ٶ�
    private static final int SNAP_VELOCITY = 500;
    // ������״̬
    private int touchState = TOUCH_STATE_REST;
    private int touchSlop;
    private float lastMotionY;

    private OnVerticalPageChangeListener verticalPageChangeListener;

    public VerticalViewPager(Context context) {
        this(context, null);
    }

    public VerticalViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * �ڹ������н���һЩ��ʼ��
     */
    public VerticalViewPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        scroller = new Scroller(context);
        curScreen = defaultScreen;
        // ViewConfiguration.get(getContext()).getScaledTouchSlop()���Եõ�һ�����룬ViewPager����������������ж��û��Ƿ�ҳ
        touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop() / 4;
    }

    public void setCurScreen(int curScreen) {
        this.curScreen = curScreen;
    }

    public interface OnVerticalPageChangeListener {
        public void onVerticalPageSelected(int position);
    }

    /**
     * �ṩһ��ҳ��ı�ļ�����
     */
    public void setOnVerticalPageChangeListener(OnVerticalPageChangeListener onVerticalPageChangeListener) {
        this.verticalPageChangeListener = onVerticalPageChangeListener;
    }

    public void setViewList(List<View> viewList) {
        for (int i = 0; i < viewList.size(); i++) {
            this.addView(viewList.get(i));
        }
        invalidate();
    }

    /**
     * ��д�˷���Ϊ��View���в���
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childheiht = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() != View.GONE) {
                int childWidth = childView.getMeasuredWidth();
                childView.layout(0, childheiht, childWidth,
                        childView.getMeasuredHeight() + childheiht);
                childheiht += childView.getMeasuredHeight();
            }
        }
    }

    /**
     * ��д�˷�����������߶ȺͿ��
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // �õ�����ҳ(��View)���������ǵĿ�͸�
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    /**
     * ����Ŀǰ��λ�ù�������һ����ͼλ��
     */
    public void snapToDestination() {
        int screenHeight = getHeight();
        // ����View�ĸ߶��Լ�������ֵ���ж����ĸ�View
        int destScreen = (getScrollY() + screenHeight / 2) / screenHeight;
        snapToScreen(destScreen);
    }

    public void snapToScreen(int whichScreen) {
        whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
        if (getScrollY() != (whichScreen * getHeight())) {

            final int delta = whichScreen * getHeight() - getScrollY();
            scroller.startScroll(0, getScrollY(), 0, delta,
                    Math.abs(delta) / 4);
            curScreen = whichScreen;
            invalidate(); // ���²���
            if (verticalPageChangeListener != null)
                verticalPageChangeListener.onVerticalPageSelected(whichScreen);
        }
    }

    public void setToScreen(int whichScreen) {
        whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
        curScreen = whichScreen;
        scrollTo(0, whichScreen * getHeight());
        if (verticalPageChangeListener != null)
            verticalPageChangeListener.onVerticalPageSelected(whichScreen);
    }

    /**
     * ��ȡ��ǰҳ��
     *
     * @return ��ǰҳ��ֵ
     */
    public int getCurScreen() {
        return curScreen;
    }

    /**
     * ��ȡ��ǰ��ͼ
     *
     * @return ��ǰ��ͼ
     */
    public View getCurrentView() {
        return getChildAt(getCurScreen());
    }

    /**
     * ����λ�û�ȡָ��ҳ�����ͼ
     *
     * @param position ҳ��λ��
     * @return ָ��ҳ�����ͼ
     */
    public View getView(int position) {
        return getChildAt(position);
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (velocityTracker == null) {
            // ʹ��obtain�����õ�VelocityTracker��һ������
            velocityTracker = VelocityTracker.obtain();
        }
        // ����ǰ�Ĵ����¼����ݸ�VelocityTracker����
        velocityTracker.addMovement(event);
        // �õ������¼�������
        final int action = event.getAction();
        final float y = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                }
                lastMotionY = y;
                break;

            case MotionEvent.ACTION_MOVE:
                int deltay = (int) (lastMotionY - y);
                lastMotionY = y;
                scrollBy(0, deltay);
                break;

            case MotionEvent.ACTION_UP:
                final VelocityTracker velocityTracker = this.velocityTracker;
                // ���㵱ǰ���ٶ�
                velocityTracker.computeCurrentVelocity(1000);
                // ���Y�᷽��ǰ���ٶ�
                int velocityY = (int) velocityTracker.getYVelocity();

                if (velocityY > SNAP_VELOCITY && curScreen > 0) {
                    snapToScreen(curScreen - 1);
                } else if (velocityY < -SNAP_VELOCITY && curScreen < getChildCount() - 1) {
                    snapToScreen(curScreen + 1);
                } else {
                    snapToDestination();
                }

                if (this.velocityTracker != null) {
                    this.velocityTracker.recycle();
                    this.velocityTracker = null;
                }
                touchState = TOUCH_STATE_REST;
                break;
            case MotionEvent.ACTION_CANCEL:
                touchState = TOUCH_STATE_REST;
                break;
        }
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        if ((action == MotionEvent.ACTION_MOVE)
                && (touchState != TOUCH_STATE_REST)) {
            return true;
        }

        final float y = ev.getY();
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                final int xDiff = (int) Math.abs(lastMotionY - y);
                if (xDiff > touchSlop) {
                    touchState = TOUCH_STATE_SCROLLING;
                }
                break;

            case MotionEvent.ACTION_DOWN:
                lastMotionY = y;
                touchState = scroller.isFinished() ? TOUCH_STATE_REST
                        : TOUCH_STATE_SCROLLING;
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                touchState = TOUCH_STATE_REST;
                break;
        }
        return touchState != TOUCH_STATE_REST;
    }
}
