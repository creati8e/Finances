package serg.chuprin.finances.app.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import serg.chuprin.finances.R
import serg.chuprin.finances.app.di.MainComponent
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromComponent

/**
 * Created by Sergey Chuprin on 01.04.2020.
 */
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModelFromComponent { MainComponent.get() }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.userAuthorizedLiveData.observe(this, Observer { isAuthorized ->
            if (isAuthorized) {
                TODO("Navigate to dashboard screen")
            } else {
                val navController = findNavController(R.id.rootFragmentContainer)
                navController.setGraph(R.navigation.navigation_authorization)
            }
        })
    }

}