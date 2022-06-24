package com.application.ui.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import com.application.R
import com.application.databinding.ActDeliveryDetailBinding
import com.application.model.DeliveryItem
import com.application.utils.common.ActBase
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class ActDeliveryDetail :  ActBase<ActDeliveryDetailBinding>(), OnMapReadyCallback {

    private lateinit var activity: Activity

    private lateinit var mMap: GoogleMap

    private var deliveryItem: DeliveryItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView(R.layout.act_delivery_detail)
        activity = this

        /*get data from intent*/
        getIntentData()

        /*initialize map*/
        initializeMap()


    }

    private fun initializeMap() {
        //Delivery object set on the view before set check using the scope function
        //If deliveritem is not null then and then only it will execute the code reside in block
        deliveryItem?.let {
            mBinding.txtDelivery.text = it.description
            mBinding.tvLoation.text = it.location.address
            Glide.with(activity)
                .load(it.imageUrl) // image url
                .placeholder(R.drawable.ic_res_placeholder)
                .error(R.drawable.ic_res_placeholder)// any placeholder to load at start
                .into(mBinding.ivDelivery)
        }
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun getIntentData() {

        if (intent != null) {
            deliveryItem = intent.getSerializableExtra("delivery") as DeliveryItem
        }
        mBinding.ivBack.setOnClickListener {
            finish()
        }
    }

    /**
     * used to set the marker on the map to when map is ready
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val latLng = LatLng(deliveryItem!!.location.lat, deliveryItem!!.location.lng)
        mMap.addMarker(
            MarkerOptions().position(latLng).title(deliveryItem!!.description)
        )
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,  4f))

        mapSmoothScroll()


    }

    private fun mapSmoothScroll() {

        mBinding.ivTransperent.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                val action = event?.action
                return when (action) {
                    MotionEvent.ACTION_DOWN -> {
                        /* Disallow ScrollView to intercept touch events. */
                        mBinding.nestedScroll.requestDisallowInterceptTouchEvent(true)
                        // Disable touch on transparent view
                        false
                    }
                    MotionEvent.ACTION_UP -> {
                        // Allow ScrollView to intercept touch events.
                        mBinding.nestedScroll.requestDisallowInterceptTouchEvent(false)
                        true
                    }
                    MotionEvent.ACTION_MOVE -> {
                        mBinding.nestedScroll.requestDisallowInterceptTouchEvent(true)
                        false
                    }
                    else ->// Allow ScrollView to intercept touch events.
                        // Disallow ScrollView to intercept touch events.
                        // Disable touch on transparent view
                    {
                        true
                    }
                }
            }
        })


    }

    /**
     * when press back button of the toolbar
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}