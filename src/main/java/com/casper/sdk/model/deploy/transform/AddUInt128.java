package com.casper.sdk.model.deploy.transform;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.casper.sdk.annotation.ExcludeFromJacocoGeneratedReport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * An implementation of Transform that Adds the given `u128`
 * 
 * @see Transform
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName("AddUInt128")
public class AddUInt128 implements Transform {

    /**
     * u128
     */
    @JsonIgnore
    private BigInteger u128;

    /**
     * getter for u128 json serialization
     *
     * @return cost as expected for json serialization
     */
    @JsonProperty("AddUInt128")
    @ExcludeFromJacocoGeneratedReport
    protected String getJsonU128() {
        return this.u128.toString(10);
    }

    /**
     * setter for u128 from json deserialized value
     *
     * @param value the deserialized value
     */
    @JsonProperty("AddUInt128")
    @ExcludeFromJacocoGeneratedReport
    protected void setJsonU128(final String value) {
        this.u128 = new BigInteger(value, 10);
    }
}
