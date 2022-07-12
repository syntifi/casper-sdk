package com.casper.sdk.model.storedvalue;

import com.casper.sdk.model.clvalue.AbstractCLValue;
import com.casper.sdk.model.clvalue.cltype.AbstractCLType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * Stored Value for {@link AbstractCLType}
 *
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see StoredValue
 * @since 0.0.1
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName("CLValue")
public class StoredValueCLValue implements StoredValue<AbstractCLValue<?, ?>> {
    @JsonProperty("CLValue")
    private AbstractCLValue<?, ?> value;

    @Override
    public boolean equals(final Object o) {
        if (o == this)
            return true;
        if (!(o instanceof StoredValueCLValue))
            return false;
        final StoredValueCLValue other = (StoredValueCLValue) o;
        if (!other.canEqual(this))
            return false;
        final Object this$value = this.getValue();
        final Object other$value = other.getValue();
        return Objects.equals(this$value, other$value);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof StoredValueCLValue;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $value = this.getValue();
        result = result * PRIME + ($value == null ? 43 : $value.hashCode());
        return result;
    }
}
