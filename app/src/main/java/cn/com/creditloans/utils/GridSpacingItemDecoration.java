package cn.com.creditloans.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.scwang.smartrefresh.layout.util.DensityUtil;

/**
 * Created by apple on 2018/7/17.
 */

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration  {
    private int spanCount;
    private int dividerWidth;
    private int dividerWidthTop;
    private int dividerWidthBot;

    private Paint dividerPaint;

    /**
     * @param spanCount gridLayoutManager 列数
     * @param dividerWidthDp 分割块宽高,单位:dp
     */
    public GridSpacingItemDecoration(Context context, int spanCount, int dividerWidthDp) {
        this.spanCount = spanCount;

        this.dividerPaint = new Paint();
        this.dividerPaint.setColor(Color.BLUE);

        this.dividerWidth = DensityUtil.dp2px(dividerWidthDp);
        this.dividerWidthTop = dividerWidth / 2;
        this.dividerWidthBot = dividerWidth - dividerWidthTop;
    }

    @Override
    public void getItemOffsets(Rect outRect, View child, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, child, parent, state);

        int pos = parent.getChildAdapterPosition(child);
        int column = (pos) % spanCount;// 计算这个child 处于第几列


        outRect.top = dividerWidthTop;
        outRect.bottom = dividerWidthBot;

        outRect.left = (column * dividerWidth / spanCount);
        outRect.right = dividerWidth - (column + 1) * dividerWidth / spanCount;

        Log.e("getItemOffsets", "pos=" + pos + ", column=" + column + " , left=" + outRect.left + ", right="
                + outRect.right + ", dividerWidth=" + dividerWidth);
    }

}
