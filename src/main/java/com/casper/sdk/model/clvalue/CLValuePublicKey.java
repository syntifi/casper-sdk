package com.casper.sdk.model.clvalue;

import com.casper.sdk.model.clvalue.cltype.CLTypePublicKey;
import com.casper.sdk.model.clvalue.encdec.CLValueDecoder;
import com.casper.sdk.model.clvalue.encdec.CLValueEncoder;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.casper.sdk.annotation.ExcludeFromJacocoGeneratedReport;
import com.casper.sdk.exception.CLValueDecodeException;
import com.casper.sdk.exception.NoSuchTypeException;
import com.casper.sdk.model.key.PublicKey;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Casper PublicKey CLValue implementation
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
public class CLValuePublicKey extends AbstractCLValue<PublicKey, CLTypePublicKey> {
    private CLTypePublicKey clType = new CLTypePublicKey();

    @JsonSetter("cl_type")
    @ExcludeFromJacocoGeneratedReport
    protected void setJsonClType(final CLTypePublicKey clType) {
        this.clType = clType;
    }

    @JsonGetter("cl_type")
    @ExcludeFromJacocoGeneratedReport
    protected String getJsonClType() {
        return this.getClType().getTypeName();
    }

    public CLValuePublicKey(final PublicKey value) {
        this.setValue(value);
    }

    @Override
    public void encode(final CLValueEncoder clve, final boolean encodeType) throws IOException, NoSuchTypeException {
        clve.writePublicKey(this);
        if (encodeType) {
            this.encodeType(clve);
        }
    }

    @Override
    public void decode(final CLValueDecoder clvd) throws IOException, CLValueDecodeException {
        try {
            clvd.readPublicKey(this);
        } catch (NoSuchAlgorithmException e) {
            throw new CLValueDecodeException("Error decoding CLValuePublicKey", e);
        }
    }
}
