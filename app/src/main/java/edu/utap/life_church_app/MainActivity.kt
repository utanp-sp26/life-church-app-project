package edu.utap.life_church_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import edu.utap.life_church_app.navigation.AppNavigation
import edu.utap.life_church_app.ui.theme.Life_church_appTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Life_church_appTheme {
                AppNavigation()
            }
        }
    }
}
