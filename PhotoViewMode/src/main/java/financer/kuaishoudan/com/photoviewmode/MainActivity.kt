package financer.kuaishoudan.com.photoviewmode

  import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
 import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         photo.setImageResource(R.drawable.test_pic)
    }
}
