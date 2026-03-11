package com.example.miniapp.model

/**
 * Repository to manage the temporary list of rooms.
 * This acts as the "Model" part of MVC that handles data.
 */
object RoomRepository {
    private val roomList = mutableListOf<Room>()

    init {
        // Add some initial data for testing
        roomList.add(Room("1", "Phòng 101", 1500000.0, false))
        roomList.add(Room("2", "Phòng 102", 1800000.0, true, "Nguyễn Văn A", "0901234567"))
        roomList.add(Room("3", "Phòng 103", 2000000.0, false))
    }

    fun getAllRooms(): MutableList<Room> {
        return roomList
    }

    fun addRoom(room: Room) {
        roomList.add(room)
    }

    fun updateRoom(index: Int, room: Room) {
        if (index in roomList.indices) {
            roomList[index] = room
        }
    }

    fun deleteRoom(index: Int) {
        if (index in roomList.indices) {
            roomList.removeAt(index)
        }
    }
}
