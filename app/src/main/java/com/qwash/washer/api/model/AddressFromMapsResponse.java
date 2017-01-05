package com.ad.sample.api.model;

/**
 * Created by Amay on 12/29/2016.
 */


        import java.util.List;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class AddressFromMapsResponse {

    @SerializedName("results")
    @Expose
    private List<Address> results = null;
    @SerializedName("status")
    @Expose
    private String status;

    public List<Address> getResults() {
        return results;
    }

    public void setResults(List<Address> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

