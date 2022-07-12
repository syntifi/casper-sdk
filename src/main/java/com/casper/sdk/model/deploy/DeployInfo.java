package com.casper.sdk.model.deploy;

import com.casper.sdk.model.uref.URef;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.casper.sdk.annotation.ExcludeFromJacocoGeneratedReport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.util.List;

/**
 * Information relating to the given Deploy
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
public class DeployInfo {

    /**
     * The relevant Deploy
     */
    @JsonProperty("deploy_hash")
    private String hash;

    /**
     * Account identifier of the creator of the Deploy.
     */
    private String from;

    /**
     * Gas cost of executing the Deploy.
     */
    @JsonIgnore
    private BigInteger gas;

    /**
     * Source purse used for payment of the Deploy.
     *
     * @see URef
     */
    private URef source;

    /**
     * Transfers performed by the Deploy.
     */
    private List<String> transfers;

    @JsonProperty("gas")
    @ExcludeFromJacocoGeneratedReport
    protected String getJsonGas() {
        return this.gas.toString(10);
    }

    @JsonProperty("gas")
    @ExcludeFromJacocoGeneratedReport
    protected void setJsonGas(final String value) {
        this.gas = new BigInteger(value, 10);
    }
}
