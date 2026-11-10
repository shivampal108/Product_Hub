package com.product.hub.service;

import java.util.List;

import com.product.hub.dtos.request.AddressRequest;
import com.product.hub.dtos.response.AddressResponse;


public interface AddressService {

    AddressResponse addAddress(AddressRequest request);

    List<AddressResponse> getMyAddresses();

    AddressResponse updateAddress(
            Long addressId, AddressRequest request);

    void deleteAddress(Long addressId);

    AddressResponse setDefaultAddress(Long addressId);
}