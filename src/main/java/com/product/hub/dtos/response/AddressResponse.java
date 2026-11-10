package com.product.hub.dtos.response;

import lombok.Data;

@Data
public class AddressResponse {

    private Long addressId;

    private String fullName;

    private String mobile;

    private String addressLine1;

    private String addressLine2;

    private String landmark;

    private String city;

    private String state;

    private String postalCode;

    private String country;

    private String addressType;

    private boolean defaultAddress;
}