package com.example.miniapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.miniapp.databinding.ActivityMainBinding
import com.example.miniapp.model.RoomRepository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var roomAdapter: RoomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        binding.fabAdd.setOnClickListener {
            // TODO: Open Add Room Screen
            Toast.makeText(this, "Chức năng Thêm phòng sẽ sớm ra mắt!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerView() {
        roomAdapter = RoomAdapter(
            rooms = RoomRepository.getAllRooms(),
            onItemClick = { position ->
                // TODO: Open Update Room Screen
                val room = RoomRepository.getAllRooms()[position]
                Toast.makeText(this, "Sửa: ${room.name}", Toast.LENGTH_SHORT).show()
            },
            onItemLongClick = { position ->
                showDeleteConfirmation(position)
            }
        )

        binding.rvRooms.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = roomAdapter
        }
    }

    private fun showDeleteConfirmation(position: Int) {
        val room = RoomRepository.getAllRooms()[position]
        AlertDialog.Builder(this)
            .setTitle("Xác nhận xóa")
            .setMessage("Bạn có chắc chắn muốn xóa ${room.name}?")
            .setPositiveButton("Xóa") { _, _ ->
                RoomRepository.deleteRoom(position)
                roomAdapter.notifyItemRemoved(position)
                roomAdapter.notifyItemRangeChanged(position, RoomRepository.getAllRooms().size)
                Toast.makeText(this, "Đã xóa ${room.name}", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    override fun onResume() {
        super.onResume()
        // Refresh list when returning to this screen
        roomAdapter.notifyDataSetChanged()
    }
}
