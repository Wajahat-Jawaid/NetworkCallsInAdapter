package com.wajahat.networkcallsinadapter.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.TypedValue
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.wajahat.networkcallsinadapter.R
import com.wajahat.networkcallsinadapter.data.model.PostAdapterModel

class VerticalItemDecoration(private val context: Context, height: Int) : RecyclerView.ItemDecoration() {

    private val dividerHeight: Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, height.toFloat(), context.resources.displayMetrics
    ).toInt()

    private val paint: Paint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.separator)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        for (i in 0 until parent.childCount) {
            val view = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(view)
            parent.adapter?.getItemViewType(position).let { viewType ->
                // If view type is of item, only then add the separator because adding the separator under
                // heading does not look good
                if (viewType == PostAdapterModel.TYPE_ITEM) {
                    c.drawRect(
                        view.left.toFloat(),
                        view.bottom.toFloat(),
                        view.right.toFloat(),
                        (view.bottom + dividerHeight).toFloat(),
                        paint
                    )
                }
            }
        }
    }
}