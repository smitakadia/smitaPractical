package com.application.ui.activities
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.application.R
import com.application.databinding.ActSplashBinding
import com.application.db.DeliveryDatabase
import com.application.repository.DeliveryRepository
import com.application.utils.common.ActBase


class ActSplash : ActBase<ActSplashBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       bindView(R.layout.act_splash)
        openMainScreen()
    }

    private fun openMainScreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            /* Create an Intent that will start the Menu-Activity. */
            val intent = Intent(
                this@ActSplash,
                ActDeliveryList::class.java
            )
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            startActivity(intent)
            finish()
        }, 3500)
    }
}