package serg.chuprin.finances.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import serg.chuprin.finances.core.api.R as CoreApiR

/**
 * Created by Sergey Chuprin on 01.04.2020.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(CoreApiR.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}