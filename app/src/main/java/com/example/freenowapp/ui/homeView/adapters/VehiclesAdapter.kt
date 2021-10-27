package com.example.freenowapp.ui.homeView.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.freenowapp.R
import com.example.freenowapp.databinding.VehicalCardItemBinding
import com.example.freenowapp.remote.model.FleetType
import com.example.freenowapp.ui.homeView.uiModel.VehicleUIModel

private const val FLEET_TYPE_NOT_AVAILABLE = "NA"

class VehiclesAdapter(
    private var carsList: List<VehicleUIModel>,
    private val clickListener: VehiclesClickListener
) : RecyclerView.Adapter<VehiclesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.from(parent)

    override fun getItemCount(): Int = carsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(carsList[position], clickListener)
    }

    class ViewHolder private constructor(private val binding: VehicalCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: VehicleUIModel, clickListener: VehiclesClickListener) {
            with(binding) {
                val fleetType = item.fleetType?.name?.lowercase()
                vehicalType.text =
                    itemView.context.getString(
                        R.string.fleet_type,
                        fleetType ?: FLEET_TYPE_NOT_AVAILABLE
                    )

                Glide.with(itemView.context)
                    .asBitmap()
                    .fitCenter()
                    .load(
                        when (item.fleetType) {
                            FleetType.POOLING -> R.drawable.car_icon
                            else -> R.drawable.taxi_marker
                        }
                    )
                    .into(vehicalImage)
            }
            itemView.setOnClickListener {
                item.id?.let { id -> clickListener.onClick(item) }
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    VehicalCardItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class VehiclesClickListener(val clickListener: (vehicle: VehicleUIModel) -> Unit) {
        fun onClick(vehicle: VehicleUIModel) = clickListener(vehicle)
    }
}
