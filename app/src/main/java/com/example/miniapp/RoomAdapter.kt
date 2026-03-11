package com.example.miniapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.miniapp.databinding.ItemRoomBinding
import com.example.miniapp.model.Room
import java.text.NumberFormat
import java.util.Locale

class RoomAdapter(
    private var rooms: List<Room>,
    private val onItemClick: (Int) -> Unit,
    private val onItemLongClick: (Int) -> Unit
) : RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

    inner class RoomViewHolder(private val binding: ItemRoomBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(room: Room, position: Int) {
            binding.tvRoomName.text = room.name
            
            val formatter = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))
            binding.tvPrice.text = "Giá: ${formatter.format(room.price)}"

            if (room.isRented) {
                binding.tvStatus.text = "Đã thuê"
                binding.tvStatus.setTextColor(Color.RED)
                binding.tvTenant.visibility = View.VISIBLE
                binding.tvTenant.text = "Người thuê: ${room.tenantName ?: "N/A"}"
            } else {
                binding.tvStatus.text = "Còn trống"
                binding.tvStatus.setTextColor(Color.GREEN)
                binding.tvTenant.visibility = View.GONE
            }

            binding.root.setOnClickListener { onItemClick(position) }
            binding.root.setOnLongClickListener {
                onItemLongClick(position)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val binding = ItemRoomBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RoomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.bind(rooms[position], position)
    }

    override fun getItemCount(): Int = rooms.size

    fun updateData(newRooms: List<Room>) {
        this.rooms = newRooms
        notifyDataSetChanged()
    }
}
