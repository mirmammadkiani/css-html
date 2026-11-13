package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AutoFixHigh
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.model.CodeTarget
import com.example.ui.theme.CodeTextStyle

@Composable
fun CodeEditorPane(
    html: String,
    css: String,
    activeTab: CodeTarget,
    onTabChange: (CodeTarget) -> Unit,
    onHtmlChange: (String) -> Unit,
    onCssChange: (String) -> Unit,
    onReset: () -> Unit,
    onShowSolution: (() -> Unit)? = null,
    extraQuickChips: List<String> = emptyList(),
    modifier: Modifier = Modifier
) {
    val currentText = if (activeTab == CodeTarget.HTML) html else css
    var textFieldValue by remember(activeTab) {
        mutableStateOf(TextFieldValue(text = currentText, selection = TextRange(currentText.length)))
    }

    LaunchedEffect(currentText) {
        if (textFieldValue.text != currentText) {
            val safeCursor = textFieldValue.selection.start.coerceIn(0, currentText.length)
            textFieldValue = TextFieldValue(
                text = currentText,
                selection = TextRange(safeCursor)
            )
        }
    }

    val defaultChips = remember(activeTab) {
        if (activeTab == CodeTarget.HTML) {
            listOf("<", ">", "/", "class=\"\"", "<div></div>", "<p></p>", "<a>")
        } else {
            listOf("{", "}", ":", ";", "px", "rem", "display: flex;", "gap: 12px;")
        }
    }

    val allChips = remember(extraQuickChips, defaultChips) {
        (extraQuickChips + defaultChips).distinct()
    }

    val editorSurface = MaterialTheme.colorScheme.surfaceVariant
    val editorHeader = MaterialTheme.colorScheme.surface
    val editorBorder = MaterialTheme.colorScheme.outline
    val editorText = MaterialTheme.colorScheme.onSurface
    val editorMuted = MaterialTheme.colorScheme.onSurfaceVariant

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .testTag("code_editor_pane"),
        shape = RoundedCornerShape(8.dp),
        color = editorSurface,
        border = BorderStroke(1.dp, editorBorder)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(editorHeader)
                    .padding(horizontal = 10.dp, vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        EditorTabChip(
                            label = "index.html",
                            selected = activeTab == CodeTarget.HTML,
                            onClick = { onTabChange(CodeTarget.HTML) },
                            modifier = Modifier.testTag("tab_html_editor")
                        )
                        EditorTabChip(
                            label = "style.css",
                            selected = activeTab == CodeTarget.CSS,
                            onClick = { onTabChange(CodeTarget.CSS) },
                            modifier = Modifier.testTag("tab_css_editor")
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (onShowSolution != null) {
                        IconButton(
                            onClick = onShowSolution,
                            modifier = Modifier
                                .minimumInteractiveComponentSize()
                                .testTag("button_show_solution")
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.AutoFixHigh,
                                contentDescription = "نمایش کد پاسخ استاندارد",
                                tint = editorText
                            )
                        }
                    }
                    IconButton(
                        onClick = onReset,
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .testTag("button_reset_code")
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Refresh,
                            contentDescription = "بازنشانی کد",
                            tint = editorMuted
                        )
                    }
                }
            }

            HorizontalDivider(color = editorBorder)

            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                val linesCount = remember(textFieldValue.text) {
                    maxOf(1, textFieldValue.text.count { it == '\n' } + 1)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 175.dp, max = 260.dp)
                        .padding(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(end = 10.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        for (i in 1..linesCount.coerceAtMost(14)) {
                            Text(
                                text = i.toString(),
                                style = CodeTextStyle.copy(color = editorMuted.copy(alpha = 0.7f))
                            )
                        }
                    }

                    BasicTextField(
                        value = textFieldValue,
                        onValueChange = { updated ->
                            textFieldValue = updated
                            if (activeTab == CodeTarget.HTML) {
                                onHtmlChange(updated.text)
                            } else {
                                onCssChange(updated.text)
                            }
                        },
                        textStyle = CodeTextStyle.copy(color = editorText),
                        cursorBrush = SolidColor(editorText),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("code_text_field")
                    )
                }

                HorizontalDivider(color = editorBorder)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(editorHeader)
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 10.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    allChips.forEach { token ->
                        Surface(
                            shape = RoundedCornerShape(5.dp),
                            color = editorSurface,
                            border = BorderStroke(1.dp, editorBorder),
                            modifier = Modifier
                                .clip(RoundedCornerShape(5.dp))
                                .clickable {
                                    val text = textFieldValue.text
                                    val start = textFieldValue.selection.start.coerceIn(0, text.length)
                                    val end = textFieldValue.selection.end.coerceIn(0, text.length)
                                    val minIdx = minOf(start, end)
                                    val maxIdx = maxOf(start, end)
                                    val nextText = text.substring(0, minIdx) + token + text.substring(maxIdx)
                                    val newCursor = minIdx + token.length
                                    textFieldValue = TextFieldValue(
                                        text = nextText,
                                        selection = TextRange(newCursor)
                                    )
                                    if (activeTab == CodeTarget.HTML) {
                                        onHtmlChange(nextText)
                                    } else {
                                        onCssChange(nextText)
                                    }
                                }
                        ) {
                            Text(
                                text = token,
                                style = CodeTextStyle.copy(color = editorText),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EditorTabChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .minimumInteractiveComponentSize()
            .clip(RoundedCornerShape(6.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(6.dp),
        color = if (selected) MaterialTheme.colorScheme.surfaceVariant else Color.Transparent,
        border = if (selected) BorderStroke(1.dp, MaterialTheme.colorScheme.outline) else null
    ) {
        Text(
            text = label,
            style = CodeTextStyle.copy(
                color = if (selected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
            ),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp)
        )
    }
}
