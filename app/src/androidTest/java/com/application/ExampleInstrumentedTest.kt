package com.application

import androidx.lifecycle.viewModelScope
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.intent.Intents
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.application.db.DeliveryDatabase
import com.application.model.DeliveryItem
import com.application.repository.DeliveryRepository
import com.application.ui.activities.ActDeliveryList
import com.application.viewmodels.DeliverViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    private  lateinit var  scenario: ActivityScenario<ActDeliveryList>

    lateinit var viewModel: DeliverViewModel
    lateinit var database: DeliveryDatabase

    @Before
    fun start(){
        scenario= ActivityScenario.launch(ActDeliveryList::class.java)
    }

    @Test
    fun checkIfDeliveryIdIsValid(){
        scenario.onActivity {
            database = DeliveryDatabase.getDatabase(it)
            val repository = DeliveryRepository(it, "delivery.json", database)
            viewModel= DeliverViewModel(repository)

            viewModel.viewModelScope.launch (Dispatchers.IO){
                repository.readFile()
                assertEquals(true,(repository.deliveryList.value as List<DeliveryItem>)[0].id!=0)
            }
        }
    }
    @After
    fun tearDown(){
        Intents.release()
        scenario.close()
    }
}