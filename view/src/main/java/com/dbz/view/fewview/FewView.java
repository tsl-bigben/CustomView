package com.dbz.view.fewview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.dbz.view.R;
import com.dbz.view.util.Utils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class FewView extends View {

    private static final String TAG = FewView.class.getName();
    private Context mContext;
    private List<FewBean> fewBeans = new ArrayList<>();
    private int viewWidth;
    private int viewHeight;
    private float mDownX;
    private float mDownY;
    private float mUpX;
    private float mUpY;
    private OnItemClickListener onItemClickListener;

    private int line_height = 0;
    //文字画笔
    private Paint mTextPaint;
    //更多提示文字画笔
    private Paint mMoreTextPaint;
    //线条画笔
    private Paint mLinePaint;
    //文字大小
    private int mTextSize;
    //更多提示文字大小
    private int mMoreTxtSize;
    //文字颜色
    private int mTextColor;
    //文字颜色
    private int mMoreTextColor;
    //图片左边距
    private int iconPaddingLeft;
    //文字左边距
    private int textPaddingLeft;
    //更多按钮右边距
    private int moreIconPaddingRight;
    //更多提示文字右边距
    private int moreTxtPaddingRight;
    //线条颜色
    private int mLineColor;
    //是否显示分割线
    private boolean showLine;
    //图片宽度
    private int imgWidth;
    //图片高度
    private int imgHeight;

    private Bitmap bitmap;
    private Bitmap moreIcon;

    public FewView(Context context) {
        this(context, null);
    }

    public FewView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FewView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initAttr(context, attrs);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FewView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttr(context, attrs);
        init();
    }

    @SuppressLint("CustomViewStyleable")
    private void initAttr(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.fewListView);
        if (typedArray != null) {
            mTextSize = typedArray.getDimensionPixelSize(R.styleable.fewListView_few_list_textSize, 14);
            mMoreTxtSize = typedArray.getDimensionPixelSize(R.styleable.fewListView_few_list_text_more_size, 15);

            mTextColor = typedArray.getColor(R.styleable.fewListView_few_list_textColor, Color.BLACK);
            mMoreTextColor = typedArray.getColor(R.styleable.fewListView_few_list_moreTextColor, Color.BLACK);
            mLineColor = typedArray.getColor(R.styleable.fewListView_few_list_lineColor, Color.BLACK);

            iconPaddingLeft = typedArray.getDimensionPixelSize(R.styleable.fewListView_few_list_icon_padding_left, 20);
            textPaddingLeft = typedArray.getDimensionPixelSize(R.styleable.fewListView_few_list_text_padding_left, 20);
            moreIconPaddingRight = typedArray.getDimensionPixelSize(R.styleable.fewListView_few_list_more_icon_padding_right, 11);
            moreTxtPaddingRight = typedArray.getDimensionPixelSize(R.styleable.fewListView_few_list_more_txt_padding_right, 17);

            imgWidth = typedArray.getDimensionPixelSize(R.styleable.fewListView_few_list_img_width, 50);
            imgHeight = typedArray.getDimensionPixelSize(R.styleable.fewListView_few_list_img_height, 50);
            //moreIconPaddingTop = typedArray.getDimensionPixelSize(R.styleable.fewListView_few_list_more_icon_padding_top, 11);
            //moreTxtPaddingTop = typedArray.getDimensionPixelSize(R.styleable.fewListView_few_list_more_txt_padding_top, 11);

            line_height = typedArray.getDimensionPixelSize(R.styleable.fewListView_few_list_line_height, 42);
            showLine = typedArray.getBoolean(R.styleable.fewListView_few_list_show_line, false);
            typedArray.recycle();
        }
    }

    private void init() {
        mTextPaint = new Paint();
        mMoreTextPaint = new Paint();
        mLinePaint = new Paint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setStrokeWidth(0.5f);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mTextColor);

        mMoreTextPaint.setTextSize(mMoreTxtSize);
        mMoreTextPaint.setStrokeWidth(1);
        mMoreTextPaint.setStyle(Paint.Style.FILL);
        mMoreTextPaint.setAntiAlias(true);
        mMoreTextPaint.setColor(mMoreTextColor);

        mLinePaint.setColor(mLineColor);
        mLinePaint.setStrokeWidth(1f);
        mLinePaint.setStyle(Paint.Style.FILL);
        mLinePaint.setTextSize(mTextSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = getMeasuredWidth();
        viewHeight = getMeasuredHeight();
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = getWidth();//获得画布宽度
        viewHeight = getHeight();//获得画布高度
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (fewBeans != null && fewBeans.size() > 0) {
            Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
            Paint.FontMetrics moreFontMetrics = mMoreTextPaint.getFontMetrics();
            float textHeight = Math.abs(fontMetrics.ascent + fontMetrics.descent);
            float moreTextHeight = Math.abs(moreFontMetrics.ascent + moreFontMetrics.descent);
            for (int i = 0; i < fewBeans.size(); i++) {
                float left = 0;
                float right = 0;
                FewBean fewBean = fewBeans.get(i);
                //绘制图标
                if (fewBean.getIconRes() != -1) {
                    bitmap = Utils.RFile2Bitmap(getResources(), fewBean.getIconRes());
                    if (bitmap != null) {
                        left += iconPaddingLeft;
                        canvas.drawBitmap(bitmap, iconPaddingLeft, line_height * 1.00f / 2 - bitmap.getHeight() * 1.00f / 2 + line_height * i, mTextPaint);
                        left += bitmap.getWidth();
                    }
                    left += textPaddingLeft;//图片不为空则是图片的左边距加上图片的宽度加上文字的左边距
                } else {
                    left += iconPaddingLeft;//如果图片为空直接使用图片的左边距
                }
                //绘制文字
                float top = line_height * 1.0f / 2 + textHeight / 2 + line_height * i;
                canvas.drawText(fewBean.getTitle(), left, top, mTextPaint);
                //绘制更多
                if (fewBean.isMore()) {
                    right = moreIconPaddingRight;
                    moreIcon = Utils.RFile2Bitmap(getResources(), fewBean.getMoreIconRes());
                    if (moreIcon != null) {
                        right += moreIcon.getWidth();//右边距加上更多图片的宽度
                        canvas.drawBitmap(moreIcon, viewWidth - right, line_height * 1.00f / 2 - moreIcon.getHeight() * 1.00f / 2 + line_height * i, mTextPaint);
                        right += moreTxtPaddingRight;
                    }
                } else {
                    right = moreIconPaddingRight;
                }
                //绘制更多提示文字
                if (fewBean.isShowMoreText()) {
                    //文字的宽度
                    float strWidth = mMoreTextPaint.measureText(fewBean.getMoreText());
                    right += strWidth;
                    canvas.drawText(fewBean.getMoreText(), viewWidth - right, line_height * 1.00f / 2 + moreTextHeight / 2 + line_height * i, mMoreTextPaint);
                } else {
                    //drawImgUrl(canvas, right, line_height, i, fewBean.getMoreText());
                }
                //绘制分割线
                if (showLine && i < fewBeans.size() - 1) {
                    canvas.drawLine(0, line_height * (i + 1), viewWidth, line_height * (i + 1), mLinePaint);
                }
            }
        }
    }

    public void setResource(List<FewBean> fewBeans) {
        this.fewBeans = fewBeans;
        postInvalidate();
    }

    public List<FewBean> getFewBeans() {
        return fewBeans;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                mUpX = event.getX();
                mUpY = event.getY();
                if (Utils.getDoublePointDistance(mDownX, mDownY, mUpX, mUpY) < 10) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick((int) (mUpY / line_height) + (mUpY % line_height > 0 ? 0 : -1));
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private int measureHeight(int measureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = fewBeans.size() * line_height + 1;
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;

    }

    private int measureWidth(int measureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = 75;//根据自己的需要更改
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        bitmapRecycle();
    }

    /**
     * 销毁图片
     */
    private void bitmapRecycle() {
        if (bitmap != null) {
            bitmap.recycle();
        }
        if (moreIcon != null) {
            moreIcon.recycle();
        }
    }

}
