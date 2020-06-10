package com.iminsys.examples.web.pdf.protect.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Controller
public class PDFDownloadController {

    // ************************************************************************
    // Acknowledgement: https://pdfbox.apache.org/2.0/cookbook/encryption.html
    // ************************************************************************

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(HttpServletResponse response) throws IOException {

        File file = new File("path/to/pdf/filename.pdf");

        PDDocument doc = PDDocument.load(file);

        // Define the length of the encryption key.
        // Possible values are 40 or 128 (256 will be available in PDFBox 2.0).
        int keyLength = 128;

        AccessPermission ap = new AccessPermission();

        // Disable printing, everything else is allowed
        ap.setCanPrint(false);

        StandardProtectionPolicy spp = new StandardProtectionPolicy("123456789", "123456789", ap);
        spp.setEncryptionKeyLength(keyLength);
        spp.setPermissions(ap);
        doc.protect(spp);

        doc.save(response.getOutputStream());
        doc.close();

        response.flushBuffer();
    }
}

