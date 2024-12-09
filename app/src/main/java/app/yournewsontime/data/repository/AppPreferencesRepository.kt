package app.yournewsontime.data.repository

import android.content.Context
import app.yournewsontime.R

class AppPreferencesRepository(context: Context) {
    private val prefs = context.getSharedPreferences(
        context.getString(R.string.app_prefs),
        Context.MODE_PRIVATE
    )

    fun isFirstLaunch(): Boolean {
        return prefs.getBoolean("is_first_launch", true)
    }

    fun setFirstLaunch(isFirst: Boolean) {
        prefs.edit().putBoolean("is_first_launch", isFirst).apply()
    }

    fun isBiometricEnabled(): Boolean {
        return prefs.getBoolean("biometric_enabled", false)
    }

    fun setBiometricEnabled(enabled: Boolean) {
        prefs.edit().putBoolean("biometric_enabled", enabled).apply()
    }
}