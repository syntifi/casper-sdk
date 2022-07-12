package com.casper.sdk.model.clvalue;

import com.casper.sdk.model.clvalue.cltype.AbstractCLTypeWithChildren;
import com.casper.sdk.model.clvalue.cltype.CLTypeData;
import com.casper.sdk.model.clvalue.cltype.CLTypeTuple1;
import com.casper.sdk.model.clvalue.encdec.CLValueDecoder;
import com.casper.sdk.model.clvalue.encdec.CLValueEncoder;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.casper.sdk.exception.CLValueDecodeException;
import com.casper.sdk.exception.CLValueEncodeException;
import com.casper.sdk.exception.DynamicInstanceException;
import com.casper.sdk.exception.NoSuchTypeException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.javatuples.Unit;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

/**
 * Casper Tuple1 CLValue implementation
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
public class CLValueTuple1 extends AbstractCLValueWithChildren<Unit<? extends AbstractCLValue<?, ?>>, CLTypeTuple1> {
    @JsonProperty("cl_type")
    private CLTypeTuple1 clType = new CLTypeTuple1();

    public CLValueTuple1(final Unit<? extends AbstractCLValue<?, ?>> value) {
        this.setValue(value);
        setChildTypes();
    }

    @Override
    public void encode(final CLValueEncoder clve, final boolean encodeType) throws IOException, NoSuchTypeException, CLValueEncodeException {
        setChildTypes();
        getValue().getValue0().encode(clve, false);
        setBytes(getValue().getValue0().getBytes());
        if (encodeType) {
            this.encodeType(clve);
        }
    }

    @Override
    public void decode(final CLValueDecoder clvd)
            throws IOException, CLValueDecodeException, DynamicInstanceException, NoSuchTypeException {
        final CLTypeData childTypeData1 = clType.getChildClTypeData(0);

        final AbstractCLValue<?, ?> child1 = CLTypeData.createCLValueFromCLTypeData(childTypeData1);
        if (child1.getClType() instanceof AbstractCLTypeWithChildren) {
            ((AbstractCLTypeWithChildren) child1.getClType())
                    .setChildTypes(((AbstractCLTypeWithChildren) clType.getChildTypes().get(0)).getChildTypes());
        }
        child1.decode(clvd);

        setValue(new Unit<>(child1));
        setBytes(getValue().getValue0().getBytes());
    }

    @Override
    protected void setChildTypes() {
        clType.setChildTypes(Collections.singletonList(getValue().getValue0().getClType()));
    }
}
