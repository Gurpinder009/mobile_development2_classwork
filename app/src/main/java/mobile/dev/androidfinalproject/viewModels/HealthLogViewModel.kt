package mobile.dev.androidfinalproject.viewModels

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.google.android.gms.tasks.OnFailureListener
import mobile.dev.androidfinalproject.dbHelpers.HealthLogsDbHelper
import mobile.dev.androidfinalproject.models.HealthLogsModel
import mobile.dev.androidfinalproject.utilities.SingletonFirebaseAuth

class HealthLogViewModel : ViewModel() {

    private val _healthLog = MutableLiveData<HealthLogsModel>()



    fun getHealthLog(): LiveData<HealthLogsModel> {
        return _healthLog
    }


    private fun setHealthLog(healthLog: HealthLogsModel) {

        _healthLog.value = healthLog
        Log.i("log1", "setHealthLog: ${_healthLog.value}")
    }

    fun fetchHealthLog() {
        HealthLogsDbHelper.getHealthLog(
            successListener = { result -> run {
                var dataTemp = HealthLogsModel(SingletonFirebaseAuth.getInstance().getCurrentUser().email!!)
                if(result.documents.isEmpty()){
                    HealthLogsDbHelper.postHealthLog(
                        dataTemp,
                        successListener = {

                        },
                        failureListener = {}
                    )
                }
                else {

                    val data = result.documents.first().data
                    dataTemp = HealthLogsModel.toHealthLog(data!!)


                }
                this.setHealthLog(dataTemp)


            } }, failureListener = { error -> run {} },)

    }



    fun updateHealthLog(updatedHealthLog: HealthLogsModel,successListener: () -> Unit,failureListener: (er:Exception)->Unit) {
        HealthLogsDbHelper.updateHealthLog(
            updatedHealthLog,
            successListener = {
                setHealthLog(updatedHealthLog)
                successListener()
            },
            failureListener = failureListener
        )
    }
}