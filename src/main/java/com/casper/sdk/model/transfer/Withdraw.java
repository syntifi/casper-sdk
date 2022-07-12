package com.casper.sdk.model.transfer;

import com.casper.sdk.model.uref.URef;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A withdraw.
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
public class Withdraw {

    /**
     * amount(String) - Decimal representation of a 512-bit integer.
     */
    private String amount;

    /**
     * bonding_purse(String) - Hex-encoded, formatted URef.
     */
    private URef bondingPurse;

    /**
     * amount(Integer) - Era ID newtype.
     */
    private int eraOfCreation;

    /**
     * unbonder_public_key(String) - Hex-encoded cryptographic public key, including
     * the algorithm tag prefix.
     */
    private String unbonderPublicKey;

    /**
     * validator_public_key(String) - Hex-encoded cryptographic public key,
     * including the algorithm tag prefix.
     */
    private String validatorPublicKey;
}
