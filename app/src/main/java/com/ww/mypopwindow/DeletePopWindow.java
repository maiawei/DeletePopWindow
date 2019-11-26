package com.ww.mypopwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DeletePopWindow extends PopupWindow implements View.OnClickListener {
    ImageView topTriangle;
    ImageView bottomTriangle;
    LinearLayout firstLayout;
    LinearLayout secondLayout ,rootLayout;
    RelativeLayout containerView;

    private Window window;
    private WindowManager.LayoutParams layoutParams;
    private Context context;
    private int heightY;
    private boolean isShowUp;

    public void showPop(View target) {
        heightY = calculatePopWindowLocation(target, rootLayout);
        if (isShowUp) {
            setAnimationStyle(R.style.deletePopWindowFromBottom);
            containerView.setGravity(Gravity.BOTTOM);
            showAtLocation(target, Gravity.BOTTOM, 0, heightY);
        } else {
            setAnimationStyle(R.style.deletePopWindowFromTop);
            containerView.setGravity(Gravity.TOP);
            showAtLocation(target, Gravity.TOP, 0, heightY);
        }
    }


    private void initSetting() {
        View rootView = LayoutInflater.from(context).inflate(R.layout.delete_news_layout, null);
        setContentView(rootView);
        topTriangle=rootView.findViewById(R.id.topTriangle);
        bottomTriangle=rootView.findViewById(R.id.bottomTriangle);
        firstLayout=rootView.findViewById(R.id.firstLayout);
        secondLayout=rootView.findViewById(R.id.secondLayout);
        rootLayout=rootView.findViewById(R.id.rootLayout);
        containerView=rootView.findViewById(R.id.containerView);
        containerView.setOnClickListener(this);
        rootView.findViewById(R.id.keywordLayout).setOnClickListener(this);
        rootView.findViewById(R.id.returnLayout).setOnClickListener(this);
        rootView.findViewById(R.id.noInterestLayout).setOnClickListener(this);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(ScreenUtils.getScreenWidth());
        window = ((Activity) context).getWindow();
        layoutParams = window.getAttributes();
        layoutParams.alpha = 0.6f;
        window.setAttributes(layoutParams);
        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void showSecondLayout() {
        Animation secondShow = AnimationUtils.loadAnimation(context, R.anim.in_from_right);
        Animation firstHidden = AnimationUtils.loadAnimation(context, R.anim.out_from_left);
        firstLayout.startAnimation(firstHidden);
        firstLayout.setVisibility(View.GONE);
        secondLayout.startAnimation(secondShow);
        secondLayout.setVisibility(View.VISIBLE);

    }

    private void showFirstLayout() {
        Animation firstShow = AnimationUtils.loadAnimation(context, R.anim.in_from_left);
        Animation secondHidden = AnimationUtils.loadAnimation(context, R.anim.out_from_right);
        secondLayout.startAnimation(secondHidden);
        secondLayout.setVisibility(View.GONE);
        firstLayout.startAnimation(firstShow);
        firstLayout.setVisibility(View.VISIBLE);

    }

    private int calculatePopWindowLocation(final View target, final View contentView) {
        int[] contentViewLocation = new int[2];
        int[] targetLocation = new int[2];
        target.getLocationInWindow(targetLocation);
        int targetHeight = target.getHeight();
        int screenHeight = ScreenUtils.getScreenHeight();
        isShowUp = (targetLocation[1] > screenHeight / 2);
        RelativeLayout.LayoutParams topParams = (RelativeLayout.LayoutParams) topTriangle.getLayoutParams();
        RelativeLayout.LayoutParams bottomParams = (RelativeLayout.LayoutParams) bottomTriangle.getLayoutParams();
        if (isShowUp) {
            bottomParams.leftMargin = targetLocation[0];
            topTriangle.setVisibility(View.GONE);
            contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            contentViewLocation[1] = screenHeight - targetLocation[1];
        } else {
            topParams.leftMargin = targetLocation[0];
            bottomTriangle.setVisibility(View.GONE);
            contentViewLocation[1] = targetLocation[1] + targetHeight;
        }
        return contentViewLocation[1];
    }

    public DeletePopWindow(Context context) {
        this(context, null);
    }

    private DeletePopWindow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private DeletePopWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initSetting();
    }


    @Override
    public void dismiss() {
        layoutParams.alpha = 1.0f;
        window.setAttributes(layoutParams);
        super.dismiss();
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.keywordLayout) {
            showSecondLayout();
        } else if (view.getId() == R.id.returnLayout) {
            showFirstLayout();
        } else {
            dismiss();
        }
    }
}
