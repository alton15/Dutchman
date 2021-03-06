package com.android.dutchman.ui.fragment.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.dutchman.R
import com.android.dutchman.databinding.FragmentPaymentBinding
import com.android.dutchman.domain.repository.payment.PaymentRepository
import com.android.dutchman.presentation.viewmodel.payment.PaymentViewModel
import com.android.dutchman.presentation.viewmodel.payment.PaymentViewModelFactory
import com.android.dutchman.ui.adapter.PaymentItemAdapter
import com.android.dutchman.util.DataBindingFragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_payment.*

class PaymentFragment : DataBindingFragment<FragmentPaymentBinding>(), PaymentRepository {

    override val layoutId: Int
        get() = R.layout.fragment_payment

    val compsite = CompositeDisposable()

    private val factory = PaymentViewModelFactory(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val viewModel = ViewModelProviders.of(activity!!, factory).get(PaymentViewModel::class.java)
        binding.vm = viewModel

//        payment_people_list_rv.layoutManager = LinearLayoutManager(context)
        payment_people_list_rv.adapter = PaymentItemAdapter(viewModel)

        return rootView
    }

    override fun onDestroy() {
        super.onDestroy()
        compsite.clear()
    }

}