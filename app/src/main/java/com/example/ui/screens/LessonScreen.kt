package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.CurriculumRepository
import com.example.model.LessonStep
import com.example.model.ValidationResult
import com.example.ui.components.CodeEditorPane
import com.example.ui.components.HtmlLivePreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonScreen(
    lessonId: String,
    completedSteps: Set<String>,
    onValidateStep: (LessonStep, String, String) -> ValidationResult,
    onBack: () -> Unit,
    onOpenNextLesson: (String) -> Unit
) {
    val lesson = remember(lessonId) { CurriculumRepository.findLesson(lessonId) }
    if (lesson == null) {
        onBack()
        return
    }

    var currentStepIndex by remember(lessonId) { mutableIntStateOf(0) }
    val currentStep = lesson.steps[currentStepIndex.coerceIn(0, lesson.steps.lastIndex)]

    var htmlCode by remember(currentStep.id) { mutableStateOf(currentStep.starterHtml) }
    var cssCode by remember(currentStep.id) { mutableStateOf(currentStep.starterCss) }
    var activeTab by remember(currentStep.id) { mutableStateOf(currentStep.defaultTab) }
    var validationResult by remember(currentStep.id) { mutableStateOf<ValidationResult?>(null) }
    var showCompleteDialog by remember { mutableStateOf(false) }

    val isStepDone = completedSteps.contains(currentStep.id) || (validationResult?.isPassed == true)

    val nextLesson = remember(lessonId) {
        val idx = CurriculumRepository.allLessons.indexOfFirst { it.id == lessonId }
        if (idx in 0 until CurriculumRepository.allLessons.lastIndex) {
            CurriculumRepository.allLessons[idx + 1]
        } else null
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                text = lesson.title,
                                style = MaterialTheme.typography.titleMedium,
                                maxLines = 1
                            )
                            Text(
                                text = "گام ${currentStepIndex + 1} از ${lesson.steps.size}  •  ${currentStep.standardSource}",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = onBack,
                            modifier = Modifier
                                .minimumInteractiveComponentSize()
                                .testTag("lesson_back_button")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                contentDescription = "بازگشت"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background
                    )
                )
                LinearProgressIndicator(
                    progress = { (currentStepIndex + 1).toFloat() / lesson.steps.size },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.outline
                )
            }
        },
        bottomBar = {
            Column {
                HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                Surface(
                    tonalElevation = 0.dp,
                    color = MaterialTheme.colorScheme.background
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (currentStepIndex > 0) {
                            OutlinedButton(
                                onClick = { currentStepIndex -= 1 },
                                modifier = Modifier.minimumInteractiveComponentSize(),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("قبلی")
                            }
                        }

                        OutlinedButton(
                            onClick = {
                                validationResult = onValidateStep(currentStep, htmlCode, cssCode)
                            },
                            modifier = Modifier
                                .weight(1f)
                                .minimumInteractiveComponentSize()
                                .testTag("verify_step_button"),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.onBackground),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = MaterialTheme.colorScheme.background,
                                contentColor = MaterialTheme.colorScheme.onBackground
                            )
                        ) {
                            if (isStepDone) {
                                Icon(
                                    imageVector = Icons.Rounded.Check,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                            }
                            Text(if (isStepDone) "تایید شد (+${currentStep.xpReward} XP)" else "بررسی کد")
                        }

                        if (isStepDone) {
                            OutlinedButton(
                                onClick = {
                                    if (currentStepIndex < lesson.steps.lastIndex) {
                                        currentStepIndex += 1
                                    } else {
                                        showCompleteDialog = true
                                    }
                                },
                                modifier = Modifier
                                    .minimumInteractiveComponentSize()
                                    .testTag("next_step_button"),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(if (currentStepIndex < lesson.steps.lastIndex) "بعدی" else "اتمام")
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Rounded.PlayArrow,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = currentStep.title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = currentStep.explanation,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "نکته استاندارد: ${currentStep.standardNote}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(14.dp)
                )
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.outline)

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "تمرین این گام",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = currentStep.taskPrompt,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            AnimatedVisibility(visible = validationResult != null) {
                validationResult?.let { result ->
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("validation_banner")
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = if (result.isPassed) {
                                    "تمام معیارهای استاندارد پاس شد (+${currentStep.xpReward} XP)"
                                } else {
                                    "نکات تکمیل این گام:"
                                },
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            result.failedHints.forEach { hint ->
                                Text(
                                    text = "• $hint",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }

            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                val isWide = maxWidth >= 700.dp
                if (isWide) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        CodeEditorPane(
                            html = htmlCode,
                            css = cssCode,
                            activeTab = activeTab,
                            onTabChange = { activeTab = it },
                            onHtmlChange = { htmlCode = it },
                            onCssChange = { cssCode = it },
                            onReset = {
                                htmlCode = currentStep.starterHtml
                                cssCode = currentStep.starterCss
                                validationResult = null
                            },
                            onShowSolution = {
                                htmlCode = currentStep.solutionHtml
                                cssCode = currentStep.solutionCss
                            },
                            extraQuickChips = currentStep.quickChips,
                            modifier = Modifier.weight(1f)
                        )
                        HtmlLivePreview(
                            html = htmlCode,
                            css = cssCode,
                            minHeightDp = 300,
                            modifier = Modifier.weight(1f)
                        )
                    }
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        CodeEditorPane(
                            html = htmlCode,
                            css = cssCode,
                            activeTab = activeTab,
                            onTabChange = { activeTab = it },
                            onHtmlChange = { htmlCode = it },
                            onCssChange = { cssCode = it },
                            onReset = {
                                htmlCode = currentStep.starterHtml
                                cssCode = currentStep.starterCss
                                validationResult = null
                            },
                            onShowSolution = {
                                htmlCode = currentStep.solutionHtml
                                cssCode = currentStep.solutionCss
                            },
                            extraQuickChips = currentStep.quickChips
                        )
                        HtmlLivePreview(
                            html = htmlCode,
                            css = cssCode,
                            minHeightDp = 220
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    if (showCompleteDialog) {
        AlertDialog(
            onDismissRequest = { showCompleteDialog = false },
            title = { Text("درس تکمیل شد") },
            text = { Text("«${lesson.title}» با موفقیت به پایان رسید.") },
            confirmButton = {
                if (nextLesson != null) {
                    Button(
                        onClick = {
                            showCompleteDialog = false
                            onOpenNextLesson(nextLesson.id)
                        }
                    ) {
                        Text("درس بعدی")
                    }
                } else {
                    Button(
                        onClick = {
                            showCompleteDialog = false
                            onBack()
                        }
                    ) {
                        Text("بازگشت")
                    }
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showCompleteDialog = false
                        onBack()
                    }
                ) {
                    Text("فهرست درس‌ها")
                }
            }
        )
    }
}
