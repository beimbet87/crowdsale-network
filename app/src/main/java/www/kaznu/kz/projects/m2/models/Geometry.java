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

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class Geometry implements Serializable {

  private static final long serialVersionUID = 1L;

  public Bounds bounds;

  public LatLng location;

  public LocationType locationType;

  public Bounds viewport;

  @Override
  public String toString() {
    return String.format(
        "[Geometry: %s (%s) bounds=%s, viewport=%s]", location, locationType, bounds, viewport);
  }
}
