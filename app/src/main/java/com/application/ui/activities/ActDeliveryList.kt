package com.application.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.R
import com.application.ui.adapter.DeliveryAdapter
import com.application.databinding.ActDeliveryListBinding
import com.application.db.DeliveryDatabase
import com.application.model.DeliveryItem
import com.application.repository.DeliveryRepository
import com.application.utils.Status
import com.application.utils.common.ActBase
import com.application.viewmodels.DeliveryViewmodelFactory
import com.application.viewmodels.DeliverViewModel

class ActDeliveryList : ActBase<ActDeliveryListBinding>() {

    private lateinit var activity: Activity
    private lateinit var deliverViewModel: DeliverViewModel

    private var deliveryAdapter: DeliveryAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView(R.layout.act_delivery_list)
        activity = this

        /*initialize databse*/
        initializeDatbase()

        /**
         * Observing the data changes from the viewmodel once the data change in livedata it will appear on recyclerview.
         */
        observeLiveData()

        /*setup local search*/
        setSearch()


    }

    private fun setSearch() {
        mBinding.editSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                deliveryAdapter?.filter?.filter(p0?.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }


    private fun initializeDatbase() {
        val database = DeliveryDatabase.getDatabase(activity)
        val repository = DeliveryRepository(activity, "delivery.json", database)
        deliverViewModel = ViewModelProvider(
            this,
            DeliveryViewmodelFactory(repository)
        )[DeliverViewModel::class.java]

    }

    private fun observeLiveData() {
        deliverViewModel.deliveryList.observe(this) { it ->
            when (it.status) {
                Status.LOADING -> {
                    //PRE LOADING FOR THE ASYNC TASK
                    mBinding.progressBar.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    //IF FILE READ SUCCESSFULLY
                    mBinding.progressBar.visibility = View.GONE
                    mBinding.recyclerDelivery.layoutManager = LinearLayoutManager(activity)
                    mBinding.recyclerDelivery.itemAnimator = DefaultItemAnimator()
                    it.data?.let { deliveryList ->
                        deliveryAdapter = DeliveryAdapter(activity, deliveryList)
                        mBinding.recyclerDelivery.adapter = deliveryAdapter

                        deliveryAdapter!!.setOnDeliveryClickListener(object :
                            DeliveryAdapter.OnDeliveryClickListener {
                            override fun onDeliveryClick(deliveryItem: DeliveryItem) {
                                Intent(activity, ActDeliveryDetail::class.java).also {
                                    it.putExtra("delivery", deliveryItem)
                                    startActivity(it)
                                }
                            }
                        })
                    }
                }
                Status.ERROR -> {
                    //IF ANY EXCEPTION OCCURS IT WILL SHOW HERE
                    mBinding.progressBar.visibility = View.GONE
                    Toast.makeText(activity, it.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}