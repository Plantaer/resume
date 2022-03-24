package pl.mo.resume.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

/**
 * Binds [ViewModel] [VM] and [ViewBinding] [VB]
 */
abstract class BindingActivity<VM : ViewModel, VB : ViewBinding> : AppCompatActivity() {

    protected abstract val mViewModel: VM

    protected lateinit var mViewBinding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewBinding = getViewBinding()
    }

    abstract fun getViewBinding(): VB
}