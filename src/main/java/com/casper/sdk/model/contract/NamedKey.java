package com.casper.sdk.model.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A named key.
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
public class NamedKey {

    /**
     * key(String) The value of the entry: a casper `Key` type.
     */
    @JsonProperty("key")
    private String key;

    /**
     * name(String) The name of the entry.
     */
    @JsonProperty("name")
    private String name;
}
