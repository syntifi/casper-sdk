package com.casper.sdk.model.deploy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.casper.sdk.annotation.ExcludeFromJacocoGeneratedReport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

/**
 * Info about a seigniorage allocation for a validator
 *
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonSubTypes({@JsonSubTypes.Type(value = Validator.class, name = "Validator"),
        @JsonSubTypes.Type(value = Delegator.class, name = "Delegator")})
public class SeigniorageAllocation {

    /**
     * Allocated amount
     */
    @JsonIgnore
    private BigInteger amount;

    @JsonProperty("amount")
    @ExcludeFromJacocoGeneratedReport
    protected String getJsonAmount() {
        return this.amount.toString(10);
    }

    @JsonProperty("amount")
    @ExcludeFromJacocoGeneratedReport
    protected void setJsonAmount(final String value) {
        this.amount = new BigInteger(value, 10);
    }
}
