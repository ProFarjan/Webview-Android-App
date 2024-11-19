package com.ecommarce.w7ds

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.ecommarce.w7ds.ui.theme.W7dsTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            W7dsTheme {
                WebViewWithLimitedLoadingIndicator(url = "https://w7ds.com/")
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewWithLimitedLoadingIndicator(url: String) {
    var isLoading by remember { mutableStateOf(true) } // Track loading state

    // Automatically hide the loading indicator after 5 seconds
    LaunchedEffect(Unit) {
        delay(5000) // Wait for 5 seconds
        isLoading = false
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // WebView Composable
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                WebView(context).apply {
                    webViewClient = object : WebViewClient() {
                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            isLoading = false // Hide loading indicator when the page finishes
                        }
                    }
                    webChromeClient = WebChromeClient() // Optional for JavaScript dialogs
                    settings.javaScriptEnabled = true
                    loadUrl(url)
                }
            }
        )

        // Loading Indicator
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color.Blue,
                    modifier = Modifier.size(50.dp)
                )
            }
        }
    }
}
