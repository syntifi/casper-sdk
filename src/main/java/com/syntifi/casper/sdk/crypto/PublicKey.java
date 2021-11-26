package com.syntifi.casper.sdk.crypto;

import java.io.IOException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class PublicKey {
    protected byte[] key;

    public abstract void readPublicKey(String filename) throws IOException;

    public abstract String sign(String msg);
}