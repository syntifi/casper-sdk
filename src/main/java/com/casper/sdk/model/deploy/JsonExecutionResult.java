package com.casper.sdk.model.deploy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.casper.sdk.model.deploy.executionresult.ExecutionResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The execution result of a single deploy.
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
public class JsonExecutionResult {

    /**
     * The block hash.
     */
    @JsonProperty("block_hash")
    private String blockHash;

    /**
     * @see ExecutionResult
     */
    private ExecutionResult result;
}
