package ru.mirea.armforpolyclinics.data

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.security.crypto.MasterKeys
import ru.mirea.armforpolyclinics.R

object TokenManager {

    private val TOKEN = "token"
    private val NAME  = "ARM for polyclinics"
    private lateinit var pref:SharedPreferences
    private lateinit var prefEditor:SharedPreferences.Editor

    fun setContext(context: Context) {
        //val masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        pref = EncryptedSharedPreferences.create(
            context,
            context.getString(R.string.app_name),
            MasterKey(context),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
         /*pref = EncryptedSharedPreferences.create(
            "secret_shared_prefs",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )*/

        prefEditor = pref.edit()

    }

    fun storeToken(token:String){
        prefEditor.putString(TOKEN, token)
        prefEditor.commit()
    }

    fun getToken(): String? {
        return pref.getString(TOKEN, TOKEN)
    }



}