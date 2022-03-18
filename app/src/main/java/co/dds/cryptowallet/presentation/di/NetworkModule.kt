package co.dds.cryptowallet.presentation.di

import co.dds.cryptowallet.data.dataSource.api.AuthInterceptor
import co.dds.cryptowallet.data.dataSource.api.TatumApi
import co.dds.cryptowallet.presentation.until.Const
import co.dds.cryptowallet.presentation.until.ResponseHandler
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { Web3j.build(HttpService(Const.urlEthereumNetwork)) }

    factory { AuthInterceptor() }
    factory { provideOkHttpClient(get(), get()) }
    factory { provideForecastApi(get()) }
    factory { provideLoggingInterceptor() }
    single { provideRetrofit(get()) }
    factory { ResponseHandler() }
}

fun provideOkHttpClient(
    authInterceptor: AuthInterceptor,
    loggingInterceptor: HttpLoggingInterceptor
): OkHttpClient =
    OkHttpClient().newBuilder().addInterceptor(authInterceptor).addInterceptor(loggingInterceptor)
        .build()

fun provideForecastApi(retrofit: Retrofit): TatumApi = retrofit.create(TatumApi::class.java)

fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    val logger = HttpLoggingInterceptor()
    logger.level = HttpLoggingInterceptor.Level.BASIC
    return logger
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder().baseUrl(Const.urlApi).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()