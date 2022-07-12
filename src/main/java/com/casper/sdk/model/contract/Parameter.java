package com.casper.sdk.model.contract;

import com.casper.sdk.annotation.ExcludeFromJacocoGeneratedReport;
import com.casper.sdk.model.clvalue.cltype.AbstractCLType;
import com.casper.sdk.model.clvalue.cltype.AbstractCLTypeBasic;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Parameter to a method
 *
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Parameter {

    /**
     * cl_type(CLType) The value of the entry: a casper `Key` type.
     */
    @JsonIgnore
    private AbstractCLType clType;

    /**
     * name(String) The name of the entry.
     */
    @JsonProperty("name")
    private String name;

    @JsonSetter("cl_type")
    @ExcludeFromJacocoGeneratedReport
    protected void setJsonClType(AbstractCLType clType) {
        this.clType = clType;
    }

    /**
     * The accessor for jackson serialization
     *
     * @return String if cl_type is basic type, CLType object if not.
     */
    @JsonGetter("cl_type")
    @ExcludeFromJacocoGeneratedReport
    protected Object getJsonClType() {
        if (this.clType instanceof AbstractCLTypeBasic) {
            return this.clType.getTypeName();
        } else {
            return this.clType;
        }
    }
}
