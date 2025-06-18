package cz.mendelu.service;

import cz.mendelu.domain.Room;
import cz.mendelu.dto.RoomDTO;
import cz.mendelu.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    private RoomDTO mapToDTO(Room room) {
        RoomDTO dto = new RoomDTO();
        dto.setId(room.getId());
        dto.setRoomNumber(room.getRoomNumber());
        dto.setCapacity(room.getCapacity());
        dto.setAvailable(room.isAvailable());
        return dto;
    }

    private Room mapToEntity(RoomDTO dto) {
        Room room = new Room();
        room.setId(dto.getId());
        room.setRoomNumber(dto.getRoomNumber());
        room.setCapacity(dto.getCapacity());
        room.setAvailable(dto.isAvailable());
        return room;
    }

    @Override
    public RoomDTO createRoom(RoomDTO dto) {
        Room room = mapToEntity(dto);
        return mapToDTO(roomRepository.save(room));
    }

    @Override
    public RoomDTO updateRoom(Long id, RoomDTO dto) {
        Room existing = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        existing.setRoomNumber(dto.getRoomNumber());
        existing.setCapacity(dto.getCapacity());
        existing.setAvailable(dto.isAvailable());
        return mapToDTO(roomRepository.save(existing));
    }

    @Override
    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }

    @Override
    public RoomDTO getRoomById(Long id) {
        return roomRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Room not found"));
    }

    @Override
    public List<RoomDTO> getAllRooms() {
        return roomRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RoomDTO upsertById(RoomDTO dto) {
        Room room = null;

        if (dto.getId() != null) {
            room = roomRepository.findById(dto.getId()).orElse(null);
            if (room != null) {
                room.setRoomNumber(dto.getRoomNumber());
                room.setCapacity(dto.getCapacity());
                room.setAvailable(dto.isAvailable());
            } else {
                // id exists in DTO but not in DB — treat as new insert
                room = mapToEntity(dto);
                room.setId(null); // explicitly null to avoid detached state conflict
            }
        } else {
            room = mapToEntity(dto);
        }

        return mapToDTO(roomRepository.save(room));
    }
}
