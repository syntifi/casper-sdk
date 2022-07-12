package com.casper.sdk.model.deploy.executabledeploy;

import com.casper.sdk.model.clvalue.encdec.CLValueEncoder;
import com.casper.sdk.model.deploy.NamedArg;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.casper.sdk.exception.CLValueEncodeException;
import com.casper.sdk.exception.DynamicInstanceException;
import com.casper.sdk.exception.NoSuchTypeException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.util.List;

/**
 * Abstract Executable Deploy Item containing the StoredContractByName.
 *
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see ExecutableDeployItem
 * @since 0.0.1
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName("StoredContractByName")
public class StoredContractByName implements ExecutableDeployItem {

    /**
     * Contract name
     */
    private String name;

    /**
     * Entry Point
     */
    @JsonProperty("entry_point")
    private String entryPoint;

    /**
     * @see NamedArg
     */
    private List<NamedArg<?>> args;

    /**
     * {@link ExecutableDeployItem} order 2
     */
    @Override
    public byte getOrder() {
        return 0x2;
    }

    /**
     * Implements the StoredContractByHash encoder
     */
    @Override
    public void encode(final CLValueEncoder clve, final boolean encodeType)
            throws IOException, CLValueEncodeException, DynamicInstanceException, NoSuchTypeException {
        clve.write(getOrder());
        clve.writeString(getName());
        clve.writeString(getEntryPoint());
        clve.writeInt(args.size());
        for (NamedArg<?> namedArg : args) {
            namedArg.encode(clve, true);
        }
    }
}