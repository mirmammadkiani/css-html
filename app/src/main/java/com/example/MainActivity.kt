package com.example

import android.graphics.Color as AndroidColor
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material.icons.rounded.Code
import androidx.compose.material.icons.rounded.MenuBook
import androidx.compose.material.icons.rounded.School
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ui.screens.LearnScreen
import com.example.ui.screens.LessonScreen
import com.example.ui.screens.PlaygroundScreen
import com.example.ui.screens.ProgressScreen
import com.example.ui.screens.ReferenceScreen
import com.example.ui.theme.ThemePreference
import com.example.ui.theme.WebAmoozTheme
import com.example.ui.viewmodel.WebAmoozViewModel

object AppRoutes {
    const val ROUTE_LEARN = "learn"
    const val ROUTE_PLAYGROUND = "playground"
    const val ROUTE_REFERENCE = "reference"
    const val ROUTE_PROGRESS = "progress"
    const val ROUTE_LESSON = "lesson/{lessonId}"

    fun lessonRoute(lessonId: String) = "lesson/$lessonId"
}

private data class TopTab(
    val route: String,
    val labelResId: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
)

private val topTabs = listOf(
    TopTab(
        route = AppRoutes.ROUTE_LEARN,
        labelResId = R.string.tab_learn,
        selectedIcon = Icons.Rounded.School,
        unselectedIcon = Icons.Outlined.School,
        testTag = "nav_learn"
    ),
    TopTab(
        route = AppRoutes.ROUTE_PLAYGROUND,
        labelResId = R.string.tab_playground,
        selectedIcon = Icons.Rounded.Code,
        unselectedIcon = Icons.Outlined.Code,
        testTag = "nav_playground"
    ),
    TopTab(
        route = AppRoutes.ROUTE_REFERENCE,
        labelResId = R.string.tab_reference,
        selectedIcon = Icons.Rounded.MenuBook,
        unselectedIcon = Icons.Outlined.MenuBook,
        testTag = "nav_reference"
    ),
    TopTab(
        route = AppRoutes.ROUTE_PROGRESS,
        labelResId = R.string.tab_progress,
        selectedIcon = Icons.Rounded.BarChart,
        unselectedIcon = Icons.Outlined.BarChart,
        testTag = "nav_progress"
    )
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(AndroidColor.TRANSPARENT, AndroidColor.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(AndroidColor.TRANSPARENT, AndroidColor.TRANSPARENT)
        )
        setContent {
            val viewModel: WebAmoozViewModel = viewModel()
            val themePreference by viewModel.themePreference.collectAsStateWithLifecycle()
            val isDark = when (themePreference) {
                ThemePreference.SYSTEM -> isSystemInDarkTheme()
                ThemePreference.DARK -> true
                ThemePreference.LIGHT -> false
            }

            LaunchedEffect(isDark) {
                enableEdgeToEdge(
                    statusBarStyle = if (isDark) {
                        SystemBarStyle.dark(AndroidColor.TRANSPARENT)
                    } else {
                        SystemBarStyle.light(AndroidColor.TRANSPARENT, AndroidColor.TRANSPARENT)
                    },
                    navigationBarStyle = if (isDark) {
                        SystemBarStyle.dark(AndroidColor.TRANSPARENT)
                    } else {
                        SystemBarStyle.light(AndroidColor.TRANSPARENT, AndroidColor.TRANSPARENT)
                    }
                )
            }

            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                WebAmoozTheme(themePreference = themePreference) {
                    WebAmoozApp(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun WebAmoozApp(viewModel: WebAmoozViewModel) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    val completedSteps by viewModel.completedSteps.collectAsStateWithLifecycle()
    val totalXp by viewModel.totalXp.collectAsStateWithLifecycle()
    val themePreference by viewModel.themePreference.collectAsStateWithLifecycle()
    val savedSnippets by viewModel.savedSnippets.collectAsStateWithLifecycle()
    val playgroundHtml by viewModel.playgroundHtml.collectAsStateWithLifecycle()
    val playgroundCss by viewModel.playgroundCss.collectAsStateWithLifecycle()

    val isTopLevel = topTabs.any { tab ->
        currentDestination?.hierarchy?.any { it.route == tab.route } == true
    }

    val navigateToTopTab: (String) -> Unit = { route ->
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val useRail = maxWidth >= 600.dp

        Scaffold(
            contentWindowInsets = WindowInsets.safeDrawing,
            bottomBar = {
                if (isTopLevel && !useRail) {
                    androidx.compose.foundation.layout.Column {
                        androidx.compose.material3.HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                        NavigationBar(
                            windowInsets = WindowInsets.navigationBars,
                            containerColor = MaterialTheme.colorScheme.background,
                            tonalElevation = 0.dp
                        ) {
                            topTabs.forEach { tab ->
                                val selected = currentDestination?.hierarchy?.any { it.route == tab.route } == true
                                NavigationBarItem(
                                    selected = selected,
                                    onClick = { navigateToTopTab(tab.route) },
                                    icon = {
                                        Icon(
                                            imageVector = if (selected) tab.selectedIcon else tab.unselectedIcon,
                                            contentDescription = stringResource(tab.labelResId)
                                        )
                                    },
                                    label = {
                                        Text(
                                            text = stringResource(tab.labelResId),
                                            style = MaterialTheme.typography.labelSmall
                                        )
                                    },
                                    modifier = Modifier.testTag(tab.testTag)
                                )
                            }
                        }
                    }
                }
            }
        ) { innerPadding ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if (isTopLevel && useRail) {
                    NavigationRail(
                        containerColor = MaterialTheme.colorScheme.surface
                    ) {
                        topTabs.forEach { tab ->
                            val selected = currentDestination?.hierarchy?.any { it.route == tab.route } == true
                            NavigationRailItem(
                                selected = selected,
                                onClick = { navigateToTopTab(tab.route) },
                                icon = {
                                    Icon(
                                        imageVector = if (selected) tab.selectedIcon else tab.unselectedIcon,
                                        contentDescription = stringResource(tab.labelResId)
                                    )
                                },
                                label = {
                                    Text(
                                        text = stringResource(tab.labelResId),
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                },
                                modifier = Modifier.testTag(tab.testTag)
                            )
                        }
                    }
                }

                NavHost(
                    navController = navController,
                    startDestination = AppRoutes.ROUTE_LEARN,
                    modifier = Modifier.weight(1f)
                ) {
                    composable(AppRoutes.ROUTE_LEARN) {
                        LearnScreen(
                            completedSteps = completedSteps,
                            totalXp = totalXp,
                            onOpenLesson = { lessonId ->
                                navController.navigate(AppRoutes.lessonRoute(lessonId))
                            }
                        )
                    }

                    composable(AppRoutes.ROUTE_PLAYGROUND) {
                        PlaygroundScreen(
                            html = playgroundHtml,
                            css = playgroundCss,
                            onHtmlChange = viewModel::updatePlaygroundHtml,
                            onCssChange = viewModel::updatePlaygroundCss,
                            onLoadTemplate = viewModel::loadIntoPlayground,
                            onSaveSnippet = viewModel::savePlaygroundSnippet
                        )
                    }

                    composable(AppRoutes.ROUTE_REFERENCE) {
                        ReferenceScreen(
                            onTryInPlayground = { html, css ->
                                viewModel.loadIntoPlayground(html, css)
                                navigateToTopTab(AppRoutes.ROUTE_PLAYGROUND)
                            }
                        )
                    }

                    composable(AppRoutes.ROUTE_PROGRESS) {
                        ProgressScreen(
                            completedSteps = completedSteps,
                            totalXp = totalXp,
                            savedSnippets = savedSnippets,
                            themePreference = themePreference,
                            onThemeChange = viewModel::setThemePreference,
                            onOpenSnippet = { snippet ->
                                viewModel.loadIntoPlayground(snippet.html, snippet.css)
                                navigateToTopTab(AppRoutes.ROUTE_PLAYGROUND)
                            },
                            onDeleteSnippet = viewModel::deleteSnippet
                        )
                    }

                    composable(
                        route = AppRoutes.ROUTE_LESSON,
                        arguments = listOf(navArgument("lessonId") { type = NavType.StringType })
                    ) { entry ->
                        val lessonId = entry.arguments?.getString("lessonId") ?: return@composable
                        LessonScreen(
                            lessonId = lessonId,
                            completedSteps = completedSteps,
                            onValidateStep = viewModel::validateStep,
                            onBack = { navController.popBackStack() },
                            onOpenNextLesson = { nextId ->
                                navController.navigate(AppRoutes.lessonRoute(nextId)) {
                                    popUpTo(AppRoutes.ROUTE_LEARN)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
