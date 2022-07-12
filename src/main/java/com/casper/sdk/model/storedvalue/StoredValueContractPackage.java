package com.casper.sdk.model.storedvalue;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.casper.sdk.model.contract.ContractPackage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Stored Value for {@link ContractPackage}
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
@EqualsAndHashCode
@JsonTypeName("ContractPackage")
public class StoredValueContractPackage implements StoredValue<ContractPackage> {
    @JsonProperty("ContractPackage")
    public ContractPackage value;
}
