<template>
  <div id="activity-feed">
    <div>
      <div class="header" id="upper-header">
        <div id="header-title">
          <h2>Finn aktiviteter</h2>
        </div>
        <img
          id="map-view"
          @click="mapViewClicked"
          src="../../img/mapview.svg"
          alt="Logo"
          height="18"
        />
        <!--Knapp som bytter mellom kart og feed -->
      </div>
      <div class="header" id="lower-header">
        <select class="dropdown" id="lower-header-sort">
          <option selected hidden>Sortering</option>
          <option @click="sortClicked" value="Nyeste">Nyeste</option>
          <option @click="sortClicked" value="Avstand">Avstand</option>
          <option @click="sortClicked" value="Antall deltakere">
            Antall deltakere
          </option>
        </select>
        <div id="lower-header-filter" @click="filterClicked">+ Filter</div>
      </div>
    </div>
    <div id="activities">
      <li v-for="activity in activitiesTest" :key="activity.id">
        <ActivityPreviewFeed :activityData="activity" />
      </li>
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
@import url("https://fonts.googleapis.com/css2?family=Mulish&display=swap");

$primary-color: #282828;
$secondary-color: #ea4b4b;
$padding: 0.6rem 1rem 0.6rem 1rem;

#activity-feed {
  margin: 35px;
}

.header {
  display: grid;
  margin-bottom: 15px;
  @media only screen and (min-width: 600px) {
    grid-template-columns: 1fr 1fr 1fr 1fr;
  }
}

#header-title {
  grid-column: 1/3;
  @media only screen and (min-width: 600px) {
    grid-column: 2/4;
  }
}

h2 {
  font-weight: 600;
  text-align: left;
  margin: 0;
  @media only screen and (min-width: 600px) {
    text-align: center;
  }
}

#map-view {
  grid-column: 3/4;
  align-self: center;
  justify-self: end;
  @media only screen and (min-width: 600px) {
    grid-column: 3/4;
  }
}

#lower-header-sort,
#lower-header-filter {
  border-radius: 20px;
  font-size: 10px;
  letter-spacing: 1px;
  text-transform: uppercase;
  font-weight: 600 !important;
  padding: 5px 10px 5px 10px;
}

#lower-header-sort {
  box-shadow: 0px 0px 0px 1px #8b8b8b;
  border-width: 0;
  color: $primary-color;
  width: 150px;
  grid-column: 1/2;
  @media only screen and (min-width: 600px) {
    grid-column: 2/3;
    justify-self: right;
  }
}

#lower-header-filter {
  background-color: $secondary-color;
  color: #ffffff;
  grid-column: 2/3;
  width: 70px;
  margin-left: 10px;
  @media only screen and (min-width: 600px) {
    grid-column: 3/4;
    justify-self: left;
    width: 100px;
  }
}

option {
  line-height: 1rem;
}

select {
  font-family: "Mulish", sans-serif;
  font-weight: 600;
  letter-spacing: 1px;
}
</style>
