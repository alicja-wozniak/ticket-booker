package com.alicjawozniak.ticketbooker.dto.validation.ticket;

import com.alicjawozniak.ticketbooker.dto.ticket.SeatsQuantityDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SeatsQuantityValidator implements ConstraintValidator<ValidSeatsQuantity, SeatsQuantityDto> {

    @Override
    public boolean isValid(
            final SeatsQuantityDto value,
            final ConstraintValidatorContext context
    ) {
        int typeQuantitiesSum = value.getTypeQuantities().values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();

        return value.getSeatIds().size() == typeQuantitiesSum
                && typeQuantitiesSum > 0;
    }
}