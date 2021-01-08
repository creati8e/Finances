package serg.chuprin.finances.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import serg.chuprin.finances.core.api.presentation.navigation.RootNavigator

/**
 * Created by Sergey Chuprin on 01.04.2020.
 */
class MainActivity : AppCompatActivity(), RootNavigator {

    override val navController: NavController
        get() = findNavController(R.id.rootFragmentContainer)

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onSupportNavigateUp(): Boolean = navController.navigateUp()

}