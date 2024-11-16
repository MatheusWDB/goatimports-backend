package br.com.nexus.goat.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nexus.goat.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<List<Order>> findAllByAddressId(Long idAddress);
}
