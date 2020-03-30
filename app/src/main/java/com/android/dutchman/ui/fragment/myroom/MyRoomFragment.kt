package com.android.dutchman.ui.fragment.myroom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.PagerAdapter
import com.android.dutchman.R
import com.android.dutchman.databinding.FragmentMyRoomBinding
import com.android.dutchman.databinding.FragmentNoticeBinding
import com.android.dutchman.domain.repository.posting.PostingRepository
import com.android.dutchman.presentation.model.MyRoomPagerModel
import com.android.dutchman.presentation.viewmodel.myroom.MyRoomViewModel
import com.android.dutchman.presentation.viewmodel.notice.NoticeViewModel
import com.android.dutchman.presentation.viewmodel.posting.PostingViewModelFactory
import com.android.dutchman.presentation.viewmodel.profileset.ProfileSetViewModelFactory
import com.android.dutchman.util.DataBindingFragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_my_room.*
import org.jetbrains.anko.find

class MyRoomFragment: DataBindingFragment<FragmentMyRoomBinding>(){

    override val layoutId: Int
    get() = R.layout.fragment_my_room

    val compsite = CompositeDisposable()

    private val fm: FragmentManager? by lazy { fragmentManager }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val viewModel = ViewModelProviders.of(this).get(MyRoomViewModel::class.java)

        viewModel.model.observe(this, Observer {
            setViewPager()
        })
        return rootView
    }

    fun setViewPager() {
        val models = arrayListOf(
            MyRoomPagerModel(
                R.drawable.back_profile_profileset,
                "이주용",
                "나랑 넷플릭스 할 4명~",
                "4명중 3명"
            )
        )
        myroom_list_pager.adapter = ApplyPageAdapter(models)
    }

    inner class ApplyPageAdapter(val models: ArrayList<MyRoomPagerModel>) : PagerAdapter() {

        override fun isViewFromObject(p0: View, p1: Any): Boolean = p0 == p1

        override fun getCount(): Int = models.size

        override fun destroyItem(container: ViewGroup, position: Int, any: Any) = container.removeView(any as View)

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val layoutInflater = LayoutInflater.from(context)
            val view = layoutInflater.inflate(R.layout.item_myroom_pager, container, false)
            view.find<ImageView>(R.id.item_myroom_profile_img).drawable = models[position].profileImg
            view.find<TextView>(R.id.item_myroom_name_tv).text = models[position].profileName
            view.find<TextView>(R.id.item_myroom_context_tv).text = models[position].postingContext
            view.find<TextView>(R.id.item_myroom_status_tv).text = models[position].postingParticipateStatus
            container.addView(view)
            return view
        }

        override fun startUpdate(container: ViewGroup) {
            super.startUpdate(container)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compsite.clear()
    }



}