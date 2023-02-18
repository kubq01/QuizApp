package com.example.QuizApp.data.users;

import com.example.QuizApp.data.Class.Class;
import com.example.QuizApp.data.Class.ClassRepository;
import com.example.QuizApp.data.quizes.TeacherQuiz;
import com.example.QuizApp.data.quizes.TeacherQuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

        private final UserRepository repo;

        private final PasswordEncoder encoder;
        private final ClassRepository classRepository;
        private final TeacherQuizRepository quizRepository;

        @Autowired
        public UserService(UserRepository repo, PasswordEncoder encoder,
                           ClassRepository classRepository,
                           TeacherQuizRepository quizRepository) {
            this.repo = repo;
            this.encoder = encoder;
            this.classRepository = classRepository;
            this.quizRepository = quizRepository;
        }
        public List<User> showAll()
        {
            return repo.findAll();
        }
        public void insert(User user)
        {
            User encodedUser = encodePassword(user);
            repo.save(encodedUser);
        }

        public void updatePassword(User user, String password)
        {
            user.setPassword(password);
            User encodedUser = encodePassword(user);
            repo.save(encodedUser);
        }

        public List<User> showByType(UserType type)
        {
            return repo.findByType(type.name());
        }

        @Transactional
        public void deleteUserById(Long id){
            Optional<User> userOpt = repo.findById(id);

            if (userOpt.isEmpty()){
                return;
            }
            User user = userOpt.get();
            switch (user.getClass().getSimpleName()){
                case "Admin":
                    break;
                case "Teacher":
                    List<Class> teacherClasses = classRepository.findAllByTeacher((Teacher) user);
                    for (Class myClass:
                            teacherClasses) {
                        myClass.setTeacher(null);
                    }
                    List<TeacherQuiz> teacherQuizes = quizRepository.findAllByTeacher((Teacher) user);
                    for (TeacherQuiz quiz:
                            teacherQuizes) {
                        quiz.setTeacher(null);
                    }
                    break;
                case "Student":
                    break;
                }
                repo.deleteById(id);

                /*Optional<User> deletedUserOpt = repo.findById(id);
                if (deletedUserOpt.isPresent()) {
                    User deletedUser = deletedUserOpt.get();
                    deletedUser.setActive(false);
                    deletedUser.setPassword("@#^$#$@%^%^#%#^%$%#$%^&%$#%@%$%^^$&#%$^%");
                }*/
           }

       public User showByID(Long ID)
        {
            Optional<User> user = repo.findById(ID);
            if(user.isPresent()) {
                return user.get();
            }else
                return null;
        }

        User encodePassword(User user){
            String password = user.getPassword();
            user.setPassword(encoder.encode(password));
            return user;
        }
}
