package com.casper.sdk.model.clvalue;

import com.casper.sdk.model.clvalue.cltype.AbstractCLTypeWithChildren;
import com.casper.sdk.model.clvalue.cltype.CLTypeData;
import com.casper.sdk.model.clvalue.cltype.CLTypeList;
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

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Casper List CLValue implementation
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
public class CLValueList extends AbstractCLValue<List<? extends AbstractCLValue<?, ?>>, CLTypeList> {
    @JsonProperty("cl_type")
    private CLTypeList clType = new CLTypeList();

    public CLValueList(final List<? extends AbstractCLValue<?, ?>> value) {
        this.setValue(value);
        setListType();
    }

    @Override
    public void encode(final CLValueEncoder clve, final boolean encodeType) throws IOException, NoSuchTypeException, CLValueEncodeException {
        setListType();

        // List length is written first
        final CLValueI32 length = new CLValueI32(getValue().size());
        length.encode(clve, false);
        setBytes(length.getBytes());

        for (AbstractCLValue<?, ?> child : getValue()) {
            child.encode(clve, false);
            setBytes(getBytes() + child.getBytes());
        }
        if (encodeType) {
            this.encodeType(clve);
        }
    }

    @Override
    public void decode(CLValueDecoder clvd)
            throws IOException, CLValueDecodeException, DynamicInstanceException, NoSuchTypeException {
        final CLTypeData childrenType = getClType().getListType().getClTypeData();

        // List length is sent first
        final CLValueI32 length = new CLValueI32();
        length.decode(clvd);
        setBytes(length.getBytes());

        final List<AbstractCLValue<?, ?>> list = new LinkedList<>();
        for (int i = 0; i < length.getValue(); i++) {
            final AbstractCLValue<?, ?> child = CLTypeData.createCLValueFromCLTypeData(childrenType);
            if (child.getClType() instanceof AbstractCLTypeWithChildren) {
                ((AbstractCLTypeWithChildren) child.getClType())
                        .setChildTypes(((AbstractCLTypeWithChildren) clType.getListType()).getChildTypes());
            }
            child.decode(clvd);
            setBytes(getBytes() + child.getBytes());
            list.add(child);
        }

        setValue(list);
    }

    protected void setListType() {
        clType.setListType(getValue().get(0).getClType());
    }
}
