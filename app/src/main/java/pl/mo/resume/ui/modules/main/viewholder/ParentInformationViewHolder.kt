package pl.mo.resume.ui.modules.main.viewholder

import android.content.Context
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import pl.mo.resume.R
import pl.mo.resume.data.model.ParentInformation
import pl.mo.resume.databinding.ItemParentInformationBinding
import pl.mo.resume.utils.isNight

class ParentInformationViewHolder(private val binding: ItemParentInformationBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        context: Context,
        parentInformation: ParentInformation,
        onItemClicked: (ParentInformation, ImageView) -> Unit
    ) {
        binding.title.text = parentInformation.title
        binding.imageView.load(if (isNight(context)) parentInformation.imageUrlDark else parentInformation.imageUrl) {
            placeholder(R.drawable.ic_photo)
            error(R.drawable.ic_photo)
        }
        binding.root.setOnClickListener {
            onItemClicked(parentInformation, binding.imageView)
        }
    }
}