<template>
  <div id="activity" @click="activityClicked">
    <div id="map">
      <img id="map-img" :src="'https://maps.googleapis.com/maps/api/staticmap?center=' + activityData.latitude + ',' + activityData.longitude + '&zoom=14&size=600x350&markers=color:blue%7Clabel:A%7C' + activityData.latitude + ',' + activityData.longitude + '&key=' + apiKey" alt="Map" />
    </div>
    <div>
      <h3>{{ activityData.title }}</h3>
      <p>{{ activityData.startTime }}</p>
      <p>{{ location }}</p>
    </div>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent } from "vue";
import { useRouter } from "vue-router";
import IActivity from "../interfaces/IActivity.interface";
import data from "@/../config.json";

export default defineComponent({
  name: "ActivityPreviewFeed",

  props: {
    activityData: {
      type: Object as () => IActivity,
      required: true,
    },
  },

  setup(props) {
    const router = useRouter();
    const location = computed((): string => {
      return (
        props.activityData.place +
        ", " +
        props.activityData.city
      );
    });

    const apiKey = data.googleAPIKey;

    const activityClicked = (): void => {
      //TODO: Route til siden som viser aktiviteten
      router.push("/activity/" + props.activityData.activityId);
      console.log("Activity clicked: " + props.activityData.activityId);
    };

    return {
      location,
      activityClicked,
      apiKey,
    };
  },
});
</script>

<style scoped>
#activity {
  cursor: pointer;
  text-align: left;
}

#activity:hover {
  border-radius: 20px;
  background-color: #F0F0F0;
}

h3 {
  margin: 6px;
}

p {
  padding-left: 6px;
  margin: 0px;
}

#map-img {
  border-radius: 20px;
  width: 100%;
  @media only screen and (min-width: 600px) {
    width: 100%;
    height: auto;
  }
}

</style>
