package co.dds.cryptowallet.presentation.di

import co.dds.cryptowallet.presentation.until.AppDispatchers
import co.dds.cryptowallet.presentation.viewmodel.*
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    factory { AppDispatchers(Dispatchers.Main, Dispatchers.IO, Dispatchers.Default) }

    viewModel { SharePreferenceViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { GenerateWalletViewModel(get(), get(), get()) }
    viewModel { TransactionTokenViewModel(get(), get(), get()) }
    viewModel { WalletAddressRoomDBViewModel(get(), get(), get(), get(), get()) }
    viewModel { NFTViewModel(get(), get(), get(), get(), get()) }
    single { TempDataViewModel() }
}