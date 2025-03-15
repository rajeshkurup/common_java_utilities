package org.rajeshkurup.common.fileops;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileOperatorTest {

    private FileOperations fileOperator;

    @BeforeEach
    public void init() {
        this.fileOperator = new FileOperator();
    }

    @AfterEach
    public void cleanUp() {
        this.fileOperator = null;
    }

    @Test
    public void testCreateDeleteFolderPass() {
        Assertions.assertDoesNotThrow(() -> {
            Assertions.assertTrue(this.fileOperator.createFolder("/tmp/testfolder"));
        });
        Assertions.assertTrue(this.fileOperator.isExist("/tmp/testfolder"));
        Assertions.assertDoesNotThrow(() -> {
            Assertions.assertTrue(this.fileOperator.delete("/tmp/testfolder"));
        });
        Assertions.assertFalse(this.fileOperator.isExist("/tmp/testfolder"));
        Assertions.assertDoesNotThrow(() -> {
            Assertions.assertTrue(this.fileOperator.createFolder("/tmp/testfolder2"));
        });
        Assertions.assertTrue(this.fileOperator.isExist("/tmp/testfolder2"));
        Assertions.assertDoesNotThrow(() -> {
            Assertions.assertTrue(this.fileOperator.delete("/tmp/testfolder2"));
        });
        Assertions.assertFalse(this.fileOperator.isExist("/tmp/testfolder2"));
    }

    @Test
    public void testReadWritePass() {
        Assertions.assertDoesNotThrow(() -> {
            Assertions.assertTrue(this.fileOperator.write("/tmp/testfolder/testfile.txt", "Hello World", true));
        });
        Assertions.assertTrue(this.fileOperator.isExist("/tmp/testfolder/testfile.txt"));
        Assertions.assertDoesNotThrow(() -> {
            Assertions.assertTrue(this.fileOperator.size("/tmp/testfolder/testfile.txt") > 0);
        });
        Assertions.assertDoesNotThrow(() -> {
            Assertions.assertEquals("Hello World\n", this.fileOperator.read("/tmp/testfolder/testfile.txt").orElse(""));
        });
        Assertions.assertDoesNotThrow(() -> {
            Assertions.assertTrue(this.fileOperator.delete("/tmp/testfolder/testfile.txt"));
        });
        Assertions.assertFalse(this.fileOperator.isExist("/tmp/testfolder/testfile.txt"));
    }

    @Test
    public void testReadWriteLinesPass() {
        Assertions.assertDoesNotThrow(() -> {
            Assertions.assertTrue(this.fileOperator.writeLines("/tmp/testfolder/testfile.txt", List.of("Hello World1", "Hello World2"), true));
        });
        Assertions.assertTrue(this.fileOperator.isExist("/tmp/testfolder/testfile.txt"));
        Assertions.assertDoesNotThrow(() -> {
            Assertions.assertTrue(this.fileOperator.size("/tmp/testfolder/testfile.txt") > 0);
        });
        Assertions.assertDoesNotThrow(() -> {
            Assertions.assertEquals("[Hello World1, Hello World2]", this.fileOperator.readLines("/tmp/testfolder/testfile.txt").get().toString());
        });
        Assertions.assertDoesNotThrow(() -> {
            Assertions.assertTrue(this.fileOperator.delete("/tmp/testfolder/testfile.txt"));
        });
        Assertions.assertFalse(this.fileOperator.isExist("/tmp/testfolder/testfile.txt"));
    }

    @Test
    public void testListFilesPass() {
        Assertions.assertDoesNotThrow(() -> {
            Assertions.assertTrue(this.fileOperator.writeLines("/tmp/testfolder/testfile.txt", List.of("Hello World1", "Hello World2"), true));
        });
        Assertions.assertTrue(this.fileOperator.isExist("/tmp/testfolder/testfile.txt"));
        Assertions.assertDoesNotThrow(() -> {
            Assertions.assertEquals("[testfile.txt]", this.fileOperator.listFiles("/tmp/testfolder").get().toString());
        });
        Assertions.assertDoesNotThrow(() -> {
            Assertions.assertTrue(this.fileOperator.delete("/tmp/testfolder/testfile.txt"));
        });
        Assertions.assertFalse(this.fileOperator.isExist("/tmp/testfolder/testfile.txt"));
    }

    @Test
    public void testCopyPass() {
        Assertions.assertDoesNotThrow(() -> {
            Assertions.assertTrue(this.fileOperator.writeLines("/tmp/testfolder/testfile.txt", List.of("Hello World1", "Hello World2"), true));
        });
        Assertions.assertTrue(this.fileOperator.isExist("/tmp/testfolder/testfile.txt"));
        Assertions.assertDoesNotThrow(() -> {
            Assertions.assertTrue(this.fileOperator.copy("/tmp/testfolder", "/tmp/testfolder2"));
        });
        Assertions.assertTrue(this.fileOperator.isExist("/tmp/testfolder2"));
        Assertions.assertDoesNotThrow(() -> {
            Assertions.assertEquals("[testfolder]", this.fileOperator.listFiles("/tmp/testfolder2").get().toString());
        });
        Assertions.assertDoesNotThrow(() -> {
            Assertions.assertTrue(this.fileOperator.delete("/tmp/testfolder"));
        });
        Assertions.assertFalse(this.fileOperator.isExist("/tmp/testfolder"));
        Assertions.assertDoesNotThrow(() -> {
            Assertions.assertTrue(this.fileOperator.delete("/tmp/testfolder2"));
        });
        Assertions.assertFalse(this.fileOperator.isExist("/tmp/testfolder2"));
    }

    @Test
    public void testMovePass() {
        Assertions.assertDoesNotThrow(() -> {
            Assertions.assertTrue(this.fileOperator.writeLines("/tmp/testfolder/testfile.txt", List.of("Hello World1", "Hello World2"), true));
        });
        Assertions.assertTrue(this.fileOperator.isExist("/tmp/testfolder/testfile.txt"));
        Assertions.assertDoesNotThrow(() -> {
            Assertions.assertTrue(this.fileOperator.move("/tmp/testfolder", "/tmp/testfolder2"));
        });
        Assertions.assertTrue(this.fileOperator.isExist("/tmp/testfolder2"));
        Assertions.assertFalse(this.fileOperator.isExist("/tmp/testfolder"));
        Assertions.assertDoesNotThrow(() -> {
            Assertions.assertEquals("[testfolder]", this.fileOperator.listFiles("/tmp/testfolder2").get().toString());
        });
        Assertions.assertDoesNotThrow(() -> {
            Assertions.assertTrue(this.fileOperator.delete("/tmp/testfolder2"));
        });
        Assertions.assertFalse(this.fileOperator.isExist("/tmp/testfolder2"));
    }

}
