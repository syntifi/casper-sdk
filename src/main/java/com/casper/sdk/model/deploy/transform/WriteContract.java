package com.casper.sdk.model.deploy.transform;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

/**
 * An implementation of Transform that specifies any of the enum values
 * - Identity
 * - WriteContractWasm
 * - WriteContract
 * - WriteContractPackage
 *
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see Transform
 * @since 0.0.1
 */
@JsonTypeName("WriteContract")
public enum WriteContract implements Transform {
    @JsonProperty("Identity")
    @JsonUnwrapped
    IDENTITY,
    @JsonProperty("WriteContractWasm")
    @JsonUnwrapped
    WRITE_CONTRACT_WASM,
    @JsonProperty("WriteContract")
    @JsonUnwrapped
    WRITE_CONTRACT,
    @JsonProperty("WriteContractPackage")
    @JsonUnwrapped
    WRITE_CONTRACT_PACKAGE
}
