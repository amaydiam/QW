package com.qwash.washer.api.model.wash_history;

/**
 * Created by Amay on 12/29/2016.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.qwash.washer.model.wash_history.WashHistory;

import java.util.List;

public class WashHistoryListResponse {



        @SerializedName("status")
        @Expose
        private Boolean status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private List<WashHistory> data = null;

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<WashHistory> getData() {
            return data;
        }

        public void setData(List<WashHistory> data) {
            this.data = data;
        }


}
