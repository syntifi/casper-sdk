package com.casper.sdk.model.deploy;

import com.casper.sdk.model.clvalue.encdec.CLValueEncoder;
import com.casper.sdk.model.clvalue.encdec.interfaces.EncodableValue;
import com.casper.sdk.model.common.Digest;
import com.casper.sdk.exception.CLValueEncodeException;
import com.casper.sdk.exception.DynamicInstanceException;
import com.casper.sdk.exception.NoSuchTypeException;
import com.casper.sdk.model.deploy.executabledeploy.ExecutableDeployItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.util.List;

/**
 * Deploy an item containing a smart contract along with the requesters'
 * signature(s)
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
public class Deploy implements EncodableValue {

    /**
     * Hex-encoded deploy hash
     */
    private Digest hash;

    /**
     * @see DeployHeader
     */
    private DeployHeader header;

    /**
     * @see Approval
     */
    private List<Approval> approvals;

    /**
     * @see ExecutableDeployItem
     */
    private ExecutableDeployItem payment;

    /**
     * @see ExecutableDeployItem
     */
    private ExecutableDeployItem session;

    /**
     * Implements Deploy encoder
     */
    @Override
    public void encode(final CLValueEncoder clve, final boolean encodeType) throws IOException, CLValueEncodeException, DynamicInstanceException, NoSuchTypeException {
        header.encode(clve, true);
        hash.encode(clve, true);
        payment.encode(clve, true);
        session.encode(clve, true);
        clve.writeInt(approvals.size());
        for (Approval approval : approvals) {
            approval.encode(clve, true);
        }
    }
}

