package com.stopstone.scrollmovetop.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.stopstone.scrollmovetop.R
import com.stopstone.scrollmovetop.model.User
import com.stopstone.scrollmovetop.databinding.ActivityMainBinding
import com.stopstone.scrollmovetop.ui.adapter.UserAdapter


class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val adapter: UserAdapter by lazy { UserAdapter() }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (!recyclerView.canScrollVertically(SCROLL_TOP)) {
                hideScrollTopButton()
            } else if (binding.btnScrollTop.visibility == View.GONE) {
                showScrollTopButton()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setRecyclerView()
        setListeners()
        loadSampleData()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.recyclerView.removeOnScrollListener(scrollListener)
    }

    private fun setRecyclerView() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addOnScrollListener(scrollListener)

    }

    private fun setListeners() {
        binding.btnScrollTop.setOnClickListener {
            binding.recyclerView.smoothScrollToPosition(0)
        }
    }

    private fun loadSampleData() {
        val items = List(30) { index ->
            User(
                profileImage = R.drawable.ic_profile_placeholder,
                name = "User ${index + 1}"
            )
        }
        adapter.submitList(items)
    }

    private fun hideScrollTopButton() {
        binding.btnScrollTop.animate()
            .alpha(0f)
            .withEndAction { binding.btnScrollTop.visibility = View.GONE }
    }

    private fun showScrollTopButton() {
        with(binding.btnScrollTop) {
            visibility = View.VISIBLE
            alpha = 0f
            animate().alpha(1f)
        }
    }

    companion object {
        private const val SCROLL_TOP = -1
    }

}
