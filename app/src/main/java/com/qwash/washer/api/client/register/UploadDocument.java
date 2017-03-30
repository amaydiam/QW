package com.qwash.washer.api.client.register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Amay on 3/27/2017.
 */

public class UploadDocument {
        @SerializedName("status")
        @Expose
        private Boolean status;
        @SerializedName("uploads")
        @Expose
        private String uploads;

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }

        public String getUploads() {
            return uploads;
        }

        public void setUploads(String uploads) {
            this.uploads = uploads;
        }

}