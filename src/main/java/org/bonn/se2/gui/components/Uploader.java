package org.bonn.se2.gui.components;

import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.ui.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class Uploader extends VerticalLayout {

    void basic() {
        final Embedded image = new Embedded("Uploaded Image");
        image.setVisible(false);

        class ImageUploader implements Upload.Receiver, Upload.SucceededListener {

            public File file;

            @Override
            public OutputStream receiveUpload(String filename, String mimeType) {
                FileOutputStream fos = null; // Stream to write to
                try {
                    // Open the file for writing.
                    file = new File("/tmp/uploads/" + filename);
                    fos = new FileOutputStream(file);
                } catch (final java.io.FileNotFoundException e) {
                    new Notification("Could not open file<br/>",
                            e.getMessage(),
                            Notification.Type.ERROR_MESSAGE)
                            .show(Page.getCurrent());
                    return null;
                }
                return fos; // Return the output stream to write to
            }

            @Override
            public void uploadSucceeded(Upload.SucceededEvent event) {
                image.setVisible(true);
                image.setSource(new FileResource(file));
            }
        }

        ImageUploader receiver = new ImageUploader();

        // Create the upload with a caption and set receiver later
        final Upload upload = new Upload("Upload Image Here", receiver);
        upload.setButtonCaption("Start Upload");
        upload.addSucceededListener(receiver);

        // Prevent too big downloads
        final long UPLOAD_LIMIT = 1000000L;
        upload.addStartedListener((Upload.StartedListener) event -> {
            if (event.getContentLength() > UPLOAD_LIMIT) {
                Notification.show("File too big",
                        Notification.Type.ERROR_MESSAGE);
                upload.interruptUpload();
            }
        });

        // Check the size also during progress
        upload.addProgressListener((Upload.ProgressListener) (readBytes, contentLength) -> {
            if (readBytes > UPLOAD_LIMIT) {
                Notification.show("File too big",
                        Notification.Type.ERROR_MESSAGE);
                upload.interruptUpload();
            }
        });

        // Put the components in a panel
        Panel panel = new Panel("Cool Image Storage");
        VerticalLayout panelContent = new VerticalLayout();
        panelContent.setMargin(true);
        panelContent.addComponents(upload, image);
        panel.setContent(panelContent);

        // Create uploads directory
        File uploads = new File("/tmp/uploads");
        if (!uploads.exists() && !uploads.mkdir())
            addComponent(new Label("ERROR: Could not create upload dir"));

        ((VerticalLayout) panel.getContent()).setSpacing(true);
        panel.setWidth("-1");
        addComponent(panel);
    }

}
