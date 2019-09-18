package financer.kuaishoudan.com.canvasdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.OverScroller
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_clear.setOnClickListener {
//          var bitmap =  view.viewToBitmap()
//            image.setImageBitmap(bitmap)
             view.reset()
        }

    }
}
