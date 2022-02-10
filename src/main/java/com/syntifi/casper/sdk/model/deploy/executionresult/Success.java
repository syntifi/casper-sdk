package com.syntifi.casper.sdk.model.deploy.executionresult;

import java.math.BigInteger;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.syntifi.casper.sdk.annotation.ExcludeFromJacocoGeneratedReport;
import com.syntifi.casper.sdk.model.deploy.ExecutionEffect;

import lombok.Data;

/**
 * Abstract Executable Result of type Success containing the details of the
 * contract execution. It shows the result of a successs execution
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 * @see ExecutionResult
 */
@Data
@JsonTypeName("Success")
public class Success implements ExecutionResult {

    /**
     * The cost of executing the deploy.
     */
    @JsonIgnore
    private BigInteger cost;

    /**
     * @see ExecutionEffect
     */
    private ExecutionEffect effect;

    /**
     * List of Hex-encoded transfer address.
     */
    private List<String> transfers;

    @JsonProperty("cost")
    @ExcludeFromJacocoGeneratedReport
    protected String getJsonCost() {
        return this.cost.toString(10);
    }

    @JsonProperty("cost")
    @ExcludeFromJacocoGeneratedReport
    protected void setJsonCost(String value) {
        this.cost = new BigInteger(value, 10);
    }
}