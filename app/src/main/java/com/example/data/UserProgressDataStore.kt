package com.example.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.model.SavedSnippet
import com.example.ui.theme.ThemePreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.json.JSONArray
import org.json.JSONObject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "webamooz_prefs")

class UserProgressDataStore(private val context: Context) {

    private object Keys {
        val COMPLETED_STEPS = stringSetPreferencesKey("completed_steps")
        val TOTAL_XP = intPreferencesKey("total_xp")
        val THEME_PREF = stringPreferencesKey("theme_pref")
        val PLAYGROUND_HTML = stringPreferencesKey("playground_html")
        val PLAYGROUND_CSS = stringPreferencesKey("playground_css")
        val SAVED_SNIPPETS_JSON = stringPreferencesKey("saved_snippets_json")
        val STEP_DRAFT_PREFIX = "draft_"
    }

    val completedStepsFlow: Flow<Set<String>> = context.dataStore.data.map { prefs ->
        prefs[Keys.COMPLETED_STEPS] ?: emptySet()
    }

    val totalXpFlow: Flow<Int> = context.dataStore.data.map { prefs ->
        prefs[Keys.TOTAL_XP] ?: 0
    }

    val themePreferenceFlow: Flow<ThemePreference> = context.dataStore.data.map { prefs ->
        when (prefs[Keys.THEME_PREF]) {
            ThemePreference.DARK.name -> ThemePreference.DARK
            ThemePreference.SYSTEM.name -> ThemePreference.SYSTEM
            else -> ThemePreference.LIGHT
        }
    }

    val playgroundCodeFlow: Flow<Pair<String, String>?> = context.dataStore.data.map { prefs ->
        val html = prefs[Keys.PLAYGROUND_HTML]
        val css = prefs[Keys.PLAYGROUND_CSS]
        if (html != null && css != null) html to css else null
    }

    val savedSnippetsFlow: Flow<List<SavedSnippet>> = context.dataStore.data.map { prefs ->
        val raw = prefs[Keys.SAVED_SNIPPETS_JSON] ?: return@map emptyList()
        parseSnippets(raw)
    }

    suspend fun markStepCompleted(stepId: String, xpToAdd: Int) {
        context.dataStore.edit { prefs ->
            val current = prefs[Keys.COMPLETED_STEPS] ?: emptySet()
            if (!current.contains(stepId)) {
                prefs[Keys.COMPLETED_STEPS] = current + stepId
                prefs[Keys.TOTAL_XP] = (prefs[Keys.TOTAL_XP] ?: 0) + xpToAdd
            }
        }
    }

    suspend fun setThemePreference(preference: ThemePreference) {
        context.dataStore.edit { prefs ->
            prefs[Keys.THEME_PREF] = preference.name
        }
    }

    suspend fun savePlaygroundCode(html: String, css: String) {
        context.dataStore.edit { prefs ->
            prefs[Keys.PLAYGROUND_HTML] = html
            prefs[Keys.PLAYGROUND_CSS] = css
        }
    }

    suspend fun saveSnippet(snippet: SavedSnippet) {
        context.dataStore.edit { prefs ->
            val list = parseSnippets(prefs[Keys.SAVED_SNIPPETS_JSON] ?: "[]").toMutableList()
            list.removeAll { it.id == snippet.id }
            list.add(0, snippet)
            prefs[Keys.SAVED_SNIPPETS_JSON] = encodeSnippets(list)
        }
    }

    suspend fun deleteSnippet(snippetId: String) {
        context.dataStore.edit { prefs ->
            val list = parseSnippets(prefs[Keys.SAVED_SNIPPETS_JSON] ?: "[]")
                .filterNot { it.id == snippetId }
            prefs[Keys.SAVED_SNIPPETS_JSON] = encodeSnippets(list)
        }
    }

    private fun parseSnippets(raw: String): List<SavedSnippet> {
        return try {
            val array = JSONArray(raw)
            buildList {
                for (i in 0 until array.length()) {
                    val obj = array.getJSONObject(i)
                    add(
                        SavedSnippet(
                            id = obj.getString("id"),
                            title = obj.getString("title"),
                            html = obj.getString("html"),
                            css = obj.getString("css"),
                            updatedAtMillis = obj.optLong("updatedAtMillis", 0L)
                        )
                    )
                }
            }
        } catch (_: Exception) {
            emptyList()
        }
    }

    private fun encodeSnippets(list: List<SavedSnippet>): String {
        val array = JSONArray()
        list.forEach { s ->
            val obj = JSONObject()
            obj.put("id", s.id)
            obj.put("title", s.title)
            obj.put("html", s.html)
            obj.put("css", s.css)
            obj.put("updatedAtMillis", s.updatedAtMillis)
            array.put(obj)
        }
        return array.toString()
    }
}
