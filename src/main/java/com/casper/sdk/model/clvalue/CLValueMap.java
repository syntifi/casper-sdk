package com.casper.sdk.model.clvalue;

import com.casper.sdk.model.clvalue.cltype.AbstractCLTypeWithChildren;
import com.casper.sdk.model.clvalue.cltype.CLTypeData;
import com.casper.sdk.model.clvalue.cltype.CLTypeMap;
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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Casper Map CLValue implementation
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
public class CLValueMap extends
        AbstractCLValueWithChildren<Map<? extends AbstractCLValue<?, ?>, ? extends AbstractCLValue<?, ?>>, CLTypeMap> {
    @JsonProperty("cl_type")
    private CLTypeMap clType = new CLTypeMap();

    public CLValueMap(final Map<? extends AbstractCLValue<?, ?>, ? extends AbstractCLValue<?, ?>> value) {
        this.setValue(value);
        setChildTypes();
    }

    @Override
    public void encode(final CLValueEncoder clve, final boolean encodeType) throws IOException, NoSuchTypeException, CLValueEncodeException {
        setChildTypes();

        final CLValueI32 mapLength = new CLValueI32(getValue().size());
        mapLength.encode(clve, false);
        setBytes(mapLength.getBytes());

        for (Entry<? extends AbstractCLValue<?, ?>, ? extends AbstractCLValue<?, ?>> entry : getValue().entrySet()) {
            entry.getKey().encode(clve, false);
            entry.getValue().encode(clve, false);
            setBytes(getBytes() + entry.getKey().getBytes() + entry.getValue().getBytes());
        }
        if (encodeType) {
            this.encodeType(clve);
        }
    }

    @Override
    public void decode(final CLValueDecoder clvd)
            throws IOException, CLValueDecodeException, DynamicInstanceException, NoSuchTypeException {
        final CLTypeData keyType = clType.getKeyValueTypes().getKeyType().getClTypeData();
        final CLTypeData valType = clType.getKeyValueTypes().getValueType().getClTypeData();

        final Map<AbstractCLValue<?, ?>, AbstractCLValue<?, ?>> map = new LinkedHashMap<>();
        final CLValueI32 mapLength = new CLValueI32(0);
        mapLength.decode(clvd);

        for (int i = 0; i < mapLength.getValue(); i++) {
            final AbstractCLValue<?, ?> key = CLTypeData.createCLValueFromCLTypeData(keyType);
            if (key.getClType() instanceof AbstractCLTypeWithChildren) {
                ((AbstractCLTypeWithChildren) key.getClType())
                        .setChildTypes(
                                ((AbstractCLTypeWithChildren) clType.getKeyValueTypes().getKeyType()).getChildTypes());
            }
            key.decode(clvd);

            final AbstractCLValue<?, ?> val = CLTypeData.createCLValueFromCLTypeData(valType);
            if (val.getClType() instanceof AbstractCLTypeWithChildren) {
                ((AbstractCLTypeWithChildren) val.getClType())
                        .setChildTypes(((AbstractCLTypeWithChildren) clType.getKeyValueTypes().getValueType())
                                .getChildTypes());
            }
            val.decode(clvd);

            map.put(key, val);
        }

        setValue(map);
    }

    @Override
    protected void setChildTypes() {
        final Entry<? extends AbstractCLValue<?, ?>, ? extends AbstractCLValue<?, ?>> entry = getValue()
                .entrySet()
                .iterator()
                .next();

        clType.setKeyValueTypes(
                new CLTypeMap.CLTypeMapEntryType(entry.getKey().getClType(), entry.getValue().getClType())
        );
    }
}