package pl.mo.resume.ui.modules.details.viewholder

import android.content.Context
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import pl.mo.resume.R
import pl.mo.resume.data.model.ChildInformation
import pl.mo.resume.data.model.ParentInformation
import pl.mo.resume.databinding.ItemChildInformationBinding
import pl.mo.resume.utils.hide
import pl.mo.resume.utils.show

class ChildInformationViewHolder(private val binding: ItemChildInformationBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(childInformation: ChildInformation) {
        binding.title.text = childInformation.title
        if (childInformation.body != null) {
            binding.body.show()
            binding.body.text = childInformation.body
        } else binding.body.hide()

        if (childInformation.date != null) {
            binding.date.show()
            binding.date.text = childInformation.date
        } else binding.date.hide()
    }

}