//package com.example.fitconnect.testData;
//
//import com.example.fitconnect.domain.chat.domain.ChatMessage;
//import com.example.fitconnect.domain.chat.domain.ChatRoom;
//import com.example.fitconnect.domain.event.domain.Category;
//import com.example.fitconnect.domain.event.domain.City;
//import com.example.fitconnect.domain.event.domain.ExerciseEvent;
//import com.example.fitconnect.domain.registration.Registration;
//import com.example.fitconnect.domain.user.domain.Role;
//import com.example.fitconnect.domain.user.domain.User;
//import com.example.fitconnect.domain.user.domain.UserBaseInfo;
//import com.example.fitconnect.dto.event.request.EventDetailDto;
//import com.example.fitconnect.dto.event.request.ExerciseEventRegistrationDto;
//import com.example.fitconnect.dto.event.request.LocationDto;
//import com.example.fitconnect.dto.event.request.RecruitmentPolicyDto;
//import com.example.fitconnect.repository.chat.chatMessage.ChatMessageRepository;
//import com.example.fitconnect.repository.chat.chatRoom.ChatRoomRepository;
//import com.example.fitconnect.repository.event.ExerciseEventRepository;
//import com.example.fitconnect.repository.registration.RegistrationRepository;
//import com.example.fitconnect.repository.user.UserRepository;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.Random;
//
//@Component
//@RequiredArgsConstructor
//public class CreateTestData implements CommandLineRunner {
//
//    private final UserRepository userRepository;
//    private final ExerciseEventRepository exerciseEventRepository;
//    private final RegistrationRepository registrationRepository;
//    private final ChatRoomRepository chatRoomRepository;
//    private final ChatMessageRepository chatMessageRepository;
//
//
//    @Override
//    @Transactional
//    public void run(String... args) throws Exception {
//        Random rand = new Random();
//        Category[] categories = Category.values();
//        City[] cities = City.values();
//
//        List<User> users = IntStream.rangeClosed(1, 10)
//                .mapToObj(i -> new User(
//                        new UserBaseInfo("user" + i + "@example.com", "User " + i, "url"),
//                        Role.MEMBER))
//                .collect(Collectors.toList());
//        userRepository.saveAll(users);
//
//        users.forEach(user -> {
//            for (int j = 0; j < categories.length; j++) {
//                ExerciseEvent event = createEvent(user, j, categories[j],
//                        cities[rand.nextInt(cities.length)]);
//                exerciseEventRepository.save(event);
//
//                if (j == 0) {
//                    ChatRoom chatRoom = new ChatRoom("Chat for Event " + event.getId(), event, user,
//                            null);
//                    chatRoomRepository.save(chatRoom);
//
//                    for (int k = 1; k <= 5; k++) {
//                        ChatMessage message = new ChatMessage(
//                                "Message " + k + " in chat room for event " + event.getId(),
//                                chatRoom, user);
//                        chatMessageRepository.save(message);
//                    }
//                }
//            }
//        });
//
//
//        List<ExerciseEvent> events = exerciseEventRepository.findAll();
//        users.forEach(user -> {
//            events.forEach(event -> {
//                if (!event.getOrganizer().equals(user)) {
//                    Registration registration = new Registration(user, event);
//                    registrationRepository.save(registration);
//                }
//            });
//        });
//    }
//
//    private ExerciseEvent createEvent(User user, int j, Category category, City city) {
//        return new ExerciseEventRegistrationDto(
//                new EventDetailDto("Event Title " + j, "Description for event " + j,
//                        LocalDateTime.now(),
//                        LocalDateTime.now().plusHours(2)),
//                new RecruitmentPolicyDto(j % 99 + 1, LocalDateTime.now(),
//                        LocalDateTime.now().plusDays(1)),
//                new LocationDto(city, "Address for event " + j),
//                category
//        ).toEntity(user,new ArrayList<>());
//    }
//}