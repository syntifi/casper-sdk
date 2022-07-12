package com.casper.sdk.model.deploy.executionresult;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Abstract Executable Result containing the details of the contract execution.
 * It can be any of the following types:
 *
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see Failure
 * @see Success
 * @since 0.0.1
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Failure.class, name = "Failure"),
        @JsonSubTypes.Type(value = Success.class, name = "Success")
})
public interface ExecutionResult {
}
