package com.example.ui.components

import android.annotation.SuppressLint
import android.graphics.Color as AndroidColor
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DesktopWindows
import androidx.compose.material.icons.rounded.PhoneAndroid
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun HtmlLivePreview(
    html: String,
    css: String,
    modifier: Modifier = Modifier,
    minHeightDp: Int = 220
) {
    var desktopMode by remember { mutableStateOf(false) }

    val fullDocument = remember(html, css, desktopMode) {
        buildHtmlDocument(html = html, css = css, desktopMode = desktopMode)
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .testTag("live_preview_container"),
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        tonalElevation = 0.dp
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 12.dp, vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (desktopMode) "پیش‌نمایش • دسکتاپ 640px" else "پیش‌نمایش • موبایل",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                IconButton(
                    onClick = { desktopMode = !desktopMode },
                    modifier = Modifier
                        .minimumInteractiveComponentSize()
                        .testTag("toggle_viewport_mode")
                ) {
                    Icon(
                        imageVector = if (desktopMode) Icons.Rounded.PhoneAndroid else Icons.Rounded.DesktopWindows,
                        contentDescription = if (desktopMode) "تغییر به نمای موبایل" else "تغییر به نمای دسکتاپ",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.outline)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(minHeightDp.dp)
                    .background(Color.White)
            ) {
                AndroidView(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("html_webview"),
                    factory = { context ->
                        WebView(context).apply {
                            setBackgroundColor(AndroidColor.WHITE)
                            settings.apply {
                                javaScriptEnabled = false
                                allowFileAccess = false
                                allowContentAccess = false
                                cacheMode = WebSettings.LOAD_NO_CACHE
                                builtInZoomControls = false
                                displayZoomControls = false
                                useWideViewPort = true
                                loadWithOverviewMode = true
                            }
                        }
                    },
                    update = { webView ->
                        webView.settings.useWideViewPort = desktopMode
                        webView.settings.loadWithOverviewMode = desktopMode
                        webView.loadDataWithBaseURL(
                            "https://preview.local/",
                            fullDocument,
                            "text/html",
                            "UTF-8",
                            null
                        )
                    }
                )
            }
        }
    }
}

private fun buildHtmlDocument(html: String, css: String, desktopMode: Boolean): String {
    val viewportContent = if (desktopMode) {
        "width=640, initial-scale=0.55"
    } else {
        "width=device-width, initial-scale=1.0"
    }
    val baseResetCss = """
        *, *::before, *::after { box-sizing: border-box; }
        body { margin: 12px; font-family: sans-serif; color: #0f172a; line-height: 1.7; }
    """.trimIndent()
    val styleBlock = "<style>\n$baseResetCss\n$css\n</style>"
    val metaViewport = "<meta name=\"viewport\" content=\"$viewportContent\">"

    val trimmed = html.trim()
    return if (trimmed.contains("<html", ignoreCase = true)) {
        var doc = trimmed
        if (!doc.contains("viewport", ignoreCase = true)) {
            doc = if (doc.contains("</head>", ignoreCase = true)) {
                doc.replaceFirst("</head>", "$metaViewport\n$styleBlock\n</head>", ignoreCase = true)
            } else {
                doc.replaceFirst("<body", "<head>$metaViewport\n$styleBlock</head>\n<body", ignoreCase = true)
            }
        } else if (doc.contains("</head>", ignoreCase = true)) {
            doc = doc.replaceFirst("</head>", "$styleBlock\n</head>", ignoreCase = true)
        }
        doc
    } else {
        """
        <!DOCTYPE html>
        <html lang="fa" dir="rtl">
        <head>
          <meta charset="UTF-8">
          $metaViewport
          $styleBlock
        </head>
        <body>
          $html
        </body>
        </html>
        """.trimIndent()
    }
}
