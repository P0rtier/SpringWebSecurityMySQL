package pl.uzi.psspringbootstarttutorial.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.uzi.psspringbootstarttutorial.models.User;
import pl.uzi.psspringbootstarttutorial.repositories.UserRepository;

import java.util.NoSuchElementException;

@Service
public class UserManager {

    private final UserRepository userRepository;

    @Autowired
    public UserManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user){
        userRepository.saveAndFlush(user);
    }

    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public Iterable<User> findAll(){
        return userRepository.findAll();
    }

    public void delete(Long id){
        try {
            findById(id);
            userRepository.deleteById(id);

        }catch (NoSuchElementException e){
            System.err.println("No such element!");
        }
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

}
