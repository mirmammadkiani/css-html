package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.ExpandLess
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.CurriculumRepository
import com.example.model.CurriculumModule
import com.example.model.Lesson
import com.example.model.LessonTopic
import com.example.ui.theme.TopicCssTextDark
import com.example.ui.theme.TopicCssTextLight
import com.example.ui.theme.TopicHtmlTextDark
import com.example.ui.theme.TopicHtmlTextLight
import com.example.ui.theme.TopicLayoutTextDark
import com.example.ui.theme.TopicLayoutTextLight

@Composable
fun LearnScreen(
    completedSteps: Set<String>,
    totalXp: Int,
    onOpenLesson: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTopic by remember { mutableStateOf<LessonTopic?>(null) }

    val modules = remember(selectedTopic) {
        if (selectedTopic == null) {
            CurriculumRepository.modules
        } else {
            CurriculumRepository.modules.filter { module ->
                module.topic == selectedTopic || module.lessons.any { it.topic == selectedTopic }
            }
        }
    }

    val totalStepsCount = CurriculumRepository.allStepsCount
    val completedCount = completedSteps.size.coerceAtMost(totalStepsCount)
    val progressRatio = if (totalStepsCount > 0) completedCount.toFloat() / totalStepsCount else 0f

    val nextLesson = remember(completedSteps) {
        CurriculumRepository.allLessons.firstOrNull { lesson ->
            lesson.steps.any { step -> !completedSteps.contains(step.id) }
        } ?: CurriculumRepository.allLessons.first()
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 720.dp),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Notion-style minimal document header (zero images)
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "MDN & WHATWG  •  $totalXp XP",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Text(
                        text = "آموزش اصولی HTML و CSS",
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Text(
                        text = "یادگیری مرحله‌به‌مرحله با کدنویسی زنده و بدون حاشیه.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "پیشرفت مسیر",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "$completedCount / $totalStepsCount گام",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    LinearProgressIndicator(
                        progress = { progressRatio },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(3.dp)
                            .clip(RoundedCornerShape(999.dp)),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.outline
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Button(
                        onClick = { onOpenLesson(nextLesson.id) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .minimumInteractiveComponentSize()
                            .testTag("continue_learning_button"),
                        shape = RoundedCornerShape(8.dp),
                        elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.PlayArrow,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "ادامه: ${nextLesson.title}",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                }
            }

            // Minimal Notion-like filter tabs
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    NotionFilterPill(
                        label = "همه",
                        selected = selectedTopic == null,
                        onClick = { selectedTopic = null }
                    )
                    NotionFilterPill(
                        label = "HTML",
                        selected = selectedTopic == LessonTopic.HTML,
                        onClick = { selectedTopic = LessonTopic.HTML }
                    )
                    NotionFilterPill(
                        label = "CSS",
                        selected = selectedTopic == LessonTopic.CSS,
                        onClick = { selectedTopic = LessonTopic.CSS }
                    )
                    NotionFilterPill(
                        label = "Layout",
                        selected = selectedTopic == LessonTopic.LAYOUT,
                        onClick = { selectedTopic = LessonTopic.LAYOUT }
                    )
                }
            }

            items(modules, key = { it.id }) { module ->
                NotionModuleToggleBox(
                    module = module,
                    completedSteps = completedSteps,
                    onOpenLesson = onOpenLesson
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun NotionFilterPill(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        border = BorderStroke(
            width = 1.dp,
            color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
        ),
        modifier = Modifier
            .minimumInteractiveComponentSize()
            .clip(RoundedCornerShape(6.dp))
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun NotionModuleToggleBox(
    module: CurriculumModule,
    completedSteps: Set<String>,
    onOpenLesson: (String) -> Unit
) {
    val isDark = MaterialTheme.colorScheme.background.luminance() < 0.5f

    // ONLY font color inside topic box header is colored
    val topicFontColor = when (module.topic) {
        LessonTopic.HTML -> if (isDark) TopicHtmlTextDark else TopicHtmlTextLight
        LessonTopic.CSS -> if (isDark) TopicCssTextDark else TopicCssTextLight
        LessonTopic.LAYOUT -> if (isDark) TopicLayoutTextDark else TopicLayoutTextLight
    }

    var expanded by remember { mutableStateOf(true) }

    Surface(
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        tonalElevation = 0.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(horizontal = 14.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "${module.number}. ${module.title}",
                            style = MaterialTheme.typography.titleMedium,
                            color = topicFontColor,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = "${module.referenceBadge}  •  ${module.description}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Icon(
                    imageVector = if (expanded) Icons.Rounded.ExpandLess else Icons.Rounded.ExpandMore,
                    contentDescription = if (expanded) "بستن فصل" else "باز کردن فصل",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    module.lessons.forEach { lesson ->
                        HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                        NotionLessonRow(
                            lesson = lesson,
                            completedSteps = completedSteps,
                            onClick = { onOpenLesson(lesson.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun NotionLessonRow(
    lesson: Lesson,
    completedSteps: Set<String>,
    onClick: () -> Unit
) {
    val doneSteps = lesson.steps.count { completedSteps.contains(it.id) }
    val isDone = doneSteps == lesson.steps.size && lesson.steps.isNotEmpty()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .minimumInteractiveComponentSize()
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 12.dp)
            .testTag("lesson_card_${lesson.id}"),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.weight(1f)
        ) {
            Surface(
                modifier = Modifier.size(22.dp),
                shape = RoundedCornerShape(5.dp),
                color = if (isDone) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background,
                border = BorderStroke(
                    width = 1.dp,
                    color = if (isDone) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                )
            ) {
                if (isDone) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Rounded.Check,
                            contentDescription = "تکمیل‌شده",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = "${lesson.number}. ${lesson.title}",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${lesson.subtitle} • ${lesson.estimatedMinutes} دقیقه",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Icon(
            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
            contentDescription = "شروع درس",
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
