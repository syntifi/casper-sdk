package com.casper.sdk.model.clvalue;

import com.casper.sdk.model.clvalue.cltype.CLTypeU8;
import com.casper.sdk.model.clvalue.encdec.CLValueDecoder;
import com.casper.sdk.model.clvalue.encdec.CLValueEncoder;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.casper.sdk.annotation.ExcludeFromJacocoGeneratedReport;
import com.casper.sdk.exception.CLValueDecodeException;
import com.casper.sdk.exception.NoSuchTypeException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;

/**
 * Casper U8 CLValue implementation
 *
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see AbstractCLValue
 * @since 0.0.1
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CLValueU8 extends AbstractCLValue<Byte, CLTypeU8> {
    private CLTypeU8 clType = new CLTypeU8();

    @JsonSetter("cl_type")
    @ExcludeFromJacocoGeneratedReport
    protected void setJsonClType(final CLTypeU8 clType) {
        this.clType = clType;
    }

    @JsonGetter("cl_type")
    @ExcludeFromJacocoGeneratedReport
    protected String getJsonClType() {
        return this.getClType().getTypeName();
    }

    public CLValueU8(final Byte value) {
        this.setValue(value);
    }

    @Override
    public void encode(final CLValueEncoder clve, final boolean encodeType) throws IOException, NoSuchTypeException {
        clve.writeU8(this);
        if (encodeType) {
            this.encodeType(clve);
        }
    }

    @Override
    public void decode(final CLValueDecoder clvd) throws IOException, CLValueDecodeException {
        clvd.readU8(this);
    }
}
