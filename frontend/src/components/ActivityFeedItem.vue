<template>
  <div id="activity" @click="activityClicked">
    <div id="map">
      <img v-if="activityHasImage" :src="getImagesFromDb" />
      <img
        v-else
        id="map-img"
        :src="
          'https://maps.googleapis.com/maps/api/staticmap?center=' +
          activityData.latitude +
          ',' +
          activityData.longitude +
          '&zoom=14&size=600x350&markers=color:blue%7Clabel:A%7C' +
          activityData.latitude +
          ',' +
          activityData.longitude +
          '&key=' +
          apiKey
        "
        alt="Map"
      />
    </div>
    <div>
      <h3>{{ activityData.title }}</h3>
      <p>{{ dateTimeFormatter }}</p>
      <p>{{ location }}</p>
      <p>{{ difficulty }}</p>
    </div>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent, ref } from "vue";
import { useRouter } from "vue-router";
import IActivity from "../interfaces/Activity/IActivity.interface";
import data from "@/../config.json";
import getActivityDifficultyName from "../utils/getActivityDifficultyName";

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
      return props.activityData.place + ", " + props.activityData.city;
    });

    const apiKey = data.googleAPIKey;

    const difficulty = computed(() => {
      return getActivityDifficultyName(props.activityData.difficulty || 0);
    });

    const activityClicked = (): void => {
      //TODO: Route til siden som viser aktiviteten
      router.push("/activity/" + props.activityData.activityId);
      console.log("Activity clicked: " + props.activityData.activityId);
    };

    const getImagesFromDb = computed(() => {
      return props.activityData.activityPicture;
    });

    const activityHasImage = computed(() => {
      return (
        props.activityData.activityPicture !== "" &&
        props.activityData.activityPicture !== null
      );
    });

    /**
     * Formats the date and time
     */
    const dateTimeFormatter = computed(() => {
      const temp = props.activityData.startTime.split(" ");
      const dateArray = temp[0].split("-");
      const date = ref(dateArray[2] + "/" + dateArray[1] + "/" + dateArray[0]);
      const time = ref(temp[1]);
      return date.value + " kl. " + time.value;
    });

    return {
      difficulty,
      location,
      activityClicked,
      apiKey,
      activityHasImage,
      getImagesFromDb,
      dateTimeFormatter,
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
  background-color: #f0f0f0;
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

function getActivityDifficultyName(difficulty: number): any { throw new
Error("Function not implemented."); } function
getActivityDifficultyName(difficulty: number): any { throw new Error("Function
not implemented."); }
