package com.casper.sdk.model.clvalue;

import com.casper.sdk.model.clvalue.cltype.CLTypeUnit;
import com.casper.sdk.model.clvalue.encdec.CLValueDecoder;
import com.casper.sdk.model.clvalue.encdec.CLValueEncoder;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.casper.sdk.annotation.ExcludeFromJacocoGeneratedReport;
import com.casper.sdk.exception.CLValueDecodeException;
import com.casper.sdk.exception.CLValueEncodeException;
import com.casper.sdk.exception.NoSuchTypeException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

/**
 * Casper Unit CLValue implementation
 * <p>
 * Unit is singleton value without additional semantics and serializes to an
 * empty byte array.
 *
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see AbstractCLValue
 * @since 0.0.1
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class CLValueUnit extends AbstractCLValue<Object, CLTypeUnit> {
    private static final String UNITY_EMPTY_VALUE = "";

    private CLTypeUnit clType = new CLTypeUnit();

    @JsonSetter("cl_type")
    @ExcludeFromJacocoGeneratedReport
    protected void setJsonClType(final CLTypeUnit clType) {
        this.clType = clType;
    }

    @JsonGetter("cl_type")
    @ExcludeFromJacocoGeneratedReport
    protected String getJsonClType() {
        return this.getClType().getTypeName();
    }

    public CLValueUnit() {
        this.setValue(UNITY_EMPTY_VALUE);
    }

    @Override
    public void encode(final CLValueEncoder clve, final boolean encodeType) throws NoSuchTypeException {
        setBytes(UNITY_EMPTY_VALUE);
        if (encodeType) {
            this.encodeType(clve);
        }
    }

    @Override
    public void decode(final CLValueDecoder clvd) {
        setBytes(UNITY_EMPTY_VALUE);
    }
}