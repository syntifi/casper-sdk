package com.casper.sdk.model.clvalue;

import com.casper.sdk.model.clvalue.cltype.CLTypeAny;
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
 * Casper Object CLValue implementation
 *
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see AbstractCLValue
 * @since 0.0.1
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CLValueAny extends AbstractCLValue<Object, CLTypeAny> {
    private CLTypeAny clType = new CLTypeAny();

    @JsonSetter("cl_type")
    @ExcludeFromJacocoGeneratedReport
    protected void setJsonClType(CLTypeAny clType) {
        this.clType = clType;
    }

    @JsonGetter("cl_type")
    @ExcludeFromJacocoGeneratedReport
    protected String getJsonClType() {
        return this.getClType().getTypeName();
    }

    public CLValueAny(final Object value) {
        this.setValue(value);
    }

    @Override
    public void encode(final CLValueEncoder clve, final boolean encodeType) throws IOException, NoSuchTypeException {
        clve.writeAny(this);
        if (encodeType) {
            this.encodeType(clve);
        }
    }

    @Override
    public void decode(final CLValueDecoder clvd) throws IOException, CLValueDecodeException {
        clvd.readAny(this);
    }
}
