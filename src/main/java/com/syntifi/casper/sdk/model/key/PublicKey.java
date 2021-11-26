package com.syntifi.casper.sdk.model.key;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.syntifi.casper.sdk.crypto.Ed25519PublicKey;
import com.syntifi.casper.sdk.crypto.Secp256k1PublicKey;
import com.syntifi.casper.sdk.exception.InvalidByteStringException;
import com.syntifi.casper.sdk.jackson.deserializer.PublicKeyDeserializer;
import com.syntifi.casper.sdk.model.clvalue.encdec.StringByteHelper;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Hex-encoded cryptographic public key, including the algorithm tag prefix.
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@JsonDeserialize(using = PublicKeyDeserializer.class)
@NoArgsConstructor
public class PublicKey extends AbstractSerializedKeyTaggedHex<AlgorithmTag> {

    public static PublicKey fromTaggedHexString(String hex)
            throws NoSuchAlgorithmException, InvalidByteStringException {
        PublicKey object = new PublicKey();
        byte[] bytes = StringByteHelper.hexStringToByteArray(hex);
        object.setTag(AlgorithmTag.getByTag(bytes[0]));
        object.setKey(Arrays.copyOfRange(bytes, 1, bytes.length));

        return object;
    }

    @JsonCreator
    public void createPublicKey(String key) throws NoSuchAlgorithmException, InvalidByteStringException {
        PublicKey obj = PublicKey.fromTaggedHexString(key);
        this.setTag(obj.getTag());
        this.setKey(obj.getKey());
    }

    public com.syntifi.casper.sdk.crypto.PublicKey getPubKey() throws NoSuchAlgorithmException {
        if (getTag().equals(AlgorithmTag.ED25519)) {
            return new Ed25519PublicKey(getKey());
        } else if (getTag().equals(AlgorithmTag.SECP256K1)) {
            return new Secp256k1PublicKey(getKey());
        } else {
            throw new NoSuchAlgorithmException();
        }
    }
}