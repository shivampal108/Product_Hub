package com.product.hub.controller;



import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.product.hub.dtos.request.AddressRequest;
import com.product.hub.dtos.response.AddressResponse;
import com.product.hub.service.AddressService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api-address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    // Add new address
    @PostMapping("/add")
    public ResponseEntity<AddressResponse> addAddress(
            @Valid @RequestBody AddressRequest request) {

        AddressResponse response =
                addressService.addAddress(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // Get all addresses of logged-in user
    @GetMapping("/my-addresses")
    public ResponseEntity<List<AddressResponse>> getMyAddresses() {

        List<AddressResponse> addresses =
                addressService.getMyAddresses();

        return ResponseEntity.ok(addresses);
    }

    // Update existing address
    @PutMapping("/update/{addressId}")
    public ResponseEntity<AddressResponse> updateAddress(
            @PathVariable Long addressId,
            @Valid @RequestBody AddressRequest request) {

        AddressResponse response =
                addressService.updateAddress(addressId, request);

        return ResponseEntity.ok(response);
    }

    // Delete address
    @DeleteMapping("/delete/{addressId}")
    public ResponseEntity<String> deleteAddress(
            @PathVariable Long addressId) {

        addressService.deleteAddress(addressId);

        return ResponseEntity.ok(
                "Address deleted successfully");
    }

    // Set default address
    @PutMapping("/default/{addressId}")
    public ResponseEntity<AddressResponse> setDefaultAddress(
            @PathVariable Long addressId) {

        AddressResponse response =
                addressService.setDefaultAddress(addressId);

        return ResponseEntity.ok(response);
    }
}
