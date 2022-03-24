package pl.mo.resume.ui.modules.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import pl.mo.resume.data.model.ParentInformation
import pl.mo.resume.databinding.ItemParentInformationBinding
import pl.mo.resume.ui.modules.main.viewholder.ParentInformationViewHolder

class ParentListAdapter(private val onItemClicked: (ParentInformation, ImageView) -> Unit) : ListAdapter<ParentInformation, ParentInformationViewHolder>(DIFF_CALLBACK)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ParentInformationViewHolder(
        ItemParentInformationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ParentInformationViewHolder, position: Int) =
        holder.bind(holder.itemView.context, getItem(position), onItemClicked)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ParentInformation>() {
            override fun areItemsTheSame(oldItem: ParentInformation, newItem: ParentInformation): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ParentInformation, newItem: ParentInformation): Boolean =
                oldItem == newItem
        }
    }
}