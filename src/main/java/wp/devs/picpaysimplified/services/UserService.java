package wp.devs.picpaysimplified.services;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wp.devs.picpaysimplified.domain.user.UserDTO;
import wp.devs.picpaysimplified.domain.user.User;
import wp.devs.picpaysimplified.domain.user.UserType;
import wp.devs.picpaysimplified.repositories.UserRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user){
        this.userRepository.save(user);
    }
    public User createUser(UserDTO user){
        User newUser = new User(user);
        this.saveUser(newUser);
        return newUser;
    }

    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }

    public User findUserById(Long id) throws Exception{
        return this.userRepository.findById(id).orElseThrow(() -> new Exception("Usuário não encontrado"));
    }

    @SneakyThrows
    public void validateUser(User payer, BigDecimal amount){
        if(payer.getUserType() == UserType.MERCHANT){
            throw new Exception("Usuário LOJISTA não pode realizar transações");
        }
        if(payer.getBalance().compareTo(amount) < 0){
            throw new Exception("Saldo Insuficente");
        }
    }
}