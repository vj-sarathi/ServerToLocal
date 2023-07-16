package com.vjsarathi.servertolocal.di

import android.app.Application
import androidx.room.Room
import com.vjsarathi.servertolocal.feature_room.data.data_source.UsersDatabase
import com.vjsarathi.servertolocal.feature_room.data.remote.data_source.RemoteApi
import com.vjsarathi.servertolocal.feature_room.data.remote.repository.RemoteUserRepoImpl
import com.vjsarathi.servertolocal.feature_room.data.repository.UserRepoImpl
import com.vjsarathi.servertolocal.feature_room.domain.remote.repository.RemoteUserRepo
import com.vjsarathi.servertolocal.feature_room.domain.remote.use_case.GetAllUsersFromServer
import com.vjsarathi.servertolocal.feature_room.domain.repository.UserRepo
import com.vjsarathi.servertolocal.feature_room.domain.use_case.GetAllUsersUseCase
import com.vjsarathi.servertolocal.feature_room.domain.use_case.InsertUser
import com.vjsarathi.servertolocal.feature_room.domain.use_case.UsersListPageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUsersDatabase(context: Application): UsersDatabase {
        return Room.databaseBuilder(
            context, UsersDatabase::class.java, UsersDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideUserRepo(
        db: UsersDatabase,
    ): UserRepo {
        return UserRepoImpl(db.roomDao)
    }

    @Provides
    @Singleton
    fun provideUsersListUseCase(
        repo: UserRepo
    ): UsersListPageUseCase {
        return UsersListPageUseCase(
            getAllUsers = GetAllUsersUseCase(repo),
            insertNewUser = InsertUser(repo),
        )
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        var httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor = httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
      /*  if (BuildConfig.Debug) {
            httpLoggingInterceptor = httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
        }*/
        client.addInterceptor(httpLoggingInterceptor)
        return client.build()
    }

    @Provides
    @Singleton
    fun provideRemoteApi(
        client: OkHttpClient
    ): RemoteApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
//            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(RemoteApi.BASE_URL)
            .build()
            .create(RemoteApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteUserRepo(
        remoteApi: RemoteApi,
    ): RemoteUserRepo {
        return RemoteUserRepoImpl(remoteApi)
    }

    @Provides
    @Singleton
    fun provideGetAllUsersFromServer(
        remoteUserRepo: RemoteUserRepo
    ): GetAllUsersFromServer = GetAllUsersFromServer(remoteUserRepo)


}