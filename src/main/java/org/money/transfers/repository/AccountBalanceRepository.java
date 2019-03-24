package org.money.transfers.repository;

import org.money.transfers.domain.AccountBalance;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import javax.persistence.LockModeType;

@Repository
public interface AccountBalanceRepository extends JpaRepository<AccountBalance, Long>
{
    @Query("select bln from AccountBalance bln, ClientAccount acc where bln.account = acc and acc.code = :code")
    Optional<AccountBalance> getBalanceByAccountCode(@Param("code") String accountCode);
    
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select bln from AccountBalance bln where bln.account = ( select acc from ClientAccount acc where acc.code = :code)")
    AccountBalance getBalanceByAccountCodeForWrite(@Param("code") String accountCode);
    
}
