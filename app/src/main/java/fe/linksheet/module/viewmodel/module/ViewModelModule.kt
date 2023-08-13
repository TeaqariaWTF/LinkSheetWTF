package fe.linksheet.module.viewmodel.module

import fe.linksheet.module.viewmodel.Amp2HtmlSettingsViewModel
import fe.linksheet.module.viewmodel.AppsWhichCanOpenLinksViewModel
import fe.linksheet.module.viewmodel.BottomSheetSettingsViewModel
import fe.linksheet.module.viewmodel.BottomSheetViewModel
import fe.linksheet.module.viewmodel.CrashHandlerViewerViewModel
import fe.linksheet.module.viewmodel.DownloaderSettingsViewModel
import fe.linksheet.module.viewmodel.FeatureFlagViewModel
import fe.linksheet.module.viewmodel.FollowRedirectsSettingsViewModel
import fe.linksheet.module.viewmodel.InAppBrowserSettingsViewModel
import fe.linksheet.module.viewmodel.LibRedirectServiceSettingsViewModel
import fe.linksheet.module.viewmodel.LibRedirectSettingsViewModel
import fe.linksheet.module.viewmodel.LinksSettingsViewModel
import fe.linksheet.module.viewmodel.LogSettingsViewModel
import fe.linksheet.module.viewmodel.LogTextSettingsViewModel
import fe.linksheet.module.viewmodel.MainViewModel
import fe.linksheet.module.viewmodel.PreferredAppSettingsViewModel
import fe.linksheet.module.viewmodel.PreferredBrowserViewModel
import fe.linksheet.module.viewmodel.ThemeSettingsViewModel
import fe.linksheet.module.viewmodel.PretendToBeAppSettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get(), get(), get(named("feature_flags"))) }
    viewModel { AppsWhichCanOpenLinksViewModel(get(), get(), get(named("feature_flags"))) }
    viewModelOf(::PreferredAppSettingsViewModel)
    viewModelOf(::InAppBrowserSettingsViewModel)
    viewModelOf(::PreferredBrowserViewModel)
    viewModelOf(::BottomSheetSettingsViewModel)
    viewModelOf(::LinksSettingsViewModel)
    viewModelOf(::LibRedirectSettingsViewModel)
    viewModelOf(::LibRedirectServiceSettingsViewModel)
    viewModelOf(::BottomSheetViewModel)
    viewModelOf(::ThemeSettingsViewModel)
    viewModelOf(::FollowRedirectsSettingsViewModel)
    viewModelOf(::DownloaderSettingsViewModel)
    viewModelOf(::LogSettingsViewModel)
    viewModelOf(::LogTextSettingsViewModel)
    viewModelOf(::CrashHandlerViewerViewModel)
    viewModelOf(::Amp2HtmlSettingsViewModel)
    viewModel { FeatureFlagViewModel(get(), get(named("feature_flags"))) }
    viewModelOf(::PretendToBeAppSettingsViewModel)
}