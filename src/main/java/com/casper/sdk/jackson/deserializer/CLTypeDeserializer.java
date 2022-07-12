package com.casper.sdk.jackson.deserializer;

import com.casper.sdk.exception.NoSuchTypeException;
import com.casper.sdk.model.clvalue.cltype.AbstractCLType;
import com.casper.sdk.model.clvalue.cltype.CLTypeData;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.impl.AsPropertyTypeDeserializer;
import com.casper.sdk.jackson.resolver.CLTypeResolver;

/**
 * Core Deserializer for the CLType property. This deserializer is used by the
 * {@link CLTypeResolver} to return the correct CLType object in Java depending
 * on the cl_type sent over json
 *
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see AbstractCLType
 * @since 0.0.1
 */
public class CLTypeDeserializer extends AbstractAnyOfDeserializer {

    public CLTypeDeserializer(final JavaType bt,
                              final TypeIdResolver idRes,
                              final String typePropertyName,
                              final boolean typeIdVisible,
                              final JavaType defaultImpl) {
        super(bt, idRes, typePropertyName, typeIdVisible, defaultImpl);
    }

    public CLTypeDeserializer(final AsPropertyTypeDeserializer src, final BeanProperty property) {
        super(src, property);
    }

    @Override
    public TypeDeserializer forProperty(final BeanProperty prop) {
        return (prop == _property) ? this : new CLTypeDeserializer(this, prop);
    }

    @Override
    protected Class<?> getClassByName(final String anyOfType) throws NoSuchTypeException {
        return CLTypeData.getCLTypeClassByName(anyOfType);
    }
}
