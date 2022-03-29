package com.kotlin.coroutines

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.kotlin.coroutines.databinding.ActivityMainBinding
import com.kotlin.coroutines.utils.ConstantUtils.Companion.TAG
import kotlinx.coroutines.*

@DelicateCoroutinesApi
class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        scopes()
        contexts()
        extensionFunctions()
        suspendedFunctions()
    }

    private fun scopes() {
        /*
        *   Coroutines Scopes {CoroutineScope(), GlobalScope, viewModelScope, lifecycleScope, liveData
        */

        runBlocking {
            // Coroutine Scope
            val job = CoroutineScope(Dispatchers.Default).launch {
                Log.d(TAG, "CoroutineScope: called")
            }
            job.join()          // Job Completed

            // Global Scope
            val job2 = GlobalScope.launch {
                Log.d(TAG, "GlobalScope: called")
            }
            job2.join()          // Job Completed

            // LifeCycle Scope
            val job3 = lifecycleScope.launch {
                Log.d(TAG, "LifeCycleScope: called")
            }
            job3.join()          // Job Completed

            // Other two's are in ViewModel
        }
    }

    private fun contexts() {
        /*
        *   Coroutines Context (Dispatcher) {Main, IO (input-output/read-write/networking), Default (Complex and long-running calculations), Unconfined (Not specific to any thread)}
        */

        CoroutineScope(Dispatchers.Main).launch { }
        CoroutineScope(Dispatchers.IO).launch { }
        CoroutineScope(Dispatchers.Default).launch { }
        CoroutineScope(Dispatchers.Unconfined).launch { }
    }

    private fun extensionFunctions() {
        /*
        *   Coroutines Functions {launch, async, runBlocking}
        */

        // runBlocking works on main thread
        runBlocking {
            // launch has no return type (fire & forget)
            val job = CoroutineScope(Dispatchers.Default).launch { }
            job.join()

            // async has return type
            val deferredJob = CoroutineScope(Dispatchers.Default).async { return@async 1 }
            val awaitResult = deferredJob.await()
            Log.d(TAG, "extensionFunctions: async : $awaitResult")
        }
    }

    private fun suspendedFunctions() {
       /*
       *   yield: Check if coroutine has been cancelled or not (launch)
       *   ensureActive: Check if coroutine has been cancelled or not (launch)
       */



        runBlocking {
            val job = CoroutineScope(Dispatchers.Default).launch {
                yield()
                isActive
                delay(500)
                ensureActive()
            }
            job.join()
            job.isActive
            job.cancel()
        }
    }
}
