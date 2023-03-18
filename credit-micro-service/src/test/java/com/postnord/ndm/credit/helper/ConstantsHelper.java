package com.postnord.ndm.credit.helper;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ConstantsHelper {

    public static final String CREDIT_CARD_PAYLOAD = "{\"ownerName\":\"aaa\",\"cardNumber\":\"1111-2222-3333-4444\",\"expirationMonth\":3,\"expirationYear\":1978,\"securityCode\":200,\"availableCredit\":60000}";
    public static final String BAD_CREDIT_CARD_PAYLOAD = "{\"ownerName\":\"aaa\",\"cardNumber\":\"1111-2222-3333-44444\",\"expirationMonth\":3,\"expirationYear\":1978,\"securityCode\":200,\"availableCredit\":60000}";
    public static final String CREDIT_CARD_PATH = "/credit";
    public static final String HTTP = "http://";

}
