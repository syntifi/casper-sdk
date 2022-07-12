package com.casper.sdk.jackson.deserializer;

import com.casper.sdk.exception.NoSuchTypeException;
import com.casper.sdk.model.clvalue.AbstractCLValue;
import com.casper.sdk.model.clvalue.cltype.CLTypeData;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.impl.AsPropertyTypeDeserializer;
import com.casper.sdk.jackson.resolver.CLValueResolver;

/**
 * Core Deserializer for the CLValue property. This deserializer is used by the {@link CLValueResolver}
 * to return the correct CLType object in Java depending on the cl_type sent over json
 *
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see AbstractCLValue
 * @since 0.0.1
 */
public class CLValueDeserializer extends AbstractAnyOfDeserializer {

    public CLValueDeserializer(final JavaType bt,
                               final TypeIdResolver idRes,
                               final String typePropertyName,
                               final boolean typeIdVisible,
                               final JavaType defaultImpl) {
        super(bt, idRes, typePropertyName, typeIdVisible, defaultImpl);
    }

    public CLValueDeserializer(final AsPropertyTypeDeserializer src, final BeanProperty property) {
        super(src, property);
    }

    @Override
    public TypeDeserializer forProperty(final BeanProperty prop) {
        return (prop == _property) ? this : new CLValueDeserializer(this, prop);
    }

    @Override
    protected JsonNode getTypeNode(final JsonNode currentNode) {
        return currentNode.get("cl_type");
    }

    @Override
    protected Class<?> getClassByName(final String anyOfType) throws NoSuchTypeException {
        return CLTypeData.getClassByName(anyOfType);
    }
}
