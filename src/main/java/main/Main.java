package main;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
import com.itextpdf.styledxmlparser.css.media.MediaType;
import com.itextpdf.text.PageSize;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        try {
            if (Objects.nonNull(args) && args.length > 0) {
                var start = Instant.now();
                final var url = new URL(args[0]);
                generatePDFFromHTML(fetchHtml(url), url);
                System.out.println("html to pdf conversion done in: " + Duration.between(start, Instant.now()).getSeconds() + "s");
            } else {
                System.err.println("no url given. good bye...");
                System.exit(-1);
            }

        } catch (Exception e) {
            System.err.println(e);
        } finally {
            System.exit(1);
        }
    }

    private static InputStream fetchHtml(URL url) throws IOException {
        return new BufferedInputStream(url.openStream());
    }

    private static void generatePDFFromHTML(InputStream inputStream, URL url) throws IOException {
        var start = Instant.now();
        ConverterProperties properties = new ConverterProperties();
        properties.setBaseUri(url.getProtocol() + "://" + url.getHost());

        MediaDeviceDescription mediaDeviceDescription = new MediaDeviceDescription(MediaType.PRINT);
        mediaDeviceDescription.setWidth(PageSize.A4.getWidth());

        properties.setMediaDeviceDescription(mediaDeviceDescription);

        HtmlConverter.convertToPdf(inputStream, new FileOutputStream(getName(url)), properties);
        System.out.println("pdf generation done in: " + Duration.between(start, Instant.now()).getSeconds() + "s");
    }

    private static String getName(URL url) {
        return url.getPath().substring(url.getPath().lastIndexOf("/") + 1) + ".pdf";
    }
}
