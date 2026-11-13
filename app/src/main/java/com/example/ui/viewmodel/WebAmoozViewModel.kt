package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.CurriculumRepository
import com.example.data.UserProgressDataStore
import com.example.model.CodeTarget
import com.example.model.LessonStep
import com.example.model.SavedSnippet
import com.example.model.ValidationResult
import com.example.ui.theme.ThemePreference
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WebAmoozViewModel(application: Application) : AndroidViewModel(application) {

    private val dataStore = UserProgressDataStore(application)

    val completedSteps: StateFlow<Set<String>> = dataStore.completedStepsFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    val totalXp: StateFlow<Int> = dataStore.totalXpFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val themePreference: StateFlow<ThemePreference> = dataStore.themePreferenceFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ThemePreference.LIGHT)

    val savedSnippets: StateFlow<List<SavedSnippet>> = dataStore.savedSnippetsFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val defaultTemplate = CurriculumRepository.playgroundTemplates.first()

    private val _playgroundHtml = MutableStateFlow(defaultTemplate.html)
    val playgroundHtml: StateFlow<String> = _playgroundHtml.asStateFlow()

    private val _playgroundCss = MutableStateFlow(defaultTemplate.css)
    val playgroundCss: StateFlow<String> = _playgroundCss.asStateFlow()

    init {
        viewModelScope.launch {
            val saved = dataStore.playgroundCodeFlow.first()
            if (saved != null) {
                _playgroundHtml.value = saved.first
                _playgroundCss.value = saved.second
            }
        }
    }

    fun setThemePreference(pref: ThemePreference) {
        viewModelScope.launch {
            dataStore.setThemePreference(pref)
        }
    }

    fun updatePlaygroundHtml(html: String) {
        _playgroundHtml.value = html
        viewModelScope.launch {
            dataStore.savePlaygroundCode(html, _playgroundCss.value)
        }
    }

    fun updatePlaygroundCss(css: String) {
        _playgroundCss.value = css
        viewModelScope.launch {
            dataStore.savePlaygroundCode(_playgroundHtml.value, css)
        }
    }

    fun loadIntoPlayground(html: String, css: String) {
        _playgroundHtml.value = html
        _playgroundCss.value = css
        viewModelScope.launch {
            dataStore.savePlaygroundCode(html, css)
        }
    }

    fun savePlaygroundSnippet(title: String) {
        val cleanTitle = title.trim().ifEmpty { "نمونه کد بدون عنوان" }
        val snippet = SavedSnippet(
            id = "snip_${System.currentTimeMillis()}",
            title = cleanTitle,
            html = _playgroundHtml.value,
            css = _playgroundCss.value,
            updatedAtMillis = System.currentTimeMillis()
        )
        viewModelScope.launch {
            dataStore.saveSnippet(snippet)
        }
    }

    fun deleteSnippet(snippetId: String) {
        viewModelScope.launch {
            dataStore.deleteSnippet(snippetId)
        }
    }

    fun validateStep(step: LessonStep, html: String, css: String): ValidationResult {
        val passed = mutableListOf<String>()
        val failed = mutableListOf<String>()

        step.checks.forEach { check ->
            val source = when (check.target) {
                CodeTarget.HTML -> html
                CodeTarget.CSS -> css
            }
            val regex = Regex(
                pattern = check.regexPattern,
                options = setOf(RegexOption.IGNORE_CASE, RegexOption.DOT_MATCHES_ALL)
            )
            if (regex.containsMatchIn(source)) {
                passed.add(check.description)
            } else {
                failed.add(check.errorHint)
            }
        }

        val isPassed = failed.isEmpty()
        if (isPassed) {
            viewModelScope.launch {
                dataStore.markStepCompleted(step.id, step.xpReward)
            }
        }

        return ValidationResult(
            isPassed = isPassed,
            passedChecks = passed,
            failedHints = failed
        )
    }
}
