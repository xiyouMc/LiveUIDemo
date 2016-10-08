package top.codemc.liveappuidemo.view.likeanimationlayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

import top.codemc.liveappuidemo.R;

/**
 * Created by Administrator on 2016/1/20.
 */
public class LikeAnimationLayout extends RelativeLayout {

    private static final String TAG = LikeAnimationLayout.class.getSimpleName();

    private LikeClickListener mLikeClickListener;

    private static final int[] ANIM_RES_IDS = new int[]{
            R.drawable.vivasam_play_like_01,
            R.drawable.vivasam_play_like_02,
            R.drawable.vivasam_play_like_03,
            R.drawable.vivasam_play_like_04,
            R.drawable.vivasam_play_like_05,
            R.drawable.vivasam_play_like_06,
            R.drawable.vivasam_play_like_07,
            R.drawable.vivasam_play_like_08,
            R.drawable.vivasam_play_like_09,
    };

    public void setLikeClickListener(LikeClickListener listener) {
        mLikeClickListener = listener;
    }

    public interface LikeClickListener {
        public void onLikeClick();
    }

    private Random random = new Random();
    private Interpolator interpolator = new LinearInterpolator();

    private ImageView mBtnLike;

    private int mLayoutWidth, mLayoutHeight;

    public LikeAnimationLayout(Context context) {
        super(context);
        inflaterLayout();
    }

    public LikeAnimationLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflaterLayout();
    }

    public LikeAnimationLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflaterLayout();
    }

    private void inflaterLayout() {
        LayoutInflater.from(getContext()).inflate(R.layout.sam_like_anim_layout, this, true);

        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);

        mLayoutWidth = wm.getDefaultDisplay().getWidth();
        mLayoutHeight = wm.getDefaultDisplay().getHeight();
        mBtnLike = (ImageView) findViewById(R.id.btn_like);
        mBtnLike.setOnClickListener(mOnClickListener);
    }

    private OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {

            if (null != mLikeClickListener) {
                mLikeClickListener.onLikeClick();
            }
            addFavor();
        }
    };

    private AnimatorSet getEnterAnimtor(final View target) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, View.ALPHA, 0.2f, 1f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, View.SCALE_X, 0.2f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, View.SCALE_Y, 0.2f, 1f);
        AnimatorSet enter = new AnimatorSet();
        enter.setDuration(500);
        enter.setInterpolator(new LinearInterpolator());
        enter.playTogether(alpha, scaleX, scaleY);
        enter.setTarget(target);
        return enter;
    }

    private ValueAnimator getBezierValueAnimator(View target, int drawableWidth) {
        PointF offset1 = getPointF(2, drawableWidth);
        PointF offset2 = getPointF(1, drawableWidth);
        BezierEvaluator evaluator = new BezierEvaluator(offset1, offset2);

        int loc[] = new int[2];
        mBtnLike.getLocationOnScreen(loc);
        PointF start = new PointF();
        start.x = loc[0];
        start.y = loc[1];
        PointF end = new PointF();
        end.x = random.nextInt(mLayoutWidth / 3 - drawableWidth) + mLayoutWidth * 2 / 3;
        end.y = mLayoutHeight / 3;
        ValueAnimator animator = ValueAnimator.ofObject(evaluator, start, end);
        Log.i(TAG, "start point " + start.toString());
        Log.i(TAG, "offset1 point " + offset1.toString());
        Log.i(TAG, "offset2 point " + offset2.toString());
        Log.i(TAG, "end point " + end.toString());
        animator.addUpdateListener(new BezierListenr(target));
        animator.setTarget(target);
        animator.setDuration(3000);
        return animator;
    }

    private PointF getPointF(int scale, int drawableWidth) {
        PointF pointF = new PointF();
        int stepXMax = mLayoutWidth / 3 - drawableWidth;
        int stepYMax = mLayoutHeight / 3 / 4;
        pointF.x = random.nextInt(stepXMax) + mLayoutWidth * 2 / 3;
        pointF.y = random.nextInt(stepYMax) + mLayoutHeight / 3 + mLayoutHeight * 2 * scale / 3 / 4;
        return pointF;
    }

    private class BezierListenr implements ValueAnimator.AnimatorUpdateListener {

        private View target;

        public BezierListenr(View target) {
            this.target = target;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            PointF pointF = (PointF) animation.getAnimatedValue();
            target.setX(pointF.x);
            target.setY(pointF.y);
            target.setAlpha(1 - animation.getAnimatedFraction());
        }
    }

    public void addFavor() {
        ImageView imageView = new ImageView(getContext());
        Drawable drawable = getRandomAnimDrawable();
        int drawableWidth = drawable.getIntrinsicWidth();
        int drawableHeight = drawable.getIntrinsicHeight();
        imageView.setImageDrawable(drawable);

        int[] loc = new int[2];
        mBtnLike.getLocationOnScreen(loc);
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.leftMargin = loc[0] + (mBtnLike.getWidth() - drawableWidth) / 2;
        lp.topMargin = loc[1] - mBtnLike.getWidth() / 2;
        addView(imageView, lp);
        Log.v(TAG, "add后子view数:" + getChildCount());

        Animator set = getAnimator(imageView, drawableWidth);
        set.addListener(new AnimEndListener(imageView));
        set.start();
    }

    private class AnimEndListener extends AnimatorListenerAdapter {
        private View target;

        public AnimEndListener(View target) {
            this.target = target;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            //因为不停的add 导致子view数量只增不减,所以在view动画结束后remove掉
            removeView((target));
            Log.v(TAG, "removeView后子view数:" + getChildCount());
        }
    }

    private Animator getAnimator(View target, int drawableWidth) {
        AnimatorSet set = getEnterAnimtor(target);

        ValueAnimator bezierValueAnimator = getBezierValueAnimator(target, drawableWidth);

        AnimatorSet finalSet = new AnimatorSet();
        finalSet.playSequentially(set);
        finalSet.playSequentially(set, bezierValueAnimator);
        finalSet.setInterpolator(interpolator);
        finalSet.setTarget(target);
        return finalSet;
    }

    private Drawable getRandomAnimDrawable() {
        Random random = new Random();
        int index = random.nextInt(9);
        Drawable drawable = getContext().getResources().getDrawable(ANIM_RES_IDS[index]);
        return drawable;
    }
}
