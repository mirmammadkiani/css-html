package com.example.model

enum class LessonTopic {
    HTML, CSS, LAYOUT
}

enum class CodeTarget {
    HTML, CSS
}

data class ValidationCheck(
    val description: String,
    val target: CodeTarget,
    val regexPattern: String,
    val errorHint: String
)

data class LessonStep(
    val id: String,
    val title: String,
    val explanation: String,
    val standardNote: String,
    val standardSource: String,
    val taskPrompt: String,
    val starterHtml: String,
    val starterCss: String,
    val solutionHtml: String,
    val solutionCss: String,
    val defaultTab: CodeTarget = CodeTarget.HTML,
    val checks: List<ValidationCheck>,
    val xpReward: Int = 25,
    val quickChips: List<String> = emptyList()
)

data class Lesson(
    val id: String,
    val moduleId: String,
    val number: Int,
    val title: String,
    val subtitle: String,
    val topic: LessonTopic,
    val estimatedMinutes: Int,
    val steps: List<LessonStep>
)

data class CurriculumModule(
    val id: String,
    val number: Int,
    val title: String,
    val description: String,
    val referenceBadge: String,
    val topic: LessonTopic,
    val lessons: List<Lesson>
)

data class ReferenceEntry(
    val id: String,
    val category: String,
    val syntax: String,
    val title: String,
    val summary: String,
    val standardDoc: String,
    val sampleHtml: String,
    val sampleCss: String
)

data class PlaygroundTemplate(
    val id: String,
    val title: String,
    val subtitle: String,
    val html: String,
    val css: String
)

data class SavedSnippet(
    val id: String,
    val title: String,
    val html: String,
    val css: String,
    val updatedAtMillis: Long
)

data class ValidationResult(
    val isPassed: Boolean,
    val passedChecks: List<String>,
    val failedHints: List<String>
)
