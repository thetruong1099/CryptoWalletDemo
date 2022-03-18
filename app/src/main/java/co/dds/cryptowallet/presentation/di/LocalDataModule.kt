package co.dds.cryptowallet.presentation.di

import android.app.Application
import androidx.room.Room
import co.dds.cryptowallet.data.dataSource.roomDB.NFTTokenDao
import co.dds.cryptowallet.data.dataSource.roomDB.WalletAddressDao
import co.dds.cryptowallet.data.dataSource.roomDB.WalletAddressDatabase
import co.dds.cryptowallet.presentation.until.Const
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val localDataModule = module {
    fun provideDatabase(application: Application): WalletAddressDatabase {
        return Room.databaseBuilder(
            application,
            WalletAddressDatabase::class.java,
            Const.databaseName
        ).fallbackToDestructiveMigration().build()
    }

    single { provideDatabase(androidApplication()) }
    single { provideWalletAddressDao(get()) }
    single { provideNFTTokenDao(get()) }
}

fun provideWalletAddressDao(database: WalletAddressDatabase): WalletAddressDao =
    database.walletAddressDao

fun provideNFTTokenDao(database: WalletAddressDatabase): NFTTokenDao = database.nftTokenDao