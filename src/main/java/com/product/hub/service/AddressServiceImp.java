
package com.product.hub.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.product.hub.dtos.request.AddressRequest;
import com.product.hub.dtos.response.AddressResponse;
import com.product.hub.entity.Address;
import com.product.hub.entity.User;
import com.product.hub.repository.AddressRepository;
import com.product.hub.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressServiceImp implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    // Get currently logged-in user
    private User getLoggedInUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                "anonymousUser".equals(authentication.getPrincipal())) {

            throw new RuntimeException("User is not authenticated");
        }

        return userRepository.findByUserName(authentication.getName());
    }

    // Make all existing addresses non-default
    private void clearDefaultAddress(Long userId) {

        List<Address> addresses =
                addressRepository.findByUser_UserId(userId);

        for (Address address : addresses) {
            address.setDefaultAddress(false);
        }
    }

    // Add address
    @Override
    public AddressResponse addAddress(AddressRequest request) {

        User user = getLoggedInUser();

        List<Address> existingAddresses =
                addressRepository.findByUser_UserId(user.getUserId());

        Address address = new Address();

        if (request.isDefaultAddress() || existingAddresses.isEmpty()) {

            clearDefaultAddress(user.getUserId());
            address.setDefaultAddress(true);

        } else {
            address.setDefaultAddress(false);
        }

        address.setUser(user);

        copyRequestToEntity(request, address);

        return convertToResponse(addressRepository.save(address));
    }

    // Get all addresses of logged-in user
    @Override
    @Transactional(readOnly = true)
    public List<AddressResponse> getMyAddresses() {

        User user = getLoggedInUser();

        return addressRepository.findByUser_UserId(user.getUserId())
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // Update address
    @Override
    public AddressResponse updateAddress(
            Long addressId, AddressRequest request) {

        User user = getLoggedInUser();

        Address address = addressRepository
                .findByAddressIdAndUser_UserId(
                        addressId, user.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("Address not found"));

        if (request.isDefaultAddress()) {

            clearDefaultAddress(user.getUserId());
            address.setDefaultAddress(true);
        }

        copyRequestToEntity(request, address);

        return convertToResponse(addressRepository.save(address));
    }

    // Delete address
    @Override
    public void deleteAddress(Long addressId) {

        User user = getLoggedInUser();

        Address address = addressRepository
                .findByAddressIdAndUser_UserId(
                        addressId, user.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("Address not found"));

        boolean wasDefault = address.isDefaultAddress();

        addressRepository.delete(address);

        if (wasDefault) {

            List<Address> remaining =
                    addressRepository.findByUser_UserId(user.getUserId());

            if (!remaining.isEmpty()) {
                remaining.get(0).setDefaultAddress(true);
            }
        }
    }

    // Set default address
    @Override
    public AddressResponse setDefaultAddress(Long addressId) {

        User user = getLoggedInUser();

        Address address = addressRepository
                .findByAddressIdAndUser_UserId(
                        addressId, user.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("Address not found"));

        clearDefaultAddress(user.getUserId());

        address.setDefaultAddress(true);

        return convertToResponse(addressRepository.save(address));
    }

    // Copy request DTO values to entity
    private void copyRequestToEntity(
            AddressRequest request, Address address) {

        address.setFullName(request.getFullName());
        address.setMobile(request.getMobile());
        address.setAddressLine1(request.getAddressLine1());
        address.setAddressLine2(request.getAddressLine2());
        address.setLandmark(request.getLandmark());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setPostalCode(request.getPostalCode());

        address.setCountry(
                request.getCountry() == null ||
                request.getCountry().isBlank()
                        ? "India"
                        : request.getCountry());

        address.setAddressType(request.getAddressType());
    }

    // Convert entity to response DTO
    private AddressResponse convertToResponse(Address address) {

        AddressResponse response = new AddressResponse();

        response.setAddressId(address.getAddressId());
        response.setFullName(address.getFullName());
        response.setMobile(address.getMobile());
        response.setAddressLine1(address.getAddressLine1());
        response.setAddressLine2(address.getAddressLine2());
        response.setLandmark(address.getLandmark());
        response.setCity(address.getCity());
        response.setState(address.getState());
        response.setPostalCode(address.getPostalCode());
        response.setCountry(address.getCountry());
        response.setAddressType(address.getAddressType());
        response.setDefaultAddress(address.isDefaultAddress());

        return response;
    }
}
