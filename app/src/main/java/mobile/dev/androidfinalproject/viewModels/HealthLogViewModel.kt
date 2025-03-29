package mobile.dev.androidfinalproject.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mobile.dev.androidfinalproject.dbHelpers.HealthLogsDbHelper
import mobile.dev.androidfinalproject.models.HealthLogsModel

class HealthLogViewModel : ViewModel() {

    private val _healthLog = MutableLiveData<HealthLogsModel>()
    val healthLog: LiveData<HealthLogsModel> get() = _healthLog

    fun setHealthLog(healthLog: HealthLogsModel) {
        _healthLog.value = healthLog
    }

    fun fetchHealthLog() {
        HealthLogsDbHelper.getHealthLog(successListener = { result ->
            val healthLogMap = result.documents.firstOrNull()?.data
            healthLogMap?.let {
                _healthLog.value = HealthLogsModel.toHealthLog(it)
            }
        }, failureListener = { error ->
            // Handle error
        })
    }
}