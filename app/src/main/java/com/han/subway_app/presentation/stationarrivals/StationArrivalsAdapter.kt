package com.han.subway_app.presentation.stationarrivals

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.han.subway_app.databinding.ItemArrivalBinding
import com.han.subway_app.domain.ArrivalInformation

class StationArrivalsAdapter: RecyclerView.Adapter<StationArrivalsAdapter.ViewHolder>() {
    var data: List<ArrivalInformation> = emptyList()
    inner class ViewHolder(private val viewBinding: ItemArrivalBinding): RecyclerView.ViewHolder(viewBinding.root){
        @SuppressLint("SetTextI18n")
        fun bind(arrival: ArrivalInformation){
            viewBinding.labelTextView.badgeColor = arrival.subway.color
            viewBinding.labelTextView.text = "${arrival.subway.label} - ${arrival.destination}"
            viewBinding.destinationTextView.text = "🚩 ${arrival.destination}"
            viewBinding.arrivalMessageTextView.text = arrival.message
            viewBinding.arrivalMessageTextView.setTextColor(if (arrival.message.contains("당역")) Color.RED else Color.DKGRAY)
            viewBinding.updatedTimeTextView.text = "측정 시간: ${arrival.updatedAt}"
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StationArrivalsAdapter.ViewHolder =
        ViewHolder(ItemArrivalBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: StationArrivalsAdapter.ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

}