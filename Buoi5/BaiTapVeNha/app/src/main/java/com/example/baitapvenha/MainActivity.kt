package com.example.baitapvenha

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.baitapvenha.screens.LoginScreen
import com.example.baitapvenha.screens.ProfileScreen
import com.example.baitapvenha.ui.theme.BaiTapVeNhaTheme
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class MainActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaiTapVeNhaTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "login_screen"
                    ) {
                        composable("login_screen") {
                            val viewModel = viewModel<SignInViewModel>()
                            val state by viewModel.state.collectAsStateWithLifecycle()

                            LaunchedEffect(key1 = Unit) {
                                googleAuthUiClient.getSignedInUser()?.let { userData ->
                                    val photoUrlEncoded = URLEncoder.encode(
                                        userData.profilePictureUrl ?: "",
                                        StandardCharsets.UTF_8.toString()
                                    )
                                    navController.navigate(
                                        "profile_screen?name=${userData.username}&email=${userData.email}&photoUrl=${photoUrlEncoded}"
                                    ) {
                                        popUpTo("login_screen") { inclusive = true }
                                    }
                                }
                            }

                            val launcher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = { result ->
                                    if (result.resultCode == RESULT_OK) {
                                        lifecycleScope.launch {
                                            val signInResult = googleAuthUiClient.signInWithIntent(
                                                intent = result.data ?: return@launch
                                            )
                                            viewModel.onSignInResult(signInResult)
                                        }
                                    }
                                }
                            )

                            LaunchedEffect(key1 = state.isSignInSuccessful) {
                                if (state.isSignInSuccessful) {
                                    Toast.makeText(
                                        applicationContext,
                                        "Sign in successful",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    val userData = state.userData
                                    val photoUrlEncoded = URLEncoder.encode(
                                        userData?.profilePictureUrl ?: "",
                                        StandardCharsets.UTF_8.toString()
                                    )

                                    navController.navigate(
                                        "profile_screen?name=${userData?.username}&email=${userData?.email}&photoUrl=${photoUrlEncoded}"
                                    ) {
                                        popUpTo("login_screen") { inclusive = true }
                                    }
                                    viewModel.resetState()
                                }
                            }

                            LoginScreen(
                                state = state,
                                onSignInClick = {
                                    lifecycleScope.launch {
                                        val signInIntentSender = googleAuthUiClient.signIn()
                                        launcher.launch(
                                            IntentSenderRequest.Builder(
                                                signInIntentSender ?: return@launch
                                            ).build()
                                        )
                                    }
                                }
                            )
                        }
                        composable(
                            route = "profile_screen?name={name}&email={email}&photoUrl={photoUrl}",
                            arguments = listOf(
                                navArgument("name") { type = NavType.StringType; nullable = true },
                                navArgument("email") { type = NavType.StringType; nullable = true },
                                navArgument("photoUrl") { type = NavType.StringType; nullable = true },
                            )
                        ) { backStackEntry ->
                            val name = backStackEntry.arguments?.getString("name")
                            val email = backStackEntry.arguments?.getString("email")
                            val photoUrl = remember(backStackEntry.arguments?.getString("photoUrl")) {
                                val url = backStackEntry.arguments?.getString("photoUrl")
                                if (url.isNullOrEmpty()) null else URLDecoder.decode(url, StandardCharsets.UTF_8.toString())
                            }

                            ProfileScreen(
                                name = name,
                                email = email,
                                photoUrl = photoUrl,
                                onSignOutClick = {
                                    lifecycleScope.launch {
                                        googleAuthUiClient.signOut()
                                        Toast.makeText(applicationContext, "Signed out", Toast.LENGTH_LONG).show()
                                        navController.navigate("login_screen") {
                                            popUpTo(navController.graph.startDestinationId) {
                                                inclusive = true
                                            }
                                        }
                                    }
                                },
                                onBackClick = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}