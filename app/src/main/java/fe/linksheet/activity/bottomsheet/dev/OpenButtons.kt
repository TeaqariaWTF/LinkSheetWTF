package fe.linksheet.activity.bottomsheet.dev

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import fe.linksheet.R
import fe.linksheet.extension.compose.currentActivity
import fe.linksheet.module.viewmodel.BottomSheetViewModel
import fe.linksheet.resolver.BottomSheetResult
import fe.linksheet.ui.HkGroteskFontFamily

@Composable
fun OpenButtons(
    bottomSheetViewModel: BottomSheetViewModel,
    onClick: (Boolean) -> Unit
) {
    val activity = LocalContext.currentActivity()
    val result = bottomSheetViewModel.resolveResult!!
    if (result !is BottomSheetResult.BottomSheetSuccessResult) return

    if (!result.isEmpty) {
        Row(modifier = Modifier.wrapContentHeight().padding(start = 15.dp, end = 15.dp)) {
            OpenButton(outlined = true, textId = R.string.just_once, onClick = { onClick(false) })
            Spacer(modifier = Modifier.width(5.dp))
            OpenButton(outlined = false, textId = R.string.always, onClick = { onClick(true) })
        }
    } else {
        // TODO: Move out of Composable
        ElevatedOrTextButton(
            onClick = { bottomSheetViewModel.startMainActivity(activity) },
            textButton = bottomSheetViewModel.useTextShareCopyButtons(),
            buttonText = R.string.open_settings
        )
    }
}

@Composable
private fun RowScope.OpenButton(
    @StringRes textId: Int,
    outlined: Boolean,
    onClick: () -> Unit
) {
    val modifier = Modifier
        .fillMaxWidth()
        .weight(0.5f)
    val content: @Composable RowScope.() -> Unit = {
        Text(
            text = stringResource(id = textId),
            fontFamily = HkGroteskFontFamily,
            maxLines = 1,
            fontWeight = FontWeight.SemiBold
        )
    }

    if (outlined) {
        OutlinedButton(modifier = modifier, onClick = onClick, content = content)
    } else {
        Button(modifier = modifier, onClick = onClick, content = content)
    }
}