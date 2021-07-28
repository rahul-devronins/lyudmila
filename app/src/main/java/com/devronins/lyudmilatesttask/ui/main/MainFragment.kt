package com.devronins.lyudmilatesttask.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devronins.lyudmilatesttask.R
import com.devronins.lyudmilatesttask.data.ClassType
import com.devronins.lyudmilatesttask.data.Gym
import com.devronins.lyudmilatesttask.databinding.MainFragmentBinding
import com.devronins.lyudmilatesttask.helper.AppUtils
import com.devronins.lyudmilatesttask.helper.PreferenceUtils
import com.devronins.lyudmilatesttask.listener.AdapterListener

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding

    private val adapterListener = object : AdapterListener {
        override fun onRecyclerItemClick(view: View, position: Int) {
            when (view.id) {
                R.id.ivFavorite -> {
                    viewModel.gymItems[position].favorite =
                        !(viewModel.gymItems[position].favorite ?: false)
                    if (viewModel.gymItems[position].favorite == true) {
                        AppUtils.saveGymItemAsFavorite(viewModel.gymItems[position])
                    } else {
                        AppUtils.removeGymItemFromFavorite(viewModel.gymItems[position])
                    }
                    binding.rvGym.adapter?.notifyDataSetChanged()
                }
                R.id.cvClassType -> {
                    viewModel.classTypes[position].selected =
                        !(viewModel.classTypes[position].selected)
                    if (viewModel.classTypes[position].selected) {
                        AppUtils.saveSelectedType(viewModel.classTypes[position])
                    } else {
                        AppUtils.removeUnselectedType(viewModel.classTypes[position])
                    }
                    binding.rvClassTypes.adapter?.notifyDataSetChanged()
                }
                R.id.ivClassFavorite -> {
                    viewModel.popularClasses[position].favorite = !(viewModel.popularClasses[position].favorite ?: false)
                    if (viewModel.popularClasses[position].favorite == true) {
                        AppUtils.saveClassAsFavorite(viewModel.popularClasses[position])
                    } else {
                        AppUtils.removeClassAsFavorite(viewModel.popularClasses[position])
                    }
                    binding.rvPopularClasses.adapter?.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.run {
            viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        binding.model = viewModel
        binding.listener = adapterListener
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.prepareClassesType(requireContext())
        viewModel.parseAssetJsonData(requireContext())

        setViewListeners()
    }

    private fun setViewListeners() {

        binding.rvGym.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = binding.rvGym.layoutManager as LinearLayoutManager?

                val mVisibleItemCount = layoutManager!!.childCount
                val mTotalItemCount = layoutManager.itemCount
                val mPastVisibleItems = layoutManager.findFirstVisibleItemPosition()

                if (mPastVisibleItems + mVisibleItemCount >= mTotalItemCount) {
                    viewModel.addMoreGymItems()
                }
            }
        })

    }

}