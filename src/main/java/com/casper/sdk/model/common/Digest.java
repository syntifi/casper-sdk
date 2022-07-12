package com.casper.sdk.model.common;

import com.casper.sdk.model.clvalue.encdec.CLValueEncoder;
import com.casper.sdk.model.clvalue.encdec.interfaces.EncodableValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.casper.sdk.exception.CLValueEncodeException;
import com.casper.sdk.exception.DynamicInstanceException;
import com.casper.sdk.exception.NoSuchTypeException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;

/**
 * Digest for Hex String
 *
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Digest implements EncodableValue {
    @JsonValue
    private String digest;

    public byte[] getDigest() {
        return Hex.decode(this.digest);
    }

    public void setDigest(byte[] hash) {
        this.digest = Hex.toHexString(hash);
    }

    public static Digest digestFromBytes(byte[] bytes) {
        Digest digest = new Digest();
        digest.setDigest(bytes);
        return digest;
    }

    /**
     * Implements Digest encoder
     */
    @Override
    public void encode(CLValueEncoder clve, boolean encodeType)
            throws IOException, CLValueEncodeException, DynamicInstanceException, NoSuchTypeException {
        clve.write(getDigest());
    }
}