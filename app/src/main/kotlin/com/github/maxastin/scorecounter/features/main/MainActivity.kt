package com.github.maxastin.scorecounter.features.main

import android.Manifest.permission.CAMERA
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.github.maxastin.scorecounter.common.navigation.NavigationRote
import com.github.maxastin.scorecounter.common.ui.theme.ScoreCounterTheme
import com.github.maxastin.scorecounter.features.camera.CameraScreen
import com.github.maxastin.scorecounter.features.comingsoon.view.ComingSoonScreen
import com.github.maxastin.scorecounter.features.games.view.GamesScreen
import com.github.maxastin.scorecounter.features.main.presentation.Main
import com.github.maxastin.scorecounter.features.main.presentation.MainViewModel
import com.github.maxastin.scorecounter.features.main.view.CameraIsRequiredDialog
import com.github.maxastin.scorecounter.features.welcome.view.WelcomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    private val requestCameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            mainViewModel.onAction(Main.Action.CameraPermissionAccept)
        } else {
            mainViewModel.onAction(Main.Action.CameraPermissionDeny)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setContent {
            ScoreCounterTheme {
                val state by mainViewModel.state.collectAsStateWithLifecycle()
                val navController = rememberNavController()

                LaunchedEffect(Unit) {
                    mainViewModel.event.collect {
                        when (it) {
                            is Main.Event.OpenCamera -> {
                                navController.navigate(NavigationRote.Camera)
                            }
                        }
                    }
                }

                AppContent(navController = navController)

                val onAction = remember {
                    { action: Main.Action ->
                        mainViewModel.onAction(action)
                    }
                }
                if (state.showNoCameraPermission) {
                    CameraIsRequiredDialog(onAction = onAction)
                }
            }
        }
    }

    @Composable
    private fun AppContent(navController: NavHostController) {
        Scaffold(
            modifier = Modifier
                .statusBarsPadding()
                .navigationBarsPadding()
        ) { padding ->
            MainNavigation(
                navController = navController,
                modifier = Modifier.padding(padding)
            )
        }
    }

    @Composable
    fun MainNavigation(
        navController: NavHostController,
        modifier: Modifier = Modifier,
    ) {
        NavHost(
            navController = navController,
            startDestination = NavigationRote.Welcome,
            modifier = modifier,
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            },
        ) {
            composable<NavigationRote.Welcome> {
                WelcomeScreen(navController = navController)
            }
            composable<NavigationRote.Games> {
                GamesScreen(
                    navController = navController,
                    onCameraRequired = {
                        requestCameraPermission()
                    }
                )
            }
            composable<NavigationRote.ComingSoon> { navBackStackEntry ->
                val comingSoonRote: NavigationRote.ComingSoon = navBackStackEntry.toRoute()
                ComingSoonScreen(
                    label = comingSoonRote.label,
                    navController = navController
                )
            }
            composable<NavigationRote.Camera> {
                CameraScreen(navController = navController)
            }
        }
    }

    private fun requestCameraPermission() {
        val isCameraPermissionDenied = ActivityCompat.shouldShowRequestPermissionRationale(this, CAMERA)
        if (isCameraPermissionDenied) {
            mainViewModel.onAction(Main.Action.CameraPermissionDeny)
        } else {
            requestCameraPermissionLauncher.launch(CAMERA)
        }
    }
}