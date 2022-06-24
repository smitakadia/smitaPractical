package com.application

import androidx.lifecycle.viewModelScope
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.intent.Intents
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

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class ExampleInstrumentedTest {

}
