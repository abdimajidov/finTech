package uz.sardorbek.fintech.transaction.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.sardorbek.fintech.transaction.model.entity.Transaction;
import uz.sardorbek.fintech.user.model.entity.User;

import java.time.LocalDate;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Page<Transaction> findAllByCreated_DateBetween(LocalDate from, LocalDate to, Pageable pageable);

    Page<Transaction> findAllBySenderOrReceiver(User sender, User receiver, Pageable pageable);

}
