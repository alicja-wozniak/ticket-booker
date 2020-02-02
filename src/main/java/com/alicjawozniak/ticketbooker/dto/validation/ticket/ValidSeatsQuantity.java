package com.alicjawozniak.ticketbooker.dto.validation.ticket;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {SeatsQuantityValidator.class})
public @interface ValidSeatsQuantity {
    String message() default "{Invalid seats quantity}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
