package com.casper.sdk.model.clvalue.encdec;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;

import com.casper.sdk.exception.CLValueEncodeException;
import com.casper.sdk.exception.NoSuchTypeException;
import com.casper.sdk.model.clvalue.AbstractCLValue;
import com.casper.sdk.model.clvalue.CLValueAny;
import com.casper.sdk.model.clvalue.CLValueBool;
import com.casper.sdk.model.clvalue.CLValueByteArray;
import com.casper.sdk.model.clvalue.CLValueI32;
import com.casper.sdk.model.clvalue.CLValueI64;
import com.casper.sdk.model.clvalue.CLValueKey;
import com.casper.sdk.model.clvalue.CLValuePublicKey;
import com.casper.sdk.model.clvalue.CLValueString;
import com.casper.sdk.model.clvalue.CLValueU128;
import com.casper.sdk.model.clvalue.CLValueU256;
import com.casper.sdk.model.clvalue.CLValueU32;
import com.casper.sdk.model.clvalue.CLValueU512;
import com.casper.sdk.model.clvalue.CLValueU64;
import com.casper.sdk.model.clvalue.CLValueU8;
import com.casper.sdk.model.clvalue.cltype.CLTypeData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Casper CLValue Encoding methods
 * 
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @see AbstractCLValue
 * @since 0.0.1
 */

public class CLValueEncoder extends ByteArrayOutputStream {

    private static final Logger LOGGER = LoggerFactory.getLogger(CLValueEncoder.class);

    public static final BigInteger ZERO = new BigInteger("0", 10);
    public static final BigInteger ONE = new BigInteger("1", 10);
    public static final BigInteger TWO = new BigInteger("2", 10);
    public static final BigInteger MAX_U64 = TWO.pow(64).subtract(ONE);
    public static final BigInteger MAX_U128 = TWO.pow(128).subtract(ONE);
    public static final BigInteger MAX_U256 = TWO.pow(256).subtract(ONE);
    public static final BigInteger MAX_U512 = TWO.pow(512).subtract(ONE);

    private static final String LOG_BUFFER_WRITE_TYPE_VALUE_MESSAGE_STRING = "Writing CLType {} from Java Type {}: {}";
    private static final String ENCODE_EXCEPTION_OUT_OF_BOUNDS_MESSAGE_STRING = "Value %s out of bounds for expected type %s";

    /**
     * Initializes buffer as zero-length
     */
    public CLValueEncoder() {
        super(0);
    }

    /**
     * Writes a boolean value to the CLValue byte buffer
     * 
     * @param clValue {@link CLValueBool} value to encode
     * @throws IOException thrown when an error occurs while writing bytes
     */
    public void writeBool(CLValueBool clValue) throws IOException {
        LOGGER.debug(LOG_BUFFER_WRITE_TYPE_VALUE_MESSAGE_STRING, CLTypeData.BOOL, Boolean.class.getSimpleName(),
                clValue.getValue());

        ByteBuffer boolByteBuffer = ByteBuffer.allocate(1)
                .put(Boolean.TRUE.equals(clValue.getValue()) ? (byte) 0x01 : (byte) 0x00);

        byte[] boolBytes = boolByteBuffer.array();

        this.write(boolBytes);

        clValue.setBytes(StringByteHelper.convertBytesToHex(boolBytes));
    }

    /**
     * Writes a single byte value to the CLValue byte buffer
     * 
     * @param clValue {@link CLValueU8} value to encode
     */
    public void writeU8(CLValueU8 clValue) {
        LOGGER.debug(LOG_BUFFER_WRITE_TYPE_VALUE_MESSAGE_STRING, CLTypeData.U8, Byte.class.getSimpleName(),
                clValue.getValue());

        this.write(clValue.getValue());

        clValue.setBytes(StringByteHelper.convertBytesToHex(new byte[] {clValue.getValue()}));
    }

    /**
     * Writes a byte array value to the CLValue byte buffer
     * 
     * @param clValue {@link CLValueByteArray} value to encode
     * @throws IOException thrown when an error occurs while writing bytes
     */
    public void writeByteArray(CLValueByteArray clValue) throws IOException {
        LOGGER.debug(LOG_BUFFER_WRITE_TYPE_VALUE_MESSAGE_STRING, CLTypeData.BYTE_ARRAY, byte[].class.getSimpleName(),
                clValue.getValue());

        this.write(clValue.getValue());

        clValue.getClType().setLength(clValue.getValue().length);
        clValue.setBytes(StringByteHelper.convertBytesToHex(clValue.getValue()));
    }

    /**
     * Writes an Integer/I32 value to the CLValue byte buffer
     * 
     * @param clValue {@link CLValueI32} value to encode
     * @throws IOException thrown when an error occurs while writing bytes
     */
    public void writeI32(CLValueI32 clValue) throws IOException {
        LOGGER.debug(LOG_BUFFER_WRITE_TYPE_VALUE_MESSAGE_STRING, CLTypeData.I32, Integer.class.getSimpleName(),
                clValue.getValue());

        byte[] intByteArray = writeInt(clValue.getValue());

        clValue.setBytes(StringByteHelper.convertBytesToHex(intByteArray));
    }

    /**
     * Writes an Unsigned Integer (Long)/U32 value to the CLValue byte buffer
     * 
     * @param clValue {@link CLValueU32} value to encode
     * @throws IOException thrown when an error occurs while writing bytes
     */
    public void writeU32(CLValueU32 clValue) throws IOException {
        LOGGER.debug(LOG_BUFFER_WRITE_TYPE_VALUE_MESSAGE_STRING, CLTypeData.U32, Long.class.getSimpleName(),
                clValue.getValue());

        byte[] uIntByteArray = writeInt(clValue.getValue().intValue());

        clValue.setBytes(StringByteHelper.convertBytesToHex(uIntByteArray));
    }

    /**
     * Writes an Integer value to byte buffer
     * 
     * @param value the value to write
     * @return byte[]
     * @throws IOException thrown when an error occurs while writing bytes
     */
    public byte[] writeInt(Integer value) throws IOException {
        LOGGER.debug("Writing Java Type {}: {}", Integer.class.getSimpleName(), value);

        ByteBuffer intByteBuffer = ByteBuffer.allocate(4).putInt(value);

        byte[] intByteArray = intByteBuffer.array();

        StringByteHelper.reverse(intByteArray);

        this.write(intByteArray);
        return intByteArray;
    }

    /**
     * Writes a Long/I64 value to the CLValue byte buffer
     * 
     * @param clValue {@link CLValueI64} value to encode
     * @throws IOException thrown when an error occurs while writing bytes
     */
    public void writeI64(CLValueI64 clValue) throws IOException {
        LOGGER.debug(LOG_BUFFER_WRITE_TYPE_VALUE_MESSAGE_STRING, CLTypeData.I64, Long.class.getSimpleName(),
                clValue.getValue());

        byte[] longByteArray = writeLong(clValue.getValue());

        clValue.setBytes(StringByteHelper.convertBytesToHex(longByteArray));
    }

    /**
     * Writes a Long value to byte buffer
     *
     * @param value the value to write
     * @return byte[]
     * @throws IOException thrown when an error occurs while writing bytes
     */
    public byte[] writeLong(Long value) throws IOException {
        LOGGER.debug("Writing Java Type {}: {}", Long.class.getSimpleName(), value);

        ByteBuffer longByteBuffer = ByteBuffer.allocate(8).putLong(value);

        byte[] longByteArray = longByteBuffer.array();

        StringByteHelper.reverse(longByteArray);

        this.write(longByteArray);
        return longByteArray;
    }

    /**
     * Writes an Unsigned Long (BigInteger)/U64 to the CLValue byte buffer
     * 
     * @param clValue {@link CLValueU64} value to encode
     * @throws IOException thrown when an error occurs while writing bytes
     * @throws CLValueEncodeException thrown when a clvalue encoding throws an error
     */
    public void writeU64(CLValueU64 clValue) throws IOException, CLValueEncodeException {
        checkBoundsFor(clValue.getValue(), CLTypeData.U64);

        LOGGER.debug(LOG_BUFFER_WRITE_TYPE_VALUE_MESSAGE_STRING, CLTypeData.U64, BigInteger.class.getSimpleName(),
                clValue.getValue());

        ByteBuffer unsignedLongByteBuffer = ByteBuffer.allocate(8).putLong(clValue.getValue().longValue());

        byte[] unsignedLongByteArray = unsignedLongByteBuffer.array();

        StringByteHelper.reverse(unsignedLongByteArray);

        this.write(unsignedLongByteArray);

        clValue.setBytes(StringByteHelper.convertBytesToHex(unsignedLongByteArray));
    }

    /**
     * Writes a BigInteger/U128 to the CLValue byte buffer
     * 
     * @param clValue {@link CLValueU128} value to encode
     * @throws IOException thrown when an error occurs while writing bytes
     * @throws CLValueEncodeException thrown when a clvalue encoding throws an error
     */
    public void writeU128(CLValueU128 clValue) throws IOException, CLValueEncodeException {
        writeBigInteger(clValue, CLTypeData.U128);
    }

    /**
     * Writes a BigInteger/U256 to the CLValue byte buffer
     * 
     * @param clValue {@link CLValueU256} value to encode
     * @throws IOException thrown when an error occurs while writing bytes
     * @throws CLValueEncodeException thrown when a clvalue encoding throws an error
     */
    public void writeU256(CLValueU256 clValue) throws IOException, CLValueEncodeException {
        writeBigInteger(clValue, CLTypeData.U256);
    }

    /**
     * Writes a BigInteger/U512 to the CLValue byte buffer
     * 
     * @param clValue {@link CLValueU512} value to encode
     * @throws IOException thrown when an error occurs while writing bytes
     * @throws CLValueEncodeException thrown when a clvalue encoding throws an error
     */
    public void writeU512(CLValueU512 clValue) throws IOException, CLValueEncodeException {
        writeBigInteger(clValue, CLTypeData.U512);
    }

    /**
     * Writes a BigInteger/U128-U256-U512 to the CLValue byte buffer
     * 
     * @param clValue {@link AbstractCLValue} value to encode
     * @param type {@link CLTypeData} CLTypeData of BigInteger
     * @throws IOException thrown when an error occurs while writing bytes
     * @throws CLValueEncodeException thrown when a clvalue encoding throws an error
     */
    protected void writeBigInteger(AbstractCLValue<BigInteger, ?> clValue, CLTypeData type)
            throws IOException, CLValueEncodeException {
        checkBoundsFor(clValue.getValue(), type);

        LOGGER.debug(LOG_BUFFER_WRITE_TYPE_VALUE_MESSAGE_STRING, type.getClTypeName(), BigInteger.class.getSimpleName(),
                clValue.getValue());

        byte bigIntegerLength = (byte) (Math.ceil(clValue.getValue().bitLength() / 8.0));
        byte[] bigIntegerBytes = writeBigInteger(clValue.getValue());

        clValue.setBytes(StringByteHelper.convertBytesToHex(new byte[] { bigIntegerLength })
                + StringByteHelper.convertBytesToHex(bigIntegerBytes));
    }

    /**
     * Writes a BigInteger to the CLValue byte buffer
     * 
     * @param bigInteger the biginteger to write
     * @return a byte array
     * @throws IOException thrown when an error occurs while writing bytes
     */
    public byte[] writeBigInteger(BigInteger bigInteger) throws IOException {
        LOGGER.debug("Writing Java Type {}: {}", BigInteger.class.getSimpleName(), bigInteger);

        byte bigIntegerLength = (byte) (Math.ceil(bigInteger.bitLength() / 8.0));

        this.write(bigIntegerLength);

        byte[] byteArray = bigInteger.toByteArray();

        // Removing leading zeroes
        int i = 0;
        boolean both = false;
        while (byteArray[i] == 0) {
            if (both) {
                i++;
            }
            both = !both;
        }

        byte[] valueByteArray = Arrays.copyOfRange(byteArray, i, bigIntegerLength + i);

        StringByteHelper.reverse(valueByteArray);

        ByteBuffer valueByteBuffer = ByteBuffer.allocate(bigIntegerLength).put(valueByteArray);

        byte[] bigIntegerBytes = valueByteBuffer.array();

        this.write(bigIntegerBytes);

        return bigIntegerBytes;
    }

    /**
     * Writes a String/String to the CLValue byte buffer
     * 
     * @param clValue {@link CLValueString} value to encode
     * @throws IOException thrown when an error occurs while writing bytes
     */
    public void writeString(CLValueString clValue) throws IOException {
        LOGGER.debug(LOG_BUFFER_WRITE_TYPE_VALUE_MESSAGE_STRING, CLTypeData.STRING, String.class.getSimpleName(),
                clValue.getValue());

        byte[] stringBytes = writeString(clValue.getValue());

        clValue.setBytes(StringByteHelper.convertBytesToHex(stringBytes));
    }

    /**
     * Writes a String to the byte buffer
     * 
     * @param string to encode
     * @return byte[]
     * @throws IOException thrown when an error occurs while writing bytes
     */
    public byte[] writeString(String string) throws IOException {
        LOGGER.debug("Writing Java Type {}: {}", String.class.getSimpleName(), string);

        ByteBuffer intByteBuffer = ByteBuffer.allocate(4).putInt(string.length());

        byte[] intByteArray = intByteBuffer.array();

        StringByteHelper.reverse(intByteArray);

        ByteBuffer stringBuffer = ByteBuffer.allocate(4 + string.length());
        stringBuffer.put(intByteArray);
        stringBuffer.put(string.getBytes());

        byte[] stringBytes = stringBuffer.array();

        this.write(stringBytes);

        return stringBytes;
    }

    /**
     * Writes a AlgorithmTag/Key Hex string to CLValue byte buffer
     * 
     * @param clValue {@link CLValuePublicKey} value to encode
     */
    public void writePublicKey(CLValuePublicKey clValue) throws IOException {
        byte[] tag = new byte[] { clValue.getValue().getTag().getByteTag() };
        clValue.setBytes(StringByteHelper.convertBytesToHex(tag)
                + StringByteHelper.convertBytesToHex(clValue.getValue().getKey()));
        this.write(tag);
        this.write(clValue.getValue().getKey());
    }

    /**
     * Writes a Tag/Key Hex string to CLValue byte buffer
     * 
     * @param clValue {@link CLValueKey} value to encode
     */
    public void writeKey(CLValueKey clValue) {
        clValue.setBytes(StringByteHelper.convertBytesToHex(new byte[] { clValue.getValue().getTag().getByteTag() })
                + StringByteHelper.convertBytesToHex(clValue.getValue().getKey()));
    }

    public void writeAny(CLValueAny clValue) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(this)) {
            oos.writeObject(clValue.getValue());
        }

        clValue.setBytes(StringByteHelper.convertBytesToHex(this.toByteArray()));
    }

    /**
     * Checks if the value is within valid bounds for given CLType
     * 
     * @param value the value to check
     * @param type  the cltype to check against
     * @throws CLValueEncodeException thrown when a clvalue encoding throws an error
     */
    private void checkBoundsFor(BigInteger value, CLTypeData type) throws CLValueEncodeException {
        BigInteger max;
        if (type.equals(CLTypeData.U64)) {
            max = MAX_U64;
        } else if (type.equals(CLTypeData.U128)) {
            max = MAX_U128;
        } else if (type.equals(CLTypeData.U256)) {
            max = MAX_U256;
        } else if (type.equals(CLTypeData.U512)) {
            max = MAX_U512;
        } else {
            throw new CLValueEncodeException("Error checking numeric bounds", new NoSuchTypeException(
                    String.format("%s is not a numeric type with check bounds for encoding", type.getClTypeName())));
        }

        if (value.compareTo(max) > 0 || value.compareTo(ZERO) < 0) {
            throw new CLValueEncodeException(String.format(ENCODE_EXCEPTION_OUT_OF_BOUNDS_MESSAGE_STRING,
                    value, type.getClTypeName()));
        }
    }
}
