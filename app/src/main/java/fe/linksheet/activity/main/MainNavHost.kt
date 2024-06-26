package fe.linksheet.activity.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import fe.linksheet.*
import fe.linksheet.composable.main.MainRoute
import fe.linksheet.composable.settings.SettingsRoute
import fe.linksheet.composable.settings.about.AboutSettingsRoute
import fe.linksheet.composable.settings.about.CreditsSettingsRoute
import fe.linksheet.composable.settings.about.DonateSettingsRoute
import fe.linksheet.composable.settings.advanced.*
import fe.linksheet.composable.settings.apps.AppsSettingsRoute
import fe.linksheet.composable.settings.apps.PretendToBeAppSettingsRoute
import fe.linksheet.composable.settings.apps.link.AppsWhichCanOpenLinksSettingsRoute
import fe.linksheet.composable.settings.apps.preferred.PreferredAppSettingsRoute
import fe.linksheet.composable.settings.bottomsheet.BottomSheetSettingsRoute
import fe.linksheet.composable.settings.browser.BrowserSettingsRoute
import fe.linksheet.composable.settings.browser.inapp.InAppBrowserSettingsDisableInSelectedRoute
import fe.linksheet.composable.settings.browser.inapp.InAppBrowserSettingsRoute
import fe.linksheet.composable.settings.browser.mode.PreferredBrowserSettingsRoute
import fe.linksheet.composable.settings.browser.mode.WhitelistedBrowsersSettingsRoute
import fe.linksheet.composable.settings.debug.DebugSettingsRoute
import fe.linksheet.composable.settings.debug.loadpreferences.LoadDumpedPreferences
import fe.linksheet.composable.settings.debug.log.LogSettingsRoute
import fe.linksheet.composable.settings.debug.log.LogTextSettingsRoute
import fe.linksheet.composable.settings.dev.DevBottomSheetSettingsRoute
import fe.linksheet.composable.settings.dev.DevSettingsRoute
import fe.linksheet.composable.settings.general.GeneralSettingsRoute
import fe.linksheet.composable.settings.link.LinksSettingsRoute
import fe.linksheet.composable.settings.link.amp2html.Amp2HtmlSettingsRoute
import fe.linksheet.composable.settings.link.downloader.DownloaderSettingsRoute
import fe.linksheet.composable.settings.link.libredirect.LibRedirectServiceSettingsRoute
import fe.linksheet.composable.settings.link.libredirect.LibRedirectSettingsRoute
import fe.linksheet.composable.settings.link.redirect.FollowRedirectsSettingsRoute
import fe.linksheet.composable.settings.notification.NotificationSettingsRoute
import fe.linksheet.composable.settings.privacy.PrivacySettingsRoute
import fe.linksheet.composable.settings.theme.ThemeSettingsRoute
import fe.linksheet.composable.util.animatedArgumentRouteComposable
import fe.linksheet.composable.util.animatedComposable
import fe.linksheet.experiment.ui.overhaul.composable.page.main.NewMainRoute
import fe.linksheet.experiment.ui.overhaul.composable.page.settings.advanced.NewExperimentsSettingsRoute
import fe.linksheet.util.AndroidVersion

@Composable
fun MainNavHost(navController: NavHostController, uiOverhaul: Boolean, onBackPressed: () -> Unit) {
    NavHost(navController = navController, startDestination = mainRoute) {
        animatedComposable(route = mainRoute) {
            if (uiOverhaul) {
                NewMainRoute(navController = navController)
            } else {
                MainRoute(navController = navController)
            }
        }

        animatedComposable(route = settingsRoute) {
            SettingsRoute(
                navController = navController,
                onBackPressed = onBackPressed
            )
        }

        animatedComposable(route = appsSettingsRoute) {
            AppsSettingsRoute(
                navController = navController,
                onBackPressed = onBackPressed
            )
        }

        animatedComposable(route = browserSettingsRoute) {
            BrowserSettingsRoute(
                navController = navController,
                onBackPressed = onBackPressed
            )
        }

        animatedComposable(route = generalSettingsRoute) {
            GeneralSettingsRoute(onBackPressed = onBackPressed)
        }

        animatedComposable(route = notificationSettingsRoute) {
            NotificationSettingsRoute(onBackPressed = onBackPressed)
        }

        animatedComposable(route = privacySettingsRoute) {
            PrivacySettingsRoute(onBackPressed = onBackPressed)
        }

        animatedComposable(route = bottomSheetSettingsRoute) {
            BottomSheetSettingsRoute(onBackPressed = onBackPressed)
        }

        animatedComposable(route = linksSettingsRoute) {
            LinksSettingsRoute(
                onBackPressed = onBackPressed,
                navController = navController,
            )
        }

        animatedComposable(route = followRedirectsSettingsRoute) {
            FollowRedirectsSettingsRoute(onBackPressed = onBackPressed)
        }

        animatedComposable(route = libRedirectSettingsRoute) {
            LibRedirectSettingsRoute(
                onBackPressed = onBackPressed,
                navController = navController,
            )
        }

        animatedArgumentRouteComposable(route = libRedirectServiceSettingsRoute) { _, _ ->
            LibRedirectServiceSettingsRoute(onBackPressed = onBackPressed)
        }

        animatedComposable(route = downloaderSettingsRoute) {
            DownloaderSettingsRoute(onBackPressed = onBackPressed)
        }

        animatedComposable(route = amp2HtmlSettingsRoute) {
            Amp2HtmlSettingsRoute(onBackPressed = onBackPressed)
        }

        animatedComposable(route = themeSettingsRoute) {
            ThemeSettingsRoute(onBackPressed = onBackPressed)
        }

        animatedComposable(route = advancedSettingsRoute) {
            AdvancedSettingsRoute(
                navController = navController,
                onBackPressed = onBackPressed
            )
        }

        animatedComposable(route = shizukuSettingsRoute) {
            ShizukuSettingsRoute(
                navController = navController,
                onBackPressed = onBackPressed
            )
        }

        animatedComposable(route = featureFlagSettingsRoute) {
            FeatureFlagSettingsRoute(
                navController = navController,
                onBackPressed = onBackPressed
            )
        }

        animatedArgumentRouteComposable(route = experimentSettingsRoute) { _, _ ->
            if (uiOverhaul) {
                NewExperimentsSettingsRoute(
                    onBackPressed = onBackPressed
                )
            } else {
                ExperimentsSettingsRoute(
                    navController = navController,
                    onBackPressed = onBackPressed
                )
            }
        }

        animatedComposable(route = exportImportSettingsRoute) {
            ExportImportSettingsRoute(onBackPressed = onBackPressed)
        }

        animatedComposable(route = debugSettingsRoute) {
            DebugSettingsRoute(
                navController = navController,
                onBackPressed = onBackPressed
            )
        }

        animatedComposable(route = logViewerSettingsRoute) {
            LogSettingsRoute(
                navController = navController,
                onBackPressed = onBackPressed
            )
        }

        animatedComposable(route = loadDumpedPreferences) {
            LoadDumpedPreferences(onBackPressed = onBackPressed)
        }

        animatedArgumentRouteComposable(route = logTextViewerSettingsRoute) { _, _ ->
            LogTextSettingsRoute(onBackPressed = onBackPressed)
        }

        animatedComposable(route = aboutSettingsRoute) {
            AboutSettingsRoute(
                navController = navController,
                onBackPressed = onBackPressed
            )
        }

        animatedComposable(route = donateSettingsRoute) {
            DonateSettingsRoute(
                onBackPressed = onBackPressed
            )
        }

        animatedComposable(route = creditsSettingsRoute) {
            CreditsSettingsRoute(onBackPressed = onBackPressed)
        }

        animatedComposable(route = preferredBrowserSettingsRoute) {
            PreferredBrowserSettingsRoute(
                navController = navController,
                onBackPressed = onBackPressed
            )
        }

        animatedComposable(route = whitelistedBrowsersSettingsRoute) {
            WhitelistedBrowsersSettingsRoute(navController = navController)
        }

        animatedComposable(route = inAppBrowserSettingsRoute) {
            InAppBrowserSettingsRoute(
                navController = navController,
                onBackPressed = onBackPressed
            )
        }

        animatedComposable(route = inAppBrowserSettingsDisableInSelectedRoute) {
            InAppBrowserSettingsDisableInSelectedRoute(navController = navController)
        }

        animatedComposable(route = preferredAppsSettingsRoute) {
            PreferredAppSettingsRoute(onBackPressed = onBackPressed)
        }

        if (AndroidVersion.AT_LEAST_API_31_S) {
            animatedComposable(route = appsWhichCanOpenLinksSettingsRoute) {
                AppsWhichCanOpenLinksSettingsRoute(onBackPressed = onBackPressed)
            }

            animatedComposable(route = pretendToBeAppRoute) {
                PretendToBeAppSettingsRoute(onBackPressed = onBackPressed)
            }
        }

        animatedComposable(route = devModeRoute) {
            DevSettingsRoute(onBackPressed = onBackPressed)
        }

        animatedComposable(route = devBottomSheetExperimentRoute) {
            DevBottomSheetSettingsRoute(onBackPressed = onBackPressed)
        }
    }
}
