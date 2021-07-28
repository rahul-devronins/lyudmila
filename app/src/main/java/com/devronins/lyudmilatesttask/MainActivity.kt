package com.devronins.lyudmilatesttask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.devronins.lyudmilatesttask.databinding.MainActivityBinding
import com.devronins.lyudmilatesttask.ui.main.MainFragment
import com.devronins.lyudmilatesttask.ui.main.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
        registerViewModelObserver()
    }

    private fun registerViewModelObserver() {

        viewModel.showProgressBar.observe(this, {
            it?.run {
                if (it && binding.rlProgressBar.visibility == View.GONE) {
                    binding.rlProgressBar.visibility = View.VISIBLE
                } else if (binding.rlProgressBar.visibility == View.VISIBLE) {
                    binding.rlProgressBar.visibility = View.GONE
                }
                viewModel.showProgressBar.value = null
            }
        })

    }

}