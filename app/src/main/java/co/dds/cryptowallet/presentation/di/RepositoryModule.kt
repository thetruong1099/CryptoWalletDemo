package co.dds.cryptowallet.presentation.di

import co.dds.cryptowallet.data.repository.*
import co.dds.cryptowallet.domain.repository.*
import org.koin.dsl.module

val repositoryModule = module {
    single { SharePreferenceRepositoryImp(get()) as SharePreferenceRepository }
    single { GenerateWalletRepositoryImp(get()) as GenerateWalletRepository }
    single { TransactionTokenRepositoryImp(get()) as TransactionTokenRepository }
    single { WalletAddressRepositoryImp(get()) as WalletAddressRepository }
    single { TatumRepositoryImp(get(), get()) as TatumRepository }
    single { NftTokenRoomDBRepositoryImp(get()) as NftTokenRoomDBRepository }
}