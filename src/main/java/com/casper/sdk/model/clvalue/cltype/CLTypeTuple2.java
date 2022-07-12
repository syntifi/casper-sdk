package com.casper.sdk.model.clvalue.cltype;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.casper.sdk.exception.NoSuchTypeException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * CLType for {@link AbstractCLType#TUPLE2}
 *
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see AbstractCLType
 * @since 0.0.1
 */
@Getter
@EqualsAndHashCode(callSuper = true, of = {"typeName"})
public class CLTypeTuple2 extends AbstractCLTypeWithChildren {
    private final String typeName = TUPLE2;

    @Override
    @JsonProperty(TUPLE2)
    protected void setChildTypeObjects(final List<Object> childTypeObjects)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            NoSuchMethodException, SecurityException, NoSuchTypeException {
        super.setChildTypeObjects(childTypeObjects);
    }

    @Override
    @JsonProperty(TUPLE2)
    protected List<Object> getChildTypeObjects() {
        return super.getChildTypeObjects();
    }
}
