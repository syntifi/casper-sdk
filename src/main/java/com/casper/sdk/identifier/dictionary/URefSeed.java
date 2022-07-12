package com.casper.sdk.identifier.dictionary;

import com.casper.sdk.model.uref.URef;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * Seed URef for dictionary item calls
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
public class URefSeed {

    /**
     * The dictionary item key formatted as a string
     */
    @JsonProperty("dictionary_item_key")
    private String dictionaryItemKey;

    /**
     * The dictionary's seed URef
     */
    @JsonProperty("seed_uref")
    private URef uref;

}
