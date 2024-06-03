package fe.linksheet.experiment.ui.overhaul.composable.page.main

import android.app.Activity
import android.app.role.RoleManager
import android.content.Intent
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircleOutline
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.getSystemService
import fe.linksheet.R
import fe.linksheet.experiment.ui.overhaul.composable.component.card.ClickableAlertCard2
import fe.linksheet.experiment.ui.overhaul.composable.util.Resource.Companion.textContent
import fe.linksheet.experiment.ui.overhaul.ui.PreviewThemeNew
import fe.linksheet.module.viewmodel.MainViewModel
import fe.linksheet.util.AndroidVersion

@Composable
private fun cardContainerColor(isDefaultBrowser: Boolean): Color {
    return if (isDefaultBrowser) MaterialTheme.colorScheme.primaryContainer
    else MaterialTheme.colorScheme.errorContainer
}

@Composable
private fun buttonColor(isDefaultBrowser: Boolean): Color {
    return if (isDefaultBrowser) MaterialTheme.colorScheme.primary
    else MaterialTheme.colorScheme.error
}

@Composable
private fun StatusCard(
    isDefaultBrowser: Boolean,
    launchIntent: (MainViewModel.SettingsIntent) -> Unit,
    onSetAsDefault: () -> Unit
) {
    val containerColor = cardContainerColor(isDefaultBrowser)
    val buttonColor = buttonColor(isDefaultBrowser)

    val icon = if (isDefaultBrowser) Icons.Rounded.CheckCircleOutline
    else Icons.Rounded.ErrorOutline

    val title = if (isDefaultBrowser) R.string.settings_main_setup_success__title_linksheet_setup_success
    else R.string.settings_main_setup_success__title_linksheet_setup_failure

    val subtitle = if (isDefaultBrowser) R.string.settings_main_setup_success__text_linksheet_setup_success_info
    else R.string.settings_main_setup_success__text_linksheet_setup_failure_info

    ClickableAlertCard2(
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        contentDescription = null,
        headline = textContent(title),
        subtitle = textContent(subtitle),
        imageVector = icon,
    ) {
//            Text(text = stringResource(id = R.string.settings_main_setup_success__subtitle_quick_settings))

        LazyRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            if (isDefaultBrowser) {
                item(key = R.string.settings_main_setup_success__button_change_browser) {
                    StatusCardButton(
                        id = R.string.settings_main_setup_success__button_change_browser,
                        buttonColor = buttonColor,
                        onClick = { launchIntent(MainViewModel.SettingsIntent.DefaultApps) }
                    )
                }

                item(key = R.string.settings_main_setup_success__button_link_handlers) {
                    StatusCardButton(
                        id = R.string.settings_main_setup_success__button_link_handlers,
                        buttonColor = buttonColor,
                        onClick = { launchIntent(MainViewModel.SettingsIntent.DomainUrls) }
                    )
                }

                item(key = R.string.settings_main_setup_success__button_connected_apps) {
                    StatusCardButton(
                        id = R.string.settings_main_setup_success__button_connected_apps,
                        buttonColor = buttonColor,
                        onClick = { launchIntent(MainViewModel.SettingsIntent.CrossProfileAccess) }
                    )
                }
            } else {
                item(key = R.string.settings_main_setup_success__button_set_default_browser) {
                    StatusCardButton(
                        id = R.string.settings_main_setup_success__button_set_default_browser,
                        buttonColor = buttonColor,
                        onClick = onSetAsDefault
                    )
                }
            }
        }
    }
}


@Composable
fun StatusCard_Wrapper(
    isDefaultBrowser: Boolean,
    updateDefaultBrowser: () -> Unit,
    launchIntent: (MainViewModel.SettingsIntent) -> Unit,
) {
    if (AndroidVersion.AT_LEAST_API_29_Q) {
        val intent = rememberRequestBrowserIntent()
        val launcher = rememberAndroidQBrowserLauncher {
            if (it.resultCode == Activity.RESULT_OK) {
                updateDefaultBrowser()
            }
        }

        StatusCard(
            isDefaultBrowser = isDefaultBrowser,
            launchIntent = launchIntent,
            onSetAsDefault = { launcher.launch(intent) }
        )
    } else {
        StatusCard(
            isDefaultBrowser = isDefaultBrowser,
            launchIntent = launchIntent,
            onSetAsDefault = { launchIntent(MainViewModel.SettingsIntent.DefaultApps) }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
private fun rememberRequestBrowserIntent(): Intent {
    val context = LocalContext.current
    val roleManager = remember(context) { context.getSystemService<RoleManager>()!! }

    return remember(roleManager) {
        roleManager.createRequestRoleIntent(RoleManager.ROLE_BROWSER)
    }
}

@Composable
private fun rememberAndroidQBrowserLauncher(onResult: (ActivityResult) -> Unit): ManagedActivityResultLauncher<Intent, ActivityResult> {
    return rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = onResult
    )
}

@Composable
private fun StatusCardButton(
    @StringRes id: Int,
    buttonColor: Color,
    onClick: () -> Unit
) {
    Button(
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
        onClick = onClick
    ) {
        Text(text = stringResource(id = id))
    }
}

@Preview
@Composable
fun StatusCardPreview() {
    PreviewThemeNew {
        StatusCard(
            isDefaultBrowser = true,
            launchIntent = {},
            onSetAsDefault = {}
        )
    }
}

@Preview
@Composable
fun StatusCardPreviewNonDefault() {
    PreviewThemeNew {
        StatusCard(
            isDefaultBrowser = false,
            launchIntent = {},
            onSetAsDefault = {}
        )
    }
}
