package financer.kuaishoudan.com.canvasdemo

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * Create by Xiangshifu
 * on 2019/2/13 6:02 PM
 */

class LayerView(context: Context?) : View(context) {

    constructor(context: Context?,attributes : AttributeSet?) : this(context)

    constructor(context: Context?,attributes : AttributeSet?,defStyleAttr : Int?) :this(context,attributes)

    override  fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        var paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color=Color.GREEN;
        if(canvas != null){
            canvas.translate(measuredWidth/2.0f,measuredHeight/2.0f)
            var rectF : RectF = RectF(0f,0f,100f,100f)
            canvas.drawRect(rectF,paint)

            canvas.save()
            canvas.translate(100f,0f)
            paint.color = Color.RED
            canvas.drawRect(rectF,paint)
            canvas.restore()

            paint.color = Color.BLUE
            canvas.drawRect(0f,0f,50f,50f,paint)

            var matrix : Matrix = Matrix()
            matrix.mapPoints(floatArrayOf(1.0f,2.0f,3.0f))

        }

    }

}