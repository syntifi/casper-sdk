package com.casper.sdk.model.clvalue.cltype;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.casper.sdk.annotation.ExcludeFromJacocoGeneratedReport;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * CLType for {@link AbstractCLType#OPTION}
 *
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see AbstractCLType
 * @since 0.0.1
 */
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = {"typeName"})
public class CLTypeOption extends AbstractCLType {
    private final String typeName = OPTION;

    @Setter
    @JsonIgnore
    private AbstractCLType childType;

    @JsonSetter(OPTION)
    @ExcludeFromJacocoGeneratedReport
    protected void setJsonClType(final AbstractCLType clType) {
        this.childType = clType;
    }

    @JsonGetter(OPTION)
    @ExcludeFromJacocoGeneratedReport
    protected Object getJsonClType() {
        if (this.childType instanceof AbstractCLTypeBasic) {
            return this.childType.getTypeName();
        } else {
            return this.childType;
        }
    }
}
