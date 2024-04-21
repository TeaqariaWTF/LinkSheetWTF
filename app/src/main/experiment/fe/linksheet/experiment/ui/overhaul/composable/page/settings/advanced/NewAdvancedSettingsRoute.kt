package fe.linksheet.experiment.ui.overhaul.composable.page.settings.advanced

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.ImportExport
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import fe.linksheet.R
import fe.linksheet.experiment.ui.overhaul.composable.component.list.item.RouteNavigateListItem
import fe.linksheet.experiment.ui.overhaul.composable.component.page.SaneScaffoldSettingsPage
import fe.linksheet.experiment.ui.overhaul.composable.component.list.item.RouteNavItem
import fe.linksheet.experiment.ui.overhaul.composable.component.page.layout.group
import fe.linksheet.experimentSettingsRoute
import fe.linksheet.exportImportSettingsRoute
import fe.linksheet.featureFlagSettingsRoute


internal object NewAdvancedSettingsRouteData {
    val items = arrayOf(
        RouteNavItem(
            featureFlagSettingsRoute,
            Icons.Default.Flag,
            R.string.feature_flags,
            R.string.feature_flags_explainer
        ),
        RouteNavItem(
            experimentSettingsRoute.route,
            Icons.Default.Android,
            R.string.experiments,
            R.string.experiments_explainer
        ),
        RouteNavItem(
            exportImportSettingsRoute,
            Icons.Default.ImportExport,
            R.string.export_import_settings,
            R.string.export_import_settings_explainer
        )
    )
}

@Composable
fun NewAdvancedSettingsRoute(onBackPressed: () -> Unit, navigate: (String) -> Unit) {
    SaneScaffoldSettingsPage(headline = stringResource(id = R.string.advanced), onBackPressed = onBackPressed) {
        group(items = NewAdvancedSettingsRouteData.items) { data, padding, shape ->
            RouteNavigateListItem(
                data = data,
                shape = shape,
                padding = padding,
                navigate = navigate,
            )
        }
    }
}