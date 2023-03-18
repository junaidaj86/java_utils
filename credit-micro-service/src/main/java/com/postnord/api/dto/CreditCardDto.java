package com.postnord.api.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RegisterForReflection
public class CreditCardDto {

    String ownerName;
    @Pattern(regexp = "\\d{4}-\\d{4}-\\d{4}-\\d{4}", message = "Invalid credit card number!")
    String cardNumber;
    int expirationMonth;
    int expirationYear;
    @Min(value = 100)
    @Max(value = 999)
    int securityCode;
    float availableCredit = 50000;

    public void setExpirationDate(int year, int month) throws IllegalArgumentException {
        var calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        if (year < calendar.get(Calendar.YEAR) || (year == calendar.get(Calendar.YEAR) && month < calendar.get(Calendar.MONTH)))
            throw new IllegalArgumentException("expiration date must lie in the future");

        this.expirationMonth = month;
        this.expirationYear = year;
    }

}
