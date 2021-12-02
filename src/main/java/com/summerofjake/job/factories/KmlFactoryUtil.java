package com.summerofjake.job.factories;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
import de.micromata.opengis.kml.v_2_2_0.Kml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class KmlFactoryUtil {

    public static Marshaller createMarshaller() {
        Marshaller m = null;
        try {
            m = JAXBContext.newInstance(new Class[] { Kml.class }).createMarshaller();
            m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, true );
            m.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NamespacePrefixMapper()
            {
                @Override
                public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix)
                {
                    return namespaceUri.matches("http://www.w3.org/\\d{4}/Atom") ? "atom"
                            : (
                            namespaceUri.matches("urn:oasis:names:tc:ciq:xsdschema:xAL:.*?") ? "xal"
                                    : (
                                    namespaceUri.matches("http://www.google.com/kml/ext/.*?") ? "gx"
                                            : (
                                            namespaceUri.matches("http://www.opengis.net/kml/.*?") ? ""
                                                    : (
                                                    null
                                            )
                                    )
                            )
                    );
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return m;
    }
}
