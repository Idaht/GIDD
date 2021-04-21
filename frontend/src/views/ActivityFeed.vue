<template>
<div> 
  <div>
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
      
        <ActivityFeedItem v-for="activity in activities" :key="activity.activityId" :activityData="activity"/>
      
    </div>
  </div>
</div>
</template>

<script lang="ts">

import { defineComponent, onBeforeMount, ref, Ref } from "vue";
import ActivityFeedItem from "../components/ActivityFeedItem.vue";
import axios from "@/axiosConfig";
import { useRouter } from "vue-router";
import IActivity from "@/interfaces/IActivity.interface";

export default defineComponent({
  name: "ActivityFeed",
  components: {
    ActivityFeedItem,
  },

  
  setup() {
    const router = useRouter();
    const activities : Ref<IActivity[]> = ref([]);

    onBeforeMount(async () => {
      try {
        const response = await axios.get("/activities");
        activities.value = response.data as IActivity[];
      } catch (error) {
        router.push("/error");
      }
    });

    const sortClicked = (): void => {
      //TODO: Open the sorting functionality, and remove console.log
      console.log("Sort clicked");
    };

    const filterClicked = (): void => {
      //TODO: Open the filtering functionality, and remove console.log
      console.log("Filter clicked");
    };

    const mapViewClicked = () : void => {
        router.push("/activity-map");
    };

    return {
      sortClicked,
      filterClicked,
      mapViewClicked,
      activities,
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
