package ir.androidcourse.samangi.bmicalculator

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragment = Programme()
        val fmTransaction = supportFragmentManager.beginTransaction()
        fmTransaction.replace(R.id.ToReplace, fragment)
        fmTransaction.commit()
    }
}