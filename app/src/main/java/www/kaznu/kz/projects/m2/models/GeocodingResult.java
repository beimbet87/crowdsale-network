// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package www.kaznu.kz.projects.m2.models;

import java.io.Serializable;
import java.util.Arrays;

public class GeocodingResult implements Serializable {

    private static final long serialVersionUID = 1L;

    public String formattedAddress;

    public String[] postcodeLocalities;

    public Geometry geometry;

    public AddressType[] types;

    public boolean partialMatch;

    public String placeId;

    public PlusCode plusCode;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[GeocodingResult");
        if (partialMatch) {
            sb.append(" PARTIAL MATCH");
        }
        sb.append(" placeId=").append(placeId);
        sb.append(" ").append(geometry);
        sb.append(", formattedAddress=").append(formattedAddress);
        sb.append(", types=").append(Arrays.toString(types));
        if (postcodeLocalities != null && postcodeLocalities.length > 0) {
            sb.append(", postcodeLocalities=").append(Arrays.toString(postcodeLocalities));
        }
        sb.append("]");
        return sb.toString();
    }
}
