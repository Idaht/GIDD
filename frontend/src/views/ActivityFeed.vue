<template>
  <div>
    <div class="header" id="upper-header">
      <h3>Finn aktiviteter</h3>
      <h3 @click="mapViewClicked">Feed/Kart</h3>
      <!--Knapp som bytter mellom kart og feed -->
    </div>
    <div class="header" id="lower-header">
      <h3 @click="sortClicked">Sortering</h3>
      <h3 @click="filterClicked">Filter</h3>
    </div>
  </div>
  <div id="activities">
    <li v-for="activity in activitiesTest" :key="activity.id">
      <ActivityPreviewFeed :activityData="activity" />
    </li>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";
import ActivityPreviewFeed from "../components/ActivityFeedItem.vue";
import { useRouter } from "vue-router";
import axios from "axios";
import IActivity from "@/interfaces/IActivity.interface";

export default defineComponent({
  name: "ActivityFeed",
  components: {
    ActivityPreviewFeed,
  },
  //TODO: Fiks problemet med at 'async' forårsaker at ingen ting blir load'et
  /*async*/ setup() {
    //const response = await axios.get("/activities");
    //const activities = ref(response.data);

    const sortClicked = (): void => {
      console.log("Sort clicked");
    };

    const filterClicked = (): void => {
      console.log("Filter clicked");
    };

    const mapViewClicked = (): void => {
      console.log("MapView clicked");
    };

    return {
      sortClicked,
      filterClicked,
      mapViewClicked,
      //activities,
      activitiesTest: [
        {
          type: Object as () => IActivity,
          id: 1,
          description: "Test description",
          equipment: "Utstyr",
          difficulty: "Vanskelig",
          city: "Trondheim",
          place: "Kongensgate",
          longitude: 1,
          latitude: 1,
          startTime: "19:00",
          durationMinutes: 60,
          isPrivateActivity: false,
        },
        {
          type: Object as () => IActivity,
          id: 2,
          description: "Test description",
          equipment: "Utstyr",
          difficulty: "Vanskelig",
          city: "Trondheim",
          place: "Kongensgate",
          longitude: 1,
          latitude: 1,
          startTime: "19:00",
          durationMinutes: 60,
          isPrivateActivity: false,
        },
        {
          type: Object as () => IActivity,
          id: 3,
          description: "Test description",
          equipment: "Utstyr",
          difficulty: "Vanskelig",
          city: "Trondheim",
          place: "Kongensgate",
          longitude: 1,
          latitude: 1,
          startTime: "19:00",
          durationMinutes: 60,
          isPrivateActivity: false,
        },
        {
          type: Object as () => IActivity,
          id: 4,
          description: "Test description",
          equipment: "Utstyr",
          difficulty: "Vanskelig",
          city: "Trondheim",
          place: "Kongensgate",
          longitude: 1,
          latitude: 1,
          startTime: "19:00",
          durationMinutes: 60,
          isPrivateActivity: false,
        },
      ],
    };
  },
});
</script>

<style scoped lang="scss">
div {
  border: 1px black solid;
  margin: 5px;
  background-color: rgb(241, 188, 64);
}

#activities {
  background-color: rgb(83, 127, 197);
}

#activities > div div {
  background-color: rgb(83, 127, 197);
}
.header {
  display: flex;
  flex-direction: row;
  justify-content: center;
}

#lower-header {
  justify-content: start;
}

#lower-header > h3 {
  cursor: pointer;
}
</style>
