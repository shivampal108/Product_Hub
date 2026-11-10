package com.product.hub.dtos.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AddressRequest {

    @NotBlank
    private String fullName;

    @NotBlank
    @Pattern(regexp = "^[6-9][0-9]{9}$",
             message = "Enter a valid 10-digit Indian mobile number")
    private String mobile;

    @NotBlank
    private String addressLine1;

    private String addressLine2;

    private String landmark;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    @NotBlank
    @Pattern(regexp = "^[1-9][0-9]{5}$",
             message = "Enter a valid 6-digit PIN code")
    private String postalCode;

    private String country = "India";

    @NotBlank
    private String addressType;

    private boolean defaultAddress;
}