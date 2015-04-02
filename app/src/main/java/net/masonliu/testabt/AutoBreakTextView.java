package net.masonliu.testabt;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Vector;

/**
 * Created by liumeng on 4/2/15.
 */
public class AutoBreakTextView extends TextView {
    public static int m_iTextHeight; // 文本的高度
    public static int m_iTextWidth;// 文本的宽度
    private Paint mPaint = null;
    private String string = "";
    private float LineSpace = 0;// 行间距
    private int padding;

    public AutoBreakTextView(Context context, AttributeSet set) {
        super(context, set);
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        m_iTextWidth = (int) (dm.widthPixels - 2 * padding - (10 * 4 * dm.density)) + 1;
        float textSize = this.getTextSize();
        padding = this.getPaddingLeft();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(textSize);
        mPaint.setColor(Color.GRAY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        char ch;
        int w = 0;
        int istart = 0;
        int m_iFontHeight;
        int m_iRealLine = 0;
        int x = padding;
        int y = padding;
        Vector m_String = new Vector();
        Paint.FontMetrics fm = mPaint.getFontMetrics();
        m_iFontHeight = (int) Math.ceil(fm.descent - fm.top) + (int) LineSpace;// 计算字体高度（字体高度＋行间距）
        for (int i = 0; i < string.length(); i++) {
            ch = string.charAt(i);
            float[] widths = new float[1];
            String srt = String.valueOf(ch);
            mPaint.getTextWidths(srt, widths);
            if (ch == '\n') {
                m_iRealLine++;
                m_String.addElement(string.substring(istart, i));
                istart = i + 1;
                w = 0;
            } else {
                w += (int) (Math.ceil(widths[0]));
                if (w > m_iTextWidth) {
                    m_iRealLine++;
                    m_String.addElement(string.substring(istart, i));
                    istart = i;
                    i--;
                    w = 0;
                } else {
                    if (i == (string.length() - 1)) {
                        m_iRealLine++;
                        m_String.addElement(string.substring(istart,
                                string.length()));
                    }
                }
            }
        }
        for (int i = 0, j = 1; i < m_iRealLine; i++, j++) {
            canvas.drawText((String) (m_String.elementAt(i)), x, y
                    + m_iFontHeight * j, mPaint);
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredHeight = measureHeight(heightMeasureSpec);
        int measuredWidth = measureWidth(widthMeasureSpec);
        this.setMeasuredDimension(measuredWidth, measuredHeight);
        this.setLayoutParams(new LinearLayout.LayoutParams(measuredWidth, measuredHeight));

    }

    private int measureHeight(int measureSpec) {
        initHeight();
        int result = m_iTextHeight;
        return result;
    }

    private void initHeight() {
        m_iTextHeight = 0;
        Paint.FontMetrics fm = mPaint.getFontMetrics();
        int m_iFontHeight = (int) Math.ceil(fm.descent - fm.top)
                + (int) LineSpace;
        int line = 0;
        int w = 0;
        for (int i = 0; i < string.length(); i++) {
            char ch = string.charAt(i);
            float[] widths = new float[1];
            String srt = String.valueOf(ch);
            mPaint.getTextWidths(srt, widths);
            if (ch == '\n') {
                line++;
                w = 0;
            } else {
                w += (int) (Math.ceil(widths[0]));
                if (w > m_iTextWidth) {
                    line++;
                    i--;
                    w = 0;
                } else {
                    if (i == (string.length() - 1)) {
                        line++;
                    }
                }
            }
        }
        m_iTextHeight = (line) * m_iFontHeight;
    }

    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int result = 500;
        if (specMode == MeasureSpec.AT_MOST) {
            result = specSize;
        } else if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }
        return result;
    }

    public void setText(String text) {
        string = text;
        initHeight();
        invalidate();
        requestLayout();
    }
}
