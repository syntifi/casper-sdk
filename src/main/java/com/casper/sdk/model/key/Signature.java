package com.casper.sdk.model.key;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.casper.sdk.jackson.deserializer.SignatureDeserializer;
import com.syntifi.crypto.key.AbstractPrivateKey;
import com.syntifi.crypto.key.Secp256k1PrivateKey;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.security.GeneralSecurityException;

/**
 * Hex-encoded cryptographic public key, including the algorithm tag prefix.
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@JsonDeserialize(using = SignatureDeserializer.class)
@NoArgsConstructor
@Builder
public class Signature extends AbstractSerializedKeyTaggedHex<AlgorithmTag> {

    public static Signature sign(final AbstractPrivateKey key, final byte[] msg) throws GeneralSecurityException {
        final byte[] signatureBytes = key.sign(msg);
        final Signature signature = new Signature();
        signature.setKey(signatureBytes);
        signature.setTag((key instanceof Secp256k1PrivateKey)
                ? AlgorithmTag.SECP256K1
                : AlgorithmTag.ED25519);
        return signature;
    }
}
