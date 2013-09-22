package blir.crypto;

import java.io.*;
import java.security.*;
import java.security.spec.*;
import javax.crypto.*;
import javax.crypto.spec.*;

/**
 * A tool for encrypting and decrypting files in a few lines of code. <p>
 * Currently only supports the DES and DESede algorithms for decryption. Use the
 * .dat file extension for binary data files (e.g. the key file and data output
 * file).
 *
 * @author Blir
 */
public class QuickCipher {

    /**
     * Controls whether debug data is printed to the standard PrintStreams
     * during execution.
     */
    private static boolean debug;
    /**
     * Performs all encryption and decryption facilitated by this QuickCipher.
     */
    private Cipher cipher;
    /**
     * The reference to the key for encryption and decryption performed by this
     * QuickCipher.
     */
    private SecretKey key;
    /**
     * Used to store any data being encrypted or decrypted by this QuickCipher.
     */
    private byte[] data;
    /**
     * The algorithm used for encryption and decryption for this QuickCipher.
     */
    private String algorithm;
    
    /**
     * The supported decryption algorithms.
     */
    public static enum Algorithm {
        DES, DESede
    }

    /**
     * Constructs a new QuickChipher with the given transformation and
     * algorithm.
     *
     * @param transformation the transformation to be used to create the Cipher
     * for this QuickCipher.
     * @param algorithm the algorithm to be used to create the SecretKey for
     * this QuickCipher.
     * @throws NoSuchAlgorithmException if transformation is null, empty, in an
     * invalid format, or if no Provider supports a CipherSpi implementation for
     * the specified algorithm or if no Provider supports a KeyGeneratorSpi
     * implementation for the specified algorithm.
     * @throws NoSuchPaddingException if transformation contains a padding
     * scheme that is not available.
     */
    public QuickCipher(String transformation, String algorithm) throws NoSuchAlgorithmException, NoSuchPaddingException {
        this.algorithm = algorithm;
        cipher = Cipher.getInstance(transformation);
        key = KeyGenerator.getInstance(this.algorithm).generateKey();
    }

    /**
     * Reads the raw data from the input File, encrypts and saves its contents
     * to the output File, and then save the raw data of the SecretKey used to
     * the SecretKey File.
     *
     * @param input the File to be read and encrypted
     * @param output the File the encrypted data will be saved to
     * @param keyFile the File the SecretKey data will be saved to
     * @throws InvalidKeyException if the given key is inappropriate for
     * initializing this cipher, or if this cipher is being initialized for
     * decryption and requires algorithm parameters that cannot be determined
     * from the given key, or if the given key has a keysize that exceeds the
     * maximum allowable keysize (as determined from the configured jurisdiction
     * policy files).
     * @throws IOException if any of the files do not exist, are a directory
     * rather than a regular file, or for some other reason cannot be opened for
     * reading or if an I/O error occurs.
     * @throws IllegalBlockSizeException if the cipher is a block cipher, no
     * padding has been requested (only in encryption mode), and the total input
     * length of the data processed by this cipher is not a multiple of block
     * size; or if this encryption algorithm is unable to process the input data
     * provided.
     * @throws BadPaddingException if the cipher is in decryption mode, and
     * (un)padding has been requested, but the decrypted data is not bounded by
     * the appropriate padding bytes
     */
    public void encrypt(File input, File output, File keyFile) throws InvalidKeyException,
            IOException, IllegalBlockSizeException, BadPaddingException {
        if (debug) {
            System.out.println("Initializing encryption...");
        }
        cipher.init(Cipher.ENCRYPT_MODE, key);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(input);
            data = new byte[(int) input.length()];
            if (debug) {
                System.out.println("Reading data...");
            }
            fis.read(data);
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
        if (debug) {
            System.out.println("Encrypting data...");
        }
        data = cipher.doFinal(data);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(output);
            if (debug) {
                System.out.println("Saving data...");
            }
            fos.write(data);
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
        if (debug) {
            System.out.println("Saving key...");
        }
        data = key.getEncoded();
        fos = null;
        try {
            fos = new FileOutputStream(keyFile);
            fos.write(data);
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
        if (debug) {
            System.out.println("Encryption complete!");
        }
        data = null;
    }

    /**
     * Reads the raw data from the SecretKey File, creates a SecretKey from that
     * raw data, reads the raw data from the input File, and then decrypts and
     * saves its contents to the output File.
     *
     * @param input the File to be read and decrypted
     * @param output the File the decrypted data will be saved to
     * @param keyFile the File the SecretKey data will be loaded from
     * @throws InvalidKeyException if the given key material is shorter than 8
     * bytes or if the given key is inappropriate for initializing this cipher,
     * or if this cipher is being initialized for decryption and requires
     * algorithm parameters that cannot be determined from the given key, or if
     * the given key has a keysize that exceeds the maximum allowable keysize
     * (as determined from the configured jurisdiction policy files).
     * @throws IOException if any of the files do not exist, are a directory
     * rather than a regular file, or for some other reason cannot be opened for
     * reading or if an I/O error occurs.
     * @throws IllegalBlockSizeException if the cipher is a block cipher, no
     * padding has been requested (only in encryption mode), and the total input
     * length of the data processed by this cipher is not a multiple of block
     * size; or if this encryption algorithm is unable to process the input data
     * provided.
     * @throws BadPaddingException if the cipher is in decryption mode, and
     * (un)padding has been requested, but the decrypted data is not bounded by
     * the appropriate padding bytes.
     * @throws NoSuchAlgorithmException if no Provider supports a
     * SecretKeyFactorySpi implementation for the specified algorithm.
     * @throws InvalidKeySpecException if the given key specification is
     * inappropriate for this secret-key factory to produce a secret key.
     * @throws UnsupportedOperationException if algorithm is not DES or DESede
     */
    public void decrypt(File input, File output, File keyFile) throws InvalidKeyException,
            IOException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeySpecException {
        if (debug) {
            System.out.println("Loading key...");
        }
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(keyFile);
            data = new byte[fis.available()];
            fis.read(data);
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
        switch (Algorithm.valueOf(algorithm)) {
            case DES:
                key = SecretKeyFactory.getInstance(algorithm).generateSecret(new DESKeySpec(data));
                break;
            case DESede:
                key = SecretKeyFactory.getInstance(algorithm).generateSecret(new DESedeKeySpec(data));
                break;
            default:
                throw new UnsupportedOperationException("Unsupported decryption algorithm");
        }

        if (debug) {
            System.out.println("Initializing decryption...");
        }
        cipher.init(Cipher.DECRYPT_MODE, key);
        if (debug) {
            System.out.println("Reading data...");
        }
        fis = null;
        try {
            fis = new FileInputStream(input);
            data = new byte[(int) input.length()];
            fis.read(data);
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
        if (debug) {
            System.out.println("Decrypting data...");
        }
        data = cipher.doFinal(data);
        if (debug) {
            System.out.println("Saving data...");
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(output);
            fos.write(data);
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
        if (debug) {
            System.out.println("Decryption complete!");
        }
        data = null;
    }

    /**
     * Sets the debug mode for this class.
     *
     * @param debug the value to set debug mode to
     */
    public static void setDebug(boolean debug) {
        QuickCipher.debug = debug;
    }
}
