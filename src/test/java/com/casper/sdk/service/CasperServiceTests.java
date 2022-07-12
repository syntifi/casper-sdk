package com.casper.sdk.service;

import com.casper.sdk.identifier.block.HashBlockIdentifier;
import com.casper.sdk.identifier.block.HeightBlockIdentifier;
import com.casper.sdk.model.account.AccountData;
import com.casper.sdk.model.auction.AuctionData;
import com.casper.sdk.model.block.JsonBlock;
import com.casper.sdk.model.block.JsonBlockData;
import com.casper.sdk.model.clvalue.CLValueString;
import com.casper.sdk.model.clvalue.encdec.StringByteHelper;
import com.casper.sdk.model.deploy.DeployData;
import com.casper.sdk.model.deploy.executabledeploy.ModuleBytes;
import com.casper.sdk.model.deploy.executabledeploy.StoredContractByHash;
import com.casper.sdk.model.deploy.executionresult.Success;
import com.casper.sdk.model.deploy.transform.WriteCLValue;
import com.casper.sdk.model.era.EraInfoData;
import com.casper.sdk.model.key.AlgorithmTag;
import com.casper.sdk.model.key.PublicKey;
import com.casper.sdk.model.peer.PeerData;
import com.casper.sdk.model.stateroothash.StateRootHashData;
import com.casper.sdk.model.status.StatusData;
import com.casper.sdk.model.storedvalue.StoredValueAccount;
import com.casper.sdk.model.storedvalue.StoredValueContract;
import com.casper.sdk.model.storedvalue.StoredValueData;
import com.casper.sdk.model.transfer.Transfer;
import com.casper.sdk.model.transfer.TransferData;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link CasperService}
 *
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
public class CasperServiceTests extends AbstractJsonRpcTests {
    private static final Logger LOGGER = LoggerFactory.getLogger(CasperServiceTests.class);

    /**
     * Test if the get block matches requested by height
     */
    @Test
    void testIfBlockReturnedMatchesRequestedByHeight() {
        final int blocks_to_check = 3;
        for (int i = 0; i < blocks_to_check; i++) {
            final JsonBlockData result = casperServiceMainnet.getBlock(new HeightBlockIdentifier(i));
            assertEquals(result.getBlock().getHeader().getHeight(), i);
        }
    }

    /**
     * Test if the get block matches requested by hash
     */
    @Test
    void testIfBlockReturnedMatchesRequestedByHash() {
        final int blocks_to_check = 3;
        for (int i = 0; i < blocks_to_check; i++) {
            LOGGER.debug(String.format("Testing with block height %d", i));
            final JsonBlockData resultByHeight = casperServiceMainnet.getBlock(new HeightBlockIdentifier(i));
            final String hash = resultByHeight.getBlock().getHash();
            final JsonBlockData resultByHash = casperServiceMainnet.getBlock(new HashBlockIdentifier(hash));

            assertEquals(resultByHash.getBlock().getHash(), hash);
        }
    }

    /**
     * Test if the get public key serialization is correct
     */
    @Test
    void testFirstBlocksPublicKeySerialization() {
        final JsonBlockData result = casperServiceMainnet.getBlock(new HeightBlockIdentifier(0));
        final PublicKey key = result.getBlock().getBody().getProposer();

        assertEquals(AlgorithmTag.ED25519, key.getTag());
        assertEquals("2bac1d0ff9240ff0b7b06d555815640497861619ca12583ddef434885416e69b",
                StringByteHelper.convertBytesToHex(key.getKey()));
    }

    /**
     * Retrieve peers list and assert it has elements
     */
    @Test
    void retrieveNonEmptyListOfPeers() {
        final PeerData peerData = casperServiceMainnet.getPeerData();

        assertNotNull(peerData);
        assertFalse(peerData.getPeers().isEmpty());
    }

    @Test
    void retrieveLastBlock() {
        final JsonBlockData blockData = casperServiceMainnet.getBlock();

        assertNotNull(blockData);
    }

    @Test
    void getBlockByHash() {
        final JsonBlockData blockData = casperServiceMainnet
                .getBlock(new HashBlockIdentifier("2fe9630b7790852e4409d815b04ca98f37effcdf9097d317b9b9b8ad658f47c8"));

        assertNotNull(blockData);
        final JsonBlock block = blockData.getBlock();
        assertEquals("0000000000000000000000000000000000000000000000000000000000000000",
                block.getHeader().getParentHash());
        assertEquals(0, block.getHeader().getHeight());
    }

    @Test
    void getBlockByHeight() {
        final JsonBlockData blockData = casperServiceMainnet.getBlock(new HeightBlockIdentifier(0));
        final JsonBlock block = blockData.getBlock();
        assertEquals("0000000000000000000000000000000000000000000000000000000000000000",
                block.getHeader().getParentHash());
        assertEquals("2fe9630b7790852e4409d815b04ca98f37effcdf9097d317b9b9b8ad658f47c8", block.getHash());
        assertNotNull(blockData);
    }

    @Test
    void retrieveLastBlockTransfers() {
        final TransferData transferData = casperServiceMainnet.getBlockTransfers();

        assertNotNull(transferData);
    }

    @Test
    void getTransferByHeight() {
        final TransferData transferData = casperServiceMainnet.getBlockTransfers(new HeightBlockIdentifier(198148));

        assertNotNull(transferData);
        assertEquals(1, transferData.getTransfers().size());
        final Transfer transaction = transferData.getTransfers().get(0);
        assertEquals("c22fab5364c793bb859fd259b808ea4c101be8421b7d638dc8f52490ab3c3539",
                transaction.getDeployHash());
        assertEquals("account-hash-2363d9065b1ecc26f50f108c22c8f3bbe6a891c81e37e0e454c68370708a6937",
                transaction.getFrom());
        assertEquals("account-hash-288797af5b4eeb5d4f36bd228b2e6479a77a27e808597ced1a7d6afe4c29febc",
                transaction.getTo());
        assertEquals(BigInteger.valueOf(597335999990000L), transaction.getAmount());
    }

    @Test
    void getTransferByHash() {
        final TransferData transferData = casperServiceMainnet.getBlockTransfers(
                new HashBlockIdentifier("db9820938ee64c7037f13ea05ab8fe7576215c3a62b02ed35c2564c2138eeb57"));

        assertNotNull(transferData);
        assertEquals(1, transferData.getTransfers().size());
        final Transfer transaction = transferData.getTransfers().get(0);
        assertEquals("c22fab5364c793bb859fd259b808ea4c101be8421b7d638dc8f52490ab3c3539",
                transaction.getDeployHash());
        assertEquals("account-hash-2363d9065b1ecc26f50f108c22c8f3bbe6a891c81e37e0e454c68370708a6937",
                transaction.getFrom());
        assertEquals("account-hash-288797af5b4eeb5d4f36bd228b2e6479a77a27e808597ced1a7d6afe4c29febc",
                transaction.getTo());
        assertEquals(BigInteger.valueOf(597335999990000L), transaction.getAmount());
    }

    @Test
    void retrieveLastBlockStateRootHash() {
        final StateRootHashData stateRootData = casperServiceMainnet.getStateRootHash();

        assertNotNull(stateRootData);
    }

    @Test
    void getStateRootHashByHeight() {
        final StateRootHashData stateRootHashData = casperServiceMainnet.getStateRootHash(new HeightBlockIdentifier(0));
        assertNotNull(stateRootHashData);
        assertEquals("8e22e3983d5ca9bcf9804bd3a6724b8c24effdf317a1d9c05175125a1bf8b679",
                stateRootHashData.getStateRootHash());
    }

    @Test
    void getStateRootHashByHash() {
        final StateRootHashData stateRootHashData = casperServiceMainnet.getStateRootHash(
                new HashBlockIdentifier("2fe9630b7790852e4409d815b04ca98f37effcdf9097d317b9b9b8ad658f47c8"));

        assertNotNull(stateRootHashData);
        assertEquals("8e22e3983d5ca9bcf9804bd3a6724b8c24effdf317a1d9c05175125a1bf8b679",
                stateRootHashData.getStateRootHash());
    }

    @Test
    void getBlockState() {
        // FIXME: This test fails on mainnet, no root hash.
        final String stateRootHash = "c0eb76e0c3c7a928a0cb43e82eb4fad683d9ad626bcd3b7835a466c0587b0fff";
        final String key = "account-hash-a9efd010c7cee2245b5bad77e70d9beb73c8776cbe4698b2d8fdf6c8433d5ba0";
        final List<String> path = Collections.singletonList("special_value");
        final StoredValueData result = casperServiceTestnet.getStateItem(stateRootHash, key, path);

        assertTrue(result.getStoredValue().getValue() instanceof CLValueString);
        // Should be equal incoming parsed
        assertEquals(((CLValueString) result.getStoredValue().getValue()).getValue(),
                ((CLValueString) result.getStoredValue().getValue()).getParsed());
    }

    @Test
    void getDeploy() {
        final DeployData deployData = casperServiceMainnet
                .getDeploy("614030ac705ed2067fed57d30545b3a4974ffc40a1c32f72e3b7b7442d6c83a3");

        assertNotNull(deployData);
        assertNotNull(deployData.getDeploy());
        assertTrue(deployData.getDeploy().getSession() instanceof StoredContractByHash);
        assertTrue(deployData.getExecutionResults().get(0).getResult() instanceof Success);
        assertTrue(((Success) deployData.getExecutionResults().get(0).getResult()).getEffect().getTransforms().get(0)
                .getTransform() instanceof WriteCLValue);
        assertTrue(deployData.getDeploy().getPayment() instanceof ModuleBytes);
        assertTrue(deployData.getDeploy().getSession() instanceof StoredContractByHash);
        final String tmp = ((StoredContractByHash) deployData.getDeploy().getSession()).getHash();
        assertEquals("ccb576d6ce6dec84a551e48f0d0b7af89ddba44c7390b690036257a04a3ae9ea", tmp);
    }

    @Test
    void getStatus() {
        final StatusData status = casperServiceMainnet.getStatus();
        assertNotNull(status);
        assertNotNull(status.getLastAddedBlockInfo());
        assertNotNull(status.getStartStateRootHash());
    }

    @Test
    void getStateItem_account() {
        final StoredValueData storedValueData = casperServiceMainnet.getStateItem(
                "09ac52260e370ed56bba5283a79b03d524b4f420bf964d7e629b0819dd1be09d",
                "account-hash-e1431ecb9f20f2a6e6571886b1e2f9dec49ebc6b2d3d640a53530abafba9bfa1", new ArrayList<>());

        assertTrue(storedValueData.getStoredValue() instanceof StoredValueAccount);
    }

    @Test
    void getStateItem_contract() {
        StoredValueData storedValueData = casperServiceMainnet.getStateItem(
                "09ac52260e370ed56bba5283a79b03d524b4f420bf964d7e629b0819dd1be09d",
                "hash-d2469afeb99130f0be7c9ce230a84149e6d756e306ef8cf5b8a49d5182e41676", new ArrayList<>());

        assertTrue(storedValueData.getStoredValue() instanceof StoredValueContract);
    }

    @Test
    void getAccountStateInfoByBlockHash() {
        AccountData account = casperServiceMainnet.getStateAccountInfo(
                "012dbde8cac6493c07c5548edc89ab7803c376278ec91757475867324d99f5f4dd",
                new HashBlockIdentifier("721767b0bcf867ccab81b3a47b1443bbef38b2ee9e2b791288f6e2a427181931"));

        assertNotNull(account);
        assertNotNull(account.getAccount());
        assertEquals("account-hash-f1075fce3b8cd4eab748b8705ca02444a5e35c0248662649013d8a5cb2b1a87c",
                account.getAccount().getHash());
    }

    @Test
    void getAccountStateInfoByBlockHeight() {
        AccountData account = casperServiceMainnet.getStateAccountInfo(
                "012dbde8cac6493c07c5548edc89ab7803c376278ec91757475867324d99f5f4dd",
                new HeightBlockIdentifier(236509));

        assertNotNull(account);
        assertNotNull(account.getAccount());
        assertEquals("account-hash-f1075fce3b8cd4eab748b8705ca02444a5e35c0248662649013d8a5cb2b1a87c",
                account.getAccount().getHash());
    }

    @Test
    void getAuctionInfoByBlockHash() {
        AuctionData auction = casperServiceMainnet.getStateAuctionInfo(
                new HashBlockIdentifier("58e0BBfB1FFf590965b6B12898CBE8b2C12FFe73fA5360E1dA42a66c3B9416BC"));

        assertNotNull(auction);
        assertNotNull(auction.getAuctionState());
        assertEquals(423571, auction.getAuctionState().getHeight());
    }

    @Test
    void getAuctionInfoByBlockHeight() {
        AuctionData auction = casperServiceMainnet.getStateAuctionInfo(new HeightBlockIdentifier(423571));

        assertNotNull(auction);
        assertNotNull(auction.getAuctionState());
        assertEquals(423571, auction.getAuctionState().getHeight());
    }

    @Test
    void getEraInfoBySwitchBlockByHeight() {
        EraInfoData eraInfoData = casperServiceMainnet.getEraInfoBySwitchBlock(new HeightBlockIdentifier(423571));

        assertNotNull(eraInfoData);
        assertNotNull(eraInfoData.getEraSummary());
        assertEquals("57a4257ccae4dc00fc1b2a0bde545b07937b8cdd18ea5544a70496cd67872d71",
                eraInfoData.getEraSummary().getStateRootHash());
    }

    @Test
    void getEraInfoBySwitchBlockByHash() {
        EraInfoData eraInfoData = casperServiceMainnet.getEraInfoBySwitchBlock(
                new HashBlockIdentifier("6eee8974bd9df0c2ae5469a239c23ff901c4ca884a1fe8b7b5319b04fac3b484"));

        assertNotNull(eraInfoData);
        assertNotNull(eraInfoData.getEraSummary());
        assertEquals("485a33d5c737030432fba0c3b15c1cc6b372fd286677bf9f29d4ab8b1f0c9223",
                eraInfoData.getEraSummary().getStateRootHash());

    }
}
