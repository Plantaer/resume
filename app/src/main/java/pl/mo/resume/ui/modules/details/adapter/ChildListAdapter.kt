package pl.mo.resume.ui.modules.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import pl.mo.resume.data.model.ChildInformation
import pl.mo.resume.databinding.ItemChildInformationBinding
import pl.mo.resume.ui.modules.details.viewholder.ChildInformationViewHolder

class ChildListAdapter : ListAdapter<ChildInformation, ChildInformationViewHolder>(DIFF_CALLBACK)  {

    override fun onCreateViewHolder(child: ViewGroup, viewType: Int) = ChildInformationViewHolder(
        ItemChildInformationBinding.inflate(
            LayoutInflater.from(child.context),
            child,
            false
        )
    )

    override fun onBindViewHolder(holder: ChildInformationViewHolder, position: Int) =
        holder.bind(getItem(position))

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ChildInformation>() {
            override fun areItemsTheSame(oldItem: ChildInformation, newItem: ChildInformation): Boolean =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: ChildInformation, newItem: ChildInformation): Boolean =
                oldItem == newItem
        }
    }
}