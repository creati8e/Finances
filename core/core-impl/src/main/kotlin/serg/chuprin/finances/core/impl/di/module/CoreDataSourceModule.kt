package serg.chuprin.finances.core.impl.di.module

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides

/**
 * Created by Sergey Chuprin on 04.04.2020.
 */
@Module
object CoreDataSourceModule {

    @Provides
    fun provideFirestore(): FirebaseFirestore = Firebase.firestore

}