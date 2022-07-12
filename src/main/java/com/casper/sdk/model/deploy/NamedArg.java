package com.casper.sdk.model.deploy;

import com.casper.sdk.exception.CLValueEncodeException;
import com.casper.sdk.exception.NoSuchTypeException;
import com.casper.sdk.model.clvalue.*;
import com.casper.sdk.model.clvalue.cltype.AbstractCLType;
import com.casper.sdk.model.clvalue.encdec.CLValueEncoder;
import com.casper.sdk.model.clvalue.encdec.interfaces.EncodableValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;

/**
 * Named arguments to a contract
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
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
public class NamedArg<P extends AbstractCLType> implements EncodableValue {

    /**
     * The first value in the array is the type of the arg
     */
    private String type;

    /**
     * The second value in the array is a CLValue type
     */
    private AbstractCLValue<?, P> clValue;

    @Override
    public void encode(final CLValueEncoder clve, final boolean encodeType)
            throws IOException, CLValueEncodeException, NoSuchTypeException {
        clve.writeString(type);
        if (clValue instanceof CLValueI32 || clValue instanceof CLValueU32) {
            clve.writeInt(32 / 8);
        } else if (clValue instanceof CLValueI64 || clValue instanceof CLValueU64) {
            clve.writeInt(64 / 8);
        } else if (clValue instanceof CLValueU128
                || clValue instanceof CLValueU256
                || clValue instanceof CLValueU512
                || clValue instanceof CLValuePublicKey
                || clValue instanceof CLValueOption) {
            final CLValueEncoder localEncoder = new CLValueEncoder();
            clValue.encode(localEncoder, false);
            final int size = localEncoder.toByteArray().length;
            clve.writeInt(size); //removing the CLValue type byte at the end
        }
        clValue.encode(clve, encodeType);
    }
}
