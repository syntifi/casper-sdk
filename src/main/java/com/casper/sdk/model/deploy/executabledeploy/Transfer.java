package com.casper.sdk.model.deploy.executabledeploy;

import com.casper.sdk.model.clvalue.encdec.CLValueEncoder;
import com.casper.sdk.model.deploy.NamedArg;
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
 * An AbstractExecutableDeployItem of Type Transfer containing the runtime args
 * of the contract.
 *
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeName("Transfer")
public class Transfer implements ExecutableDeployItem {

    /**
     * List of {@link NamedArg}
     */
    private List<NamedArg<?>> args;

    /**
     * {@link ExecutableDeployItem} order 5
     */
    @Override
    public byte getOrder() {
        return 0x5;
    }

    /**
     * Implements the Transfer encoder
     */
    @Override
    public void encode(final CLValueEncoder clve, final boolean encodeType)
            throws IOException, CLValueEncodeException, DynamicInstanceException, NoSuchTypeException {
        clve.write(getOrder());
        clve.writeInt(args.size());
        for (NamedArg<?> namedArg : args) {
            namedArg.encode(clve, true);
        }
    }
}