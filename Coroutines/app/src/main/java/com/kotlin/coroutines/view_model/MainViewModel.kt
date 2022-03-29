package com.kotlin.coroutines.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.coroutines.utils.ConstantUtils.Companion.TAG
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainViewModel : ViewModel() {

    init {
        runBlocking {
            // ViewModelScope
            val job = viewModelScope.launch {
                Log.d(TAG, "ViewModelScope: called")
            }
            job.join()


            // LiveData Scope
            // uses emit() // learn more
        }
    }
}