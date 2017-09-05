package illiyin.mhandharbeni.burgertahudelivery.fragment.sub.model;

/**
 * Created by root on 06/08/17.
 */

public class ModelAddress {
    Integer distance;
    String alamat;
    String id_outlet;
    Double latitude, longitude;


    public ModelAddress(Integer distance, String alamat, String id_outlet, Double latitude, Double longitude) {
        this.distance = distance;
        this.alamat = alamat;
        this.id_outlet = id_outlet;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getId_outlet() {
        return id_outlet;
    }

    public void setId_outlet(String id_outlet) {
        this.id_outlet = id_outlet;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
