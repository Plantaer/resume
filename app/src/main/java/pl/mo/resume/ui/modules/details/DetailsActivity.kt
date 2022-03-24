package pl.mo.resume.ui.modules.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import pl.mo.resume.databinding.ActivityDetailsBinding
import pl.mo.resume.ui.base.BindingActivity
import pl.mo.resume.ui.modules.details.adapter.ChildListAdapter
import pl.mo.resume.utils.isNight
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DetailsActivity : BindingActivity<DetailsViewModel, ActivityDetailsBinding>() {

    companion object {
        private const val KEY_PARENT_ID = "parentId"

        fun getStartIntent(
            context: Context,
            parentId: Int
        ) = Intent(context, DetailsActivity::class.java).apply { putExtra(KEY_PARENT_ID, parentId) }
    }

    @Inject
    lateinit var viewModelFactory: DetailsViewModel.DetailsViewModelFactory

    override val mViewModel: DetailsViewModel by viewModels {
        val parentId = intent.extras?.getInt(KEY_PARENT_ID)
            ?: throw IllegalArgumentException("`parentId` must be non-null")

        DetailsViewModel.provideFactory(viewModelFactory, parentId)
    }

    private val mAdapter = ChildListAdapter()

    override fun getViewBinding(): ActivityDetailsBinding =
        ActivityDetailsBinding.inflate(layoutInflater)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)

        setSupportActionBar(mViewBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initView()
    }

    private fun initView() {
        mViewBinding.run {
            childRecyclerView.adapter = mAdapter
        }
        mViewModel.parentInformation.observe(this) { parent ->
            mViewBinding.imageView.load(if (isNight(this)) parent.imageUrlDark else parent.imageUrl)
            mViewBinding.parentTitle.text = parent.title
            mAdapter.submitList(parent.children)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                supportFinishAfterTransition()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}