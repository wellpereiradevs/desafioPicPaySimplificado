package wp.devs.picpaysimplified.domain.user;

import java.math.BigDecimal;
public record TransactionDTO(BigDecimal amount, Long payerId, Long payeeId) {
}