//package cz.mendelu;
//
//import cz.mendelu.config.DamnsConfig;
//import cz.mendelu.dto.RoomDTO;
//import cz.mendelu.dto.StudentDTO;
//import cz.mendelu.service.RoomService;
//import cz.mendelu.service.StudentService;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//
//public class App {
//    public static void main(String[] args) {
//
//        ApplicationContext context =
//                new AnnotationConfigApplicationContext(DamnsConfig.class);
//
//        RoomService roomService = context.getBean(RoomService.class);
//        StudentService studentService = context.getBean(StudentService.class);
//
//        // Create and save a Room using RoomDTO
//        RoomDTO room = new RoomDTO();
//        room.setRoomNumber("A101");
//        room.setCapacity(2);
//        roomService.createRoom(room);
//
//        // Create and save a Student using StudentDTO
//        StudentDTO student = new StudentDTO();
//        student.setName("Alice Smith");
//        student.setEmail("alice@example.com");
//        studentService.createStudent(student);
//
//        // Print all rooms
//        System.out.println("All Rooms:");
//        System.out.println(roomService.getAllRooms());
//
//        // Print all students
//        System.out.println("All Students:");
//        System.out.println(studentService.getAllStudents());
//    }
//}
