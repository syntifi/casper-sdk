package com.casper.sdk.jackson.deserializer;

import com.casper.sdk.exception.NoSuchTypeException;
import com.casper.sdk.model.clvalue.AbstractCLValue;
import com.casper.sdk.model.deploy.transform.TransformTypeData;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.impl.AsPropertyTypeDeserializer;
import com.casper.sdk.jackson.resolver.CLValueResolver;

/**
 * Core Deserializer for the CLValue property. This deserializer is used by the
 * {@link CLValueResolver} to return the correct CLType object in Java depending
 * on the cl_type sent over json
 *
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see AbstractCLValue
 * @since 0.0.1
 */
public class TransformDeserializer extends AbstractAnyOfDeserializer {

    public TransformDeserializer(final JavaType bt, final TypeIdResolver idRes, final String typePropertyName,
                                 final boolean typeIdVisible, JavaType defaultImpl) {
        super(bt, idRes, typePropertyName, typeIdVisible, defaultImpl);
    }

    public TransformDeserializer(final AsPropertyTypeDeserializer src, final BeanProperty property) {
        super(src, property);
    }

    @Override
    public TypeDeserializer forProperty(final BeanProperty prop) {
        return (prop == _property) ? this : new TransformDeserializer(this, prop);
    }

    @Override
    protected Class<?> getClassByName(String anyOfType) throws NoSuchTypeException {
        return TransformTypeData.getClassByName(anyOfType);
    }
}
