/*
 * @version 0.1a
 * @author Coll@Aldernativ
 * @Programmer
 *
 *
 */

package org.bonn.se2.services.util;

import com.vaadin.server.Page;
import com.vaadin.server.StreamResource;
import com.vaadin.shared.Position;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;
import org.bonn.se2.gui.windows.EditStudentWindow;
import org.bonn.se2.model.dao.StudentDAO;
import org.bonn.se2.model.objects.dto.Document;
import org.bonn.se2.model.objects.dto.Student;

import java.io.*;

public class FileUploader implements Upload.Receiver, Upload.SucceededListener {

    public File file;
    public String mimeType;
    private String filename;

    public static byte[] toByteArray(FileInputStream fis) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = fis.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        return bos.toByteArray();
    }

    public static Image convertToImage(final byte[] imageData) {
        StreamResource.StreamSource streamSource = (StreamResource.StreamSource) () -> (imageData == null) ? null : new ByteArrayInputStream(
                imageData);
        return new Image(null, new StreamResource(streamSource, "streamedSourceFromByteArray"));
    }

    @Override
    public OutputStream receiveUpload(String filename, String mimeType) {

        try {
            this.file = File.createTempFile("tmp-", "." + mimeType.split("/")[1]);
            this.mimeType = mimeType;

            return new FileOutputStream(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void uploadSucceeded(Upload.SucceededEvent event) {

        Notification notification = new Notification("Information");
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (this.mimeType.contains("pdf")) {
            try {
                Student st = new StudentDAO().retrieve(SessionFunctions.getCurrentUser().getUsername());
                if (st != null) {
                    Document doc = new Document();
                    assert fis != null;
                    doc.setFile(toByteArray(fis));
                    doc.setUserID(SessionFunctions.getCurrentUser().getUserID());
                    doc.setTitle(filename);

                    //DocumentDAO ddoa = new DocumentDAO();
                    //ddoa.updateOne(doc);

                    notification.setDescription("Lebenslauf hochgeladen. Bitte speichern nicht vergessen.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (this.mimeType.contains("image")) {
            try {
                SessionFunctions.getCurrentUser().setImage(toByteArray(fis));
                notification.setDescription("Profilbild hochgeladen. Bitte speichern nicht vergessen.");
                notification.setDelayMsec(3000);

                if (SessionFunctions.getCurrentRole().equals(Configuration.Roles.STUDENT)) {
                    EditStudentWindow.refreshProfilePic(convertToImage(SessionFunctions.getCurrentUser().getImage()));
                }

            } catch (IOException e) {
                e.printStackTrace();
                notification.setDescription("Fehler");
            }
        } else {
            notification.setDescription("Dieser Datentyp wird nicht akzeptiert.");
        }

        notification.setDelayMsec(20000);
        notification.setPosition(Position.BOTTOM_RIGHT);
        notification.show(Page.getCurrent());
    }

}
