/*
 * @version 0.1a
 * @author Coll@Aldernativ
 * @Programmer
 *
 *
 */

package org.bonn.se2.services.util;

import com.vaadin.server.StreamResource;

import java.io.ByteArrayInputStream;

public class Utils {

    public static StreamResource convertToPdf(byte[] bArray, String title) {
        StreamResource.StreamSource streamSource = (StreamResource.StreamSource) () ->
                (bArray == null) ? null : new ByteArrayInputStream(bArray);
        return new StreamResource(streamSource, title + ".pdf");
    }

}
