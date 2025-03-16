package org.rajeshkurup.common.hash;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HashOperatorTest {

    private HashOperator hashOperator;

    @BeforeEach
    public void init() {
        this.hashOperator = new HashOperator();
    }

    @AfterEach
    public void cleanUp() {
        this.hashOperator = null;
    }

    @Test
    public void testHashPass() {
        Assertions.assertEquals("b10a8db164e0754105b7a99be72e3fe5", hashOperator.hash("Hello World", Hashable.HashType.MD5).orElse(""));
        Assertions.assertEquals("0a4d55a8d778e5022fab701977c5d840bbc486d0", hashOperator.hash("Hello World", Hashable.HashType.SHA1).orElse(""));
        Assertions.assertEquals("a591a6d40bf420404a011733cfb7b190d62c65bf0bcda32b57b277d9ad9f146e", hashOperator.hash("Hello World", Hashable.HashType.SHA256).orElse(""));
        Assertions.assertEquals("99514329186b2f6ae4a1329e7ee6c610a729636335174ac6b740f9028396fcc803d0e93863a7c3d90f86beee782f4f3f", hashOperator.hash("Hello World", Hashable.HashType.SHA384).orElse(""));
        Assertions.assertEquals("2c74fd17edafd80e8447b0d46741ee243b7eb74dd2149a0ab1b9246fb30382f27e853d8585719e0e67cbda0daa8f51671064615d645ae27acb15bfb1447f459b", hashOperator.hash("Hello World", Hashable.HashType.SHA512).orElse(""));
    }

    @Test
    public void testHashFailInvalidHashType() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            hashOperator.hash("Hello World", Hashable.HashType.valueOf("INVALID")).orElse("");
        });
    }

    @Test
    public void testHashFailInvalidData() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            hashOperator.hash(null, Hashable.HashType.MD5).orElse("");
        });
    }

    @Test
    public void testHashFailBadHashType() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            hashOperator.hash("Hello World", null).orElse("");
        });
    }

}
