package com.casper.sdk.model.clvalue;

import com.casper.sdk.model.clvalue.cltype.AbstractCLTypeWithChildren;
import com.casper.sdk.model.clvalue.cltype.CLTypeData;
import com.casper.sdk.model.clvalue.cltype.CLTypeOption;
import com.casper.sdk.model.clvalue.encdec.CLValueDecoder;
import com.casper.sdk.model.clvalue.encdec.CLValueEncoder;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.casper.sdk.exception.CLValueDecodeException;
import com.casper.sdk.exception.CLValueEncodeException;
import com.casper.sdk.exception.DynamicInstanceException;
import com.casper.sdk.exception.NoSuchTypeException;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.Optional;

/**
 * Casper Option CLValue implementation
 *
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see AbstractCLValue
 * @since 0.0.1
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class CLValueOption extends AbstractCLValue<Optional<AbstractCLValue<?, ?>>, CLTypeOption> {
    @JsonProperty("cl_type")
    private CLTypeOption clType = new CLTypeOption();

    public CLValueOption() {
        this(Optional.of(new CLValueAny(null)));
    }

    public CLValueOption(@SuppressWarnings("OptionalUsedAsFieldOrParameterType") final Optional<AbstractCLValue<?, ?>> value) {
        this.setValue(value);
        this.clType.setChildType(value.map(AbstractCLValue::getClType).orElse(null));
    }

    @Override
    public void encode(final CLValueEncoder clve, final boolean encodeType) throws IOException, NoSuchTypeException, CLValueEncodeException {
        final Optional<AbstractCLValue<?, ?>> value = getValue();

        final CLValueBool isPresent = new CLValueBool(value.isPresent() && value.get().getValue() != null);
        isPresent.encode(clve, false);
        setBytes(isPresent.getBytes());

        final Optional<AbstractCLValue<?, ?>> child = getValue();

        if (child.isPresent() && child.get().getClType() instanceof AbstractCLTypeWithChildren) {
            ((AbstractCLTypeWithChildren) child.get().getClType())
                    .setChildTypes(((AbstractCLTypeWithChildren) clType.getChildType()).getChildTypes());
        }
        if (child.isPresent() && isPresent.getValue().equals(Boolean.TRUE)) {
            child.get().encode(clve, false);
            setBytes(getBytes() + child.get().getBytes());
        }
        if (encodeType) {
            this.encodeType(clve);
            if (child.isPresent() && isPresent.getValue().equals(Boolean.TRUE)) {
                child.get().encodeType(clve);
            }
        }
    }

    @Override
    public void decode(final CLValueDecoder clvd)
            throws IOException, CLValueDecodeException, DynamicInstanceException, NoSuchTypeException {
        final CLValueBool isPresent = new CLValueBool();
        isPresent.decode(clvd);
        setBytes(isPresent.getBytes());

        final CLTypeData childTypeData = clType.getChildType().getClTypeData();

        final AbstractCLValue<?, ?> child = CLTypeData.createCLValueFromCLTypeData(childTypeData);

        if (child.getClType() instanceof AbstractCLTypeWithChildren) {
            ((AbstractCLTypeWithChildren) child.getClType())
                    .setChildTypes(((AbstractCLTypeWithChildren) clType.getChildType()).getChildTypes());
        }

        setValue(Optional.of(child));

        if (isPresent.getValue().equals(Boolean.TRUE)) {
            child.decode(clvd);
            setBytes(getBytes() + child.getBytes());
        }
    }
}
