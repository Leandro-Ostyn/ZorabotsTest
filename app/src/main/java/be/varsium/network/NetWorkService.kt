package be.varsium.network

import be.varsium.models.AdvancedComposer
import be.varsium.models.TimelineEntry
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

var okHttpClient: OkHttpClient = OkHttpClient.Builder()
    .connectTimeout(1, TimeUnit.MINUTES)
    .readTimeout(30, TimeUnit.SECONDS)
    .writeTimeout(15, TimeUnit.SECONDS)
    .build()

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .add(GenericCollectionAdapterFactory(ArrayList::class.java){ArrayList()})
    .build()


private val retrofit: Retrofit.Builder by lazy {
    Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
}

class ZorabotsApi(private val baseUrl: String) {
    val zoraBotsApiService: ZoraBotsApiService by lazy {
            retrofit.baseUrl(baseUrl)
            retrofit.build().create(ZoraBotsApiService::class.java)}
    }


interface ZoraBotsApiService {
    @GET(" ")
    fun getJsonsAsync(): Deferred<Response<AdvancedComposer>>
}

