package com.szntest.testrouter

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.sunset.common.router.annotation.Route

/**
 * Created by on 2021/3/2 13:40
 * @author szn
 * 说明：
 */

@Route("/app/test1")
class Test1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val title = intent.getStringExtra("title")
        val index = intent.getIntExtra("Index", 0)
        val text = intent.getStringExtra("text")


        findViewById<TextView>(R.id.textView).text = "$title $index \n $text"
    }

    override fun onBackPressed() {
        val data = Intent()
        data.putExtra("result", "从Test1Activity返回")
        setResult(RESULT_OK, data)
        super.onBackPressed()

    }

}