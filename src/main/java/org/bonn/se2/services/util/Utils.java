/*
 * @version 0.1a
 * @author Coll@Aldernativ
 * @Programmer
 *
 *
 */

package org.bonn.se2.services.util;

import com.vaadin.server.StreamResource;
import com.vaadin.ui.Image;

import java.io.ByteArrayInputStream;

public class Utils {

    public static Image convertToImg(final byte[] imageData) {
        StreamResource.StreamSource streamSource = (StreamResource.StreamSource) () ->
                (imageData == null) ? null : new ByteArrayInputStream(imageData);
        return new Image(null, new StreamResource(streamSource, "streamedSourceFromByteArray"));
    }

}
