package org.money.transfers.repository;

import org.money.transfers.domain.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long>
{
    
}
