package wp.devs.picpaysimplified.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import wp.devs.picpaysimplified.domain.user.TransactionDTO;
import wp.devs.picpaysimplified.domain.user.transaction.Transaction;
import wp.devs.picpaysimplified.repositories.TransactionRepository;


import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@Service
public class TransactionService {
    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private NotificationService notificationService;

    public Transaction createTransaction(TransactionDTO transaction) throws Exception {

        var payer = this.userService.findUserById(transaction.payerId());
        var payee = this.userService.findUserById(transaction.payeeId());

        userService.validateUser(payer, transaction.amount());

        boolean isAuthorized = isAuthorizedTransaction();

        if (!isAuthorized) {
            throw new Exception("Transação não autorizada");
        }

        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transaction.amount());
        newTransaction.setPayer(payer);
        newTransaction.setPayee(payee);
        newTransaction.setTransactionTime(LocalDateTime.now());

        payer.setBalance(payer.getBalance().subtract(transaction.amount()));
        payee.setBalance(payee.getBalance().add(transaction.amount()));

        this.repository.save(newTransaction);
        this.userService.saveUser(payer);
        this.userService.saveUser(payee);

        notificationService.sendNotification(payer, "Transação realizada com sucesso");
        notificationService.sendNotification(payee, "Transação recebida com sucesso");

        return newTransaction;
    }

    public boolean isAuthorizedTransaction() {
        var response = restTemplate.getForEntity("https://run.mocky.io/v3/9fafdd68-a090-496f-8c9a-3442cf30dae6", Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            String message = (String) Objects.requireNonNull(response.getBody()).get("message");
            return "Autorizado".equalsIgnoreCase(message);
        } else return false;
    }
}
