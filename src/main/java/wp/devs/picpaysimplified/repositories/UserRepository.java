package wp.devs.picpaysimplified.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import wp.devs.picpaysimplified.domain.user.User;
import wp.devs.picpaysimplified.domain.user.transaction.Transaction;

public interface UserRepository extends JpaRepository<User, Long > {
    void saveUser(Transaction newTransaction);
}
