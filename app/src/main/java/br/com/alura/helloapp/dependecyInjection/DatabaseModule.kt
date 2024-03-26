package br.com.alura.helloapp.dependecyInjection

import android.content.Context
import br.com.alura.helloapp.room.repository.ContatoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule{
    @Provides
    fun provideContatoRepository(@ApplicationContext context: Context): ContatoRepository{
        return ContatoRepository(context = context)
    }

}