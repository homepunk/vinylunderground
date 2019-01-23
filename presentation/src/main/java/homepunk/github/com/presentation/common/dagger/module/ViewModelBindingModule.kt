package homepunk.github.com.presentation.common.dagger.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import homepunk.github.com.presentation.common.dagger.ViewModelFactory
import homepunk.github.com.presentation.common.dagger.ViewModelKey
import homepunk.github.com.presentation.feature.discover.DiscoverHostViewModel
import homepunk.github.com.presentation.feature.discover.event.upcoming.EventListViewModel
import homepunk.github.com.presentation.feature.discover.library.DiscoverLibraryViewModel
import homepunk.github.com.presentation.feature.main.MainActivityViewModel
import homepunk.github.com.presentation.feature.mode.AppModeViewModel

/**Created by Homepunk on 27.12.2018. **/
@Module
interface ViewModelBindingModule {
    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class )
    fun bindMainActivityViewModel(viewModel: MainActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DiscoverLibraryViewModel::class )
    fun bindHomeFragmentViewModel(viewModel: DiscoverLibraryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EventListViewModel::class )
    fun bindUpcomingEventsViewModel(viewModel: EventListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AppModeViewModel::class )
    fun bindAppModeViewModel(viewModel: AppModeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DiscoverHostViewModel::class )
    fun bindDiscoverHostViewModel(viewModel: DiscoverHostViewModel): ViewModel
}