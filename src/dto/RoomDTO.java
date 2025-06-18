package cz.mendelu.dto;

public class RoomDTO {
    private Long id;
    private String roomNumber;
    private int capacity;
    private boolean available; // <-- Add this field

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isAvailable() { // <-- Add getter
        return available;
    }

    public void setAvailable(boolean available) { // <-- Add setter
        this.available = available;
    }
}
