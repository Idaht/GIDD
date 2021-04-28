<template>
  <div id="map"></div>
</template>

<script lang="ts">
/*global google*/ //This line of code MUST be here, otherwise Eslint will have a stroke

import { defineComponent, watch } from "vue";
import IActivity from "@/interfaces/IActivity.interface";
import ICoordinates from "@/interfaces/ICoordinates.interface";
import data from "@/../config.json";
import { Loader } from "@googlemaps/js-api-loader";

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
  },

  setup(props) {
    let map: google.maps.Map;

    function initMap(): google.maps.Map {
      map = new google.maps.Map(document.getElementById("map") as HTMLElement, {
        center: { lat: props.center.lat, lng: props.center.lng },
        zoom: props.zoom || 14,
        mapTypeId: props.mapTypeId || "roadmap",
        disableDefaultUI: props.disableDefaultUI || true,
      });
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
              title: "Hello World!",
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

    return {};
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
