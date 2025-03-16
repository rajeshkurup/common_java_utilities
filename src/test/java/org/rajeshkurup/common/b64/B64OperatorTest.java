package org.rajeshkurup.common.b64;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class B64OperatorTest {

    private B64Operations b64Operations;

    @BeforeEach
    public void init() {
        this.b64Operations = new B64Operator();
    }

    @AfterEach
    public void cleanUp() {
        this.b64Operations = null;
    }

    @Test
    public void testEncodePass() {
        Assertions.assertEquals("SGVsbG8gV29ybGQh", b64Operations.encode("Hello World!").orElse(""));
        Assertions.assertEquals("", b64Operations.encode("").orElse(""));
    }

    @Test
    public void testEncodeFail() {
        Assertions.assertThrows(NullPointerException.class, () -> b64Operations.encode(null));
    }

    @Test
    public void testDecodePass() {
        Assertions.assertEquals("Hello World!", b64Operations.decode("SGVsbG8gV29ybGQh").orElse(""));
        Assertions.assertEquals("", b64Operations.encode("").orElse(""));
    }

    @Test
    public void testDecodeFailInvalidB64Token() {
        Assertions.assertEquals("", b64Operations.decode("Hello World!").orElse(""));
    }

    @Test
    public void testDecodeFail() {
        Assertions.assertThrows(NullPointerException.class, () -> b64Operations.decode(null));
    }

}
