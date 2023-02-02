/* This is just a placeholder. */
package ocr;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OCRTest {

    @Test
    void testNullInputTop() { assertThrows(OCRException.class, () -> new OCRTranslator().translate(null, "","")); }

    @Test
    void testNullInputMiddle() { assertThrows(OCRException.class, () -> new OCRTranslator().translate("", null,"")); }

    @Test
    void testNullInputBottom() { assertThrows(OCRException.class, () -> new OCRTranslator().translate("", "",null)); }

    @Test
    void testEmptyInput() {
        OCRTranslator ocrTranslator = new OCRTranslator();
        assertEquals("", ocrTranslator.translate("", "", ""));
    }

    @Test
    void testDifferentLengthInputTop() {
        assertThrows(OCRException.class, () -> new OCRTranslator().translate(" ", "",""));
    }

    @Test
    void testDifferentLengthInputMiddle() {
        assertThrows(OCRException.class, () -> new OCRTranslator().translate("", " ",""));
    }

    @Test
    void testDifferentLengthInputBottom() {
        assertThrows(OCRException.class, () -> new OCRTranslator().translate("", ""," "));
    }

    @Test
    void testInvalidChars() { assertThrows(OCRException.class, () -> new OCRTranslator().translate("asd", "123","3as")); }

    @Test
    void testOCR0() {
        OCRTranslator ocrTranslator = new OCRTranslator();
        assertEquals("0", ocrTranslator.translate(" _ ", "| |", "|_|"));
    }

    @Test
    void testOCR1() {
        OCRTranslator ocrTranslator = new OCRTranslator();
        assertEquals("1", ocrTranslator.translate(" ", "|", "|"));
    }

    @Test
    void testOCR2() {
        OCRTranslator ocrTranslator = new OCRTranslator();
        assertEquals("2", ocrTranslator.translate(" _ ", " _|", "|_ "));
    }
    @Test
    void testOCR3() {
        OCRTranslator ocrTranslator = new OCRTranslator();
        assertEquals("3", ocrTranslator.translate("_ ", "_|", "_|"));
    }
    @Test
    void testOCR4() {
        OCRTranslator ocrTranslator = new OCRTranslator();
        assertEquals("4", ocrTranslator.translate("   ", "|_|", "  |"));
    }
    @Test
    void testOCR5() {
        OCRTranslator ocrTranslator = new OCRTranslator();
        assertEquals("5", ocrTranslator.translate(" _ ", "|_ ", " _|"));
    }
    @Test
    void testOCR6() {
        OCRTranslator ocrTranslator = new OCRTranslator();
        assertEquals("6", ocrTranslator.translate(" _ ", "|_ ", "|_|"));
    }
    @Test
    void testOCR7() {
        OCRTranslator ocrTranslator = new OCRTranslator();
        assertEquals("7", ocrTranslator.translate("_ ", " |", " |"));
    }
    @Test
    void testOCR8() {
        OCRTranslator ocrTranslator = new OCRTranslator();
        assertEquals("8", ocrTranslator.translate(" _ ", "|_|", "|_|"));
    }
    @Test
    void testOCR9() {
        OCRTranslator ocrTranslator = new OCRTranslator();
        assertEquals("9", ocrTranslator.translate(" _ ", "|_|", "  |"));
    }

    @Test
    void testInvalidOCRDigit() {
        assertThrows(OCRException.class, () -> new OCRTranslator().translate("___", "|_ "," | "));
    }
    @Test
    void testValidAndInvalidOCRDigits() {
        assertThrows(OCRException.class, () -> new OCRTranslator().translate(" _   _ ", "|_| |_|","    |_|"));
    }

    @Test
    void testOCRStartWithSpaces() {
        OCRTranslator ocrTranslator = new OCRTranslator();
        assertEquals("1", ocrTranslator.translate("    ", "   |", "   |"));
    }
    @Test
    void testOCREndWithSpaces() {
        OCRTranslator ocrTranslator = new OCRTranslator();
        assertEquals("1", ocrTranslator.translate("    ", "|   ", "|   "));
    }
    @Test
    void testMultiDigitInput() {
        OCRTranslator ocrTranslator = new OCRTranslator();
        assertEquals("0123456789", ocrTranslator.translate("  _     _  _       _   _  _   _   _ ", " | | |  _| _| |_| |_  |_   | |_| |_|", " |_| | |_  _|   |  _| |_|  | |_|   |"));
    }
    @Test
    void testMultiDigitInputDoubleSpaces() {
        OCRTranslator ocrTranslator = new OCRTranslator();
        assertEquals("04", ocrTranslator.translate(" _      ", "| |  |_|", "|_|    |"));
    }
}
