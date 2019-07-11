package cy.agorise.graphenej.operations;

import com.google.common.primitives.UnsignedLong;

import org.junit.Assert;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;

import cy.agorise.graphenej.Asset;
import cy.agorise.graphenej.AssetAmount;
import cy.agorise.graphenej.HtlcHash;
import cy.agorise.graphenej.HtlcHashType;
import cy.agorise.graphenej.UserAccount;
import cy.agorise.graphenej.Util;

public class CreateHtlcOperationTest {
    private final String SERIALIZED_OP = "0000000000000000007b7c80241100000000000000a06e327ea7388c18e4740e350ed4e60f2e04fc41c8007800000000";
    private final String PREIMAGE_HEX = "666f6f626172";
    private final String HASH_RIPEMD160 = "a06e327ea7388c18e4740e350ed4e60f2e04fc41";
    private final String HASH_SHA1 = "8843d7f92416211de9ebb963ff4ce28125932878";
    private final String HASH_SHA256 = "c3ab8ff13720e8ad9047dd39466b3c8974e592c2fa383d4a3960714caef0c4f2";

    private final Asset CORE = new Asset("1.3.0");

    @Test
    public void testRipemd160(){
        try {
            byte[] hashRipemd160 = Util.htlcHash(Util.hexToBytes(PREIMAGE_HEX), HtlcHashType.RIPEMD160);
            String hexHash = Util.bytesToHex(hashRipemd160);
            Assert.assertEquals(HASH_RIPEMD160, hexHash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSha1(){
        try {
            byte[] hashSha1 = Util.htlcHash(Util.hexToBytes(PREIMAGE_HEX), HtlcHashType.SHA1);
            String hexHash = Util.bytesToHex(hashSha1);
            Assert.assertEquals(HASH_SHA1, hexHash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSha256(){
        try {
            byte[] hashSha256 = Util.htlcHash(Util.hexToBytes(PREIMAGE_HEX), HtlcHashType.SHA256);
            String hexHash = Util.bytesToHex(hashSha256);
            Assert.assertEquals(HASH_SHA256, hexHash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOperationSerialization() throws NoSuchAlgorithmException {
        UserAccount from = new UserAccount("1.2.123");
        UserAccount to = new UserAccount("1.2.124");
        AssetAmount fee = new AssetAmount(UnsignedLong.valueOf(0), CORE);
        AssetAmount amount = new AssetAmount(UnsignedLong.valueOf(1123456), CORE);
        byte[] hashBytes = Util.htlcHash("foobar".getBytes(), HtlcHashType.RIPEMD160);
        HtlcHash preimageHash = new HtlcHash(HtlcHashType.RIPEMD160, hashBytes);
        CreateHtlcOperation operation = new CreateHtlcOperation(fee, from, to, amount, preimageHash, (short) 200, 120);
        byte[] opBytes = operation.toBytes();
        Assert.assertArrayEquals(Util.hexToBytes(SERIALIZED_OP), opBytes);
    }
}
