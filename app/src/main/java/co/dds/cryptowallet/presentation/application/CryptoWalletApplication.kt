package co.dds.cryptowallet.presentation.application

import android.app.Application
import co.dds.cryptowallet.presentation.di.*
import co.dds.cryptowallet.presentation.until.Const
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CryptoWalletApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKotlin()
    }

    private fun initKotlin() {
        startKoin {
            androidContext(this@CryptoWalletApplication)
            modules(
                listOf(
                    viewModelModule,
                    repositoryModule,
                    useCaseModule,
                    networkModule,
                    localDataModule
                )
            )
        }
    }
}