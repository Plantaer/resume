package pl.mo.resume.ui.modules.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.shreyaspatil.MaterialDialog.MaterialDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pl.mo.resume.R
import pl.mo.resume.data.model.ParentInformation
import pl.mo.resume.data.state.DataState
import pl.mo.resume.databinding.ActivityMainBinding
import pl.mo.resume.ui.base.BindingActivity
import pl.mo.resume.ui.modules.details.DetailsActivity
import pl.mo.resume.ui.modules.main.adapter.ParentListAdapter
import pl.mo.resume.utils.*
import pl.mo.resume.utils.DurationConstants.ANIMATION_DURATION
import pl.mo.resume.utils.StringConstants.DETAILS_ERROR


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : BindingActivity<MainViewModel, ActivityMainBinding>() {

    override val mViewModel: MainViewModel by viewModels()

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    private val mAdapter = ParentListAdapter(this::onItemClicked)

    override fun onStart() {
        super.onStart()
        handleNetworkChanges()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme) // Set AppTheme before setting content view.

        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)

        initView()
        observeData()
    }

    private fun initView() {
        mViewBinding.run {
            parentRecyclerView.adapter = mAdapter

            swipeRefreshLayout.setOnRefreshListener { mViewModel.getAllInformations() }
        }
    }

    private fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.data.collect { value ->
                    when (value) {
                        is DataState.Loading -> showLoading(true)
                        is DataState.Success -> {
                            if (value.data.isNotEmpty()) {
                                mAdapter.submitList(value.data.toMutableList())
                                showLoading(false)
                            }
                        }
                        is DataState.Error -> {
                            showToast(value.message)
                            showLoading(false)
                        }
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        mViewBinding.swipeRefreshLayout.isRefreshing = isLoading
    }

    override fun onBackPressed() {
        MaterialDialog.Builder(this)
            .setTitle(getString(R.string.exit_dialog_title))
            .setMessage(getString(R.string.exit_dialog_message))
            .setPositiveButton(getString(R.string.option_yes)) { dialogInterface, _ ->
                dialogInterface.dismiss()
                super.onBackPressed()
            }
            .setNegativeButton(getString(R.string.option_no)) { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .build()
            .show()
    }

    private fun onItemClicked(parentInformation: ParentInformation, imageView: ImageView) {
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            imageView,
            imageView.transitionName
        )
        val parentId = parentInformation.id ?: run {
            showToast(DETAILS_ERROR)
            return
        }
        val intent = DetailsActivity.getStartIntent(this, parentId)
        startActivity(intent, options.toBundle())
    }

    private fun handleNetworkChanges() {
        NetworkUtils.getNetworkLiveData(applicationContext).observe(this) { isConnected ->
            if (!isConnected) {
                mViewBinding.textViewNetworkStatus.text =
                    getString(R.string.text_no_connectivity)
                mViewBinding.networkStatusLayout.apply {
                    show()
                    setBackgroundColor(getColorRes(R.color.colorStatusNotConnected))
                }
            } else {
                if (mAdapter.itemCount == 0) mViewModel.getAllInformations()
                mViewBinding.textViewNetworkStatus.text = getString(R.string.text_connectivity)
                mViewBinding.networkStatusLayout.apply {
                    setBackgroundColor(getColorRes(R.color.colorStatusConnected))

                    animate()
                        .alpha(1f)
                        .setStartDelay(ANIMATION_DURATION)
                        .setDuration(ANIMATION_DURATION)
                        .setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                hide()
                            }
                        })
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_theme -> {
                val mode =
                    if ((resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) ==
                        Configuration.UI_MODE_NIGHT_NO
                    ) {
                        AppCompatDelegate.MODE_NIGHT_YES
                    } else {
                        AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
                    }

                // Change UI Mode
                AppCompatDelegate.setDefaultNightMode(mode)
                true
            }
            R.id.action_share -> {
                val url = getString(R.string.profile_url)
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
                true
            }

            else -> true
        }
    }
}