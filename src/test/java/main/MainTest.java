package main;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

class MainTest {

    @Test
    void testGetNameWithoutPath() throws MalformedURLException {
        final var name = Main.getName(new URL("http://example.com/"));
        assertThat(name).isEqualTo("example.com.pdf");
    }

    @Test
    void testGetNameWithPath() throws MalformedURLException {
        final var name = Main.getName(new URL("http://example.com/test"));
        assertThat(name).isEqualTo("test.pdf");
    }

    @Test
    void testGeneratePdf() {
        assertThatNoException().isThrownBy(() -> Main.generatePDFFromHTML(new URL("http://example.com/")));
    }
}
