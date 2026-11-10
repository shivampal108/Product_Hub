package com.product.hub.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.product.hub.entity.Address;

public interface AddressRepository
        extends JpaRepository<Address, Long> {

    List<Address> findByUser_UserId(Long userId);

    Optional<Address> findByAddressIdAndUser_UserId(
            Long addressId, Long userId);

    Optional<Address> findByUser_UserIdAndDefaultAddressTrue(
            Long userId);
}