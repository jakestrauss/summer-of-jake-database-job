package com.summerofjake.job.factories;

import com.summerofjake.job.model.LatLong;
import com.summerofjake.job.strava.api.ActivityApi;
import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.LineString;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class KmlFactoryImpl implements KmlFactory {
    private ActivityApi activityApi;
    private Marshaller marshaller;

    public KmlFactoryImpl(ActivityApi activityApi) {
        this.activityApi = activityApi;
        this.marshaller = KmlFactoryUtil.createMarshaller();
    }


    @Override
    public String constructKmlUrl(String activityId) {
        List<LatLong> coordinateList = activityApi.getActivityRouteStream(activityId);
        Kml routeKml = createKml(coordinateList, activityId);
        

        return "";
    }

    private Kml createKml(List<LatLong> coordinateList, String activityId) {

        final Kml kml = new Kml();
        Document document = kml.createAndSetDocument().withName("Strava Activity ID: " + activityId).withOpen(true);
        Folder tracks = document.createAndAddFolder().withId("Tracks").withName("Tracks").withOpen(true);
        LineString lineString = tracks.createAndAddPlacemark().createAndSetLineString();
        for(LatLong latLong : coordinateList) {
            lineString.addToCoordinates(latLong.getLon(), latLong.getLat(), 0);
        }

        kml.setFeature(document);
        File file = new File(activityId + ".kml");
        try {
            marshaller.marshal(kml, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return kml;
    }
}
