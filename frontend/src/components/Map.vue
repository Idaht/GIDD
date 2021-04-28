<template>
  <div id="map"></div>
</template>

<script lang="ts">
/*global google*/ //This line of code MUST be here, otherwise Eslint will have a stroke

import { defineComponent, watch, inject } from "vue";
import IActivity from "../interfaces/Activity/IActivity.interface";
import ICoordinates from "@/interfaces/ICoordinates.interface";
import data from "@/../config.json";
import { Loader } from "@googlemaps/js-api-loader";
import getActivityDifficultyNames from "../utils/getActivityDifficultyName";
import { useRouter } from "vue-router";

export default defineComponent({
  name: "Map",

  props: {
    center: {
      type: Object as () => ICoordinates,
      required: true,
    },
    zoom: Number,
    mapTypeId: String,
    disableDefaultUI: Boolean,
    activityData: {
      type: Array,
      required: true,
    },
    getLocation: Boolean,
    setLocation: Boolean,
  },

  setup(props) {
    const router = useRouter();
    let map: google.maps.Map;
    let marker: google.maps.Marker; //This is used to place a marker when retriving coordinates from the map
    const choosenLocation = inject("coordinates", {
      lat: 0.0,
      lng: 0.0,
    } as ICoordinates);

    function initMap(): google.maps.Map {
      map = new google.maps.Map(document.getElementById("map") as HTMLElement, {
        center: { lat: props.center.lat, lng: props.center.lng },
        zoom: props.zoom || 14,
        mapTypeId: props.mapTypeId || "roadmap",
        disableDefaultUI: props.disableDefaultUI || true,
      });
      if (props.setLocation) {
        marker = new window.google.maps.Marker({
            position: map.getCenter()
          });
        marker.setMap(map);
      }
      if (props.getLocation) {
        //If the map will be used to retrive a postion based on a click
        map.addListener("click", (mapsMouseEvent: any) => {
          if (marker != null) {
            marker.setMap(null); //If the marker is already on the map; remove it
          }
          marker = new window.google.maps.Marker({
            position: mapsMouseEvent.latLng,
          });
          marker.setMap(map);
          choosenLocation.lat = mapsMouseEvent.latLng.lat();
          choosenLocation.lng = mapsMouseEvent.latLng.lng();
        });
      }
      return map;
    }

    watch(
      () => props.activityData,
      (newValue, oldValue) => {
        if (newValue != oldValue) {
          const infoWindow = new window.google.maps.InfoWindow();

          props.activityData.forEach((element) => {
            const activity = element as IActivity;
            let marker = new window.google.maps.Marker({
              position: { lat: activity.latitude, lng: activity.longitude },
            });

            marker.setMap(map);
            marker.setTitle(activity.description + " | " + activity.activityId);
            const contentString =
              '<div id="activity">' +
              "<div><h3>" +
              activity.description +
              "</h3><h4>" +
              activity.startTime +
              " | " +
              activity.place +
              ", " +
              activity.city +
              "</h4></div><div><h4>" +
              getActivityDifficultyNames(activity.difficulty || 0) +
              "</h4></div></div>";
            marker.addListener("click", () => {
              infoWindow.close();
              infoWindow.setContent(contentString);
              infoWindow.open(marker.getMap(), marker);
            });
          });
        }
      }
    );

    const loader = new Loader({
      apiKey: data.googleAPIKey,
      version: "weekly",
    });

    loader.load().then(() => {
      if (map == null) {
        map = initMap();
      }
    });

    return {
      choosenLocation,
    };
  },
});
</script>

<style scoped>
#map {
  height: 100%;
}

html,
body {
  height: 100%;
  width: 100%;
  margin: 0;
  padding: 0;
}
</style>
