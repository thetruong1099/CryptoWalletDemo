package co.dds.cryptowallet.presentation.di


import co.dds.cryptowallet.domain.usecase.generateWalletUseCase.*
import co.dds.cryptowallet.domain.usecase.nftTokenRoomDbUseCase.DeleteNftTokenUseCase
import co.dds.cryptowallet.domain.usecase.nftTokenRoomDbUseCase.GetAllNftTokenUseCase
import co.dds.cryptowallet.domain.usecase.nftTokenRoomDbUseCase.InsertNftTokenUseCase
import co.dds.cryptowallet.domain.usecase.nftUseCase.GetNftFileFromIPFSUseCase
import co.dds.cryptowallet.domain.usecase.nftUseCase.GetNftMetadataUseCase
import co.dds.cryptowallet.domain.usecase.sharePreferenceUseCase.*
import co.dds.cryptowallet.domain.usecase.transactionTokenUseCase.GetBalanceUseCase
import co.dds.cryptowallet.domain.usecase.transactionTokenUseCase.GetGasPriceUseCase
import co.dds.cryptowallet.domain.usecase.transactionTokenUseCase.SendTokenUseCase
import co.dds.cryptowallet.domain.usecase.walletAddressRoomDBUseCase.*
import org.koin.dsl.module

val useCaseModule = module {
    single { SetPassWordUseCase(get()) }
    single { GetPasswordUseCase(get()) }
    single { SaveLoginUseCase(get()) }
    single { GetStatusLoginSave(get()) }
    single { SaveMnemonicUseCase(get()) }
    single { GetMnemonicUseCase(get()) }

    single { GenerateCredentialUseCase(get()) }
    single { GenerateSeedPhraseUseCase(get()) }
    single { GenerateCheckValidateMnemonicUseCase(get()) }

    single { GetBalanceUseCase(get()) }
    single { GetGasPriceUseCase(get()) }
    single { SendTokenUseCase(get()) }

    single { InsertWalletAddressUseCase(get()) }
    single { GetWalletAddressByIdUseCase(get()) }
    single { DeleteDatabaseUseCase(get()) }
    single { ExistsAddressUseCase(get()) }
    single { GetPrivateKeyByIdUseCase(get()) }

    single { GetNftMetadataUseCase(get()) }
    single { GetNftFileFromIPFSUseCase(get()) }

    single { InsertNftTokenUseCase(get()) }
    single { DeleteNftTokenUseCase(get()) }
    single { GetAllNftTokenUseCase(get()) }
}