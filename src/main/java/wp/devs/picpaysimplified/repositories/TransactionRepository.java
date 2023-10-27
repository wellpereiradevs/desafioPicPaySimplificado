package wp.devs.picpaysimplified.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import wp.devs.picpaysimplified.domain.user.transaction.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
