<template>
  <div id="activity-feed">
    <div>
      <div class="header" id="upper-header">
        <div id="header-title">
          <h2>Finn aktiviteter</h2>
        </div>
        <div id="view-container">
          <button id="view-list" class="icon"></button>
          <button @click="mapViewClicked" id="view-map" class="icon"></button>
        </div>
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
        <ActivityFeedItem v-for="activity in activities" :key="activity.activityId" :activityData="activity"/>
    </div>
  </div>
  <!-- Et plusstegn på knappen -->
  <button @click="makeActivity">Opprett aktivitet</button>
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
    const activities: Ref<IActivity[]> = ref([]);

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

    const mapViewClicked = (): void => {
      router.push("/activity-map");
    };

    const makeActivity = (): void => {
      router.push("/make-activity");
    };

    return {
      sortClicked,
      filterClicked,
      mapViewClicked,
      activities,
      makeActivity,
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
  color: $primary-color;
  margin: 35px;
  @media only screen and (min-width: 600px) {
    width: 45%;
    margin: auto;
    grid-template-columns: 1fr 1fr;
  }
}

.header {
  margin-bottom: 15px;
  text-align: left;
}

#upper-header {
  display: grid;
  grid-template-columns: 2fr 1fr;
}

#lower-header {
  display: grid;
  grid-template-columns: 1fr 3fr;
}

h2 {
  font-weight: 600;
  text-align: left;
  margin: 0;
}

#view-container {
  text-align: right;
  align-content: center;
  display: block;
}

#view-list {
  background-image: url("data:image/svg+xml;charset=utf-8,%3Csvg xmlns='http://www.w3.org/2000/svg' fill='%23C2C2C2' viewBox='0 0 24 24'%3E%3Cpath fill-rule='evenodd' d='M9 5h11a1 1 0 010 2H9a1 1 0 110-2zm0 6h11a1 1 0 010 2H9a1 1 0 010-2zm0 6h11a1 1 0 010 2H9a1 1 0 010-2zm-4.5-6.5a1.5 1.5 0 110 3 1.5 1.5 0 010-3zm0 6a1.5 1.5 0 110 3 1.5 1.5 0 010-3zm0-12a1.5 1.5 0 110 3 1.5 1.5 0 010-3z'/%3E%3C/svg%3E");
}

#view-map {
  background-image: url("data:image/svg+xml;charset=utf-8,%3Csvg xmlns='http://www.w3.org/2000/svg' fill='%23333333' viewBox='0 0 24 24'%3E%3Cpath fill-rule='evenodd' d='M12 21c-1.5 0-7-6-7-11a7 7 0 1114 0c0 5-5.5 11-7 11zm0-8.5a2.5 2.5 0 100-5 2.5 2.5 0 000 5z'/%3E%3C/svg%3E");
}

.icon {
  margin-top: 7px;
  margin-left: 5px;
  align-items: flex-end;
  width: 22px;
  height: 22px;
  padding: 0px;
  background-color: unset;
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
}

#lower-header-filter {
  width: 50px;
}

option {
  line-height: 1rem;
}

select {
  background: url(data:image/svg+xml;base64,PHN2ZyBpZD0iTGF5ZXJfMSIgZGF0YS1uYW1lPSJMYXllciAxIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCA0Ljk1IDEwIj48ZGVmcz48c3R5bGU+LmNscy0xe2ZpbGw6I2ZmZjt9LmNscy0ye2ZpbGw6IzQ0NDt9PC9zdHlsZT48L2RlZnM+PHRpdGxlPmFycm93czwvdGl0bGU+PHJlY3QgY2xhc3M9ImNscy0xIiB3aWR0aD0iNC45NSIgaGVpZ2h0PSIxMCIvPjxwb2x5Z29uIGNsYXNzPSJjbHMtMiIgcG9pbnRzPSIxLjQxIDQuNjcgMi40OCAzLjE4IDMuNTQgNC42NyAxLjQxIDQuNjciLz48cG9seWdvbiBjbGFzcz0iY2xzLTIiIHBvaW50cz0iMy41NCA1LjMzIDIuNDggNi44MiAxLjQxIDUuMzMgMy41NCA1LjMzIi8+PC9zdmc+)
    no-repeat 93% 50%;
  font-family: "Mulish", sans-serif;
  font-weight: 600;
  letter-spacing: 1px;
  -moz-appearance: none;
  -webkit-appearance: none;
  -webkit-border-radius: 0px;
  appearance: none;
  outline-width: 0;
}

#activities {
  margin-top: 35px;
  display: flex;
  flex-flow: wrap;
  position: relative;
}

#activity {
  padding: 10px 0px 10px 0px;
  @media only screen and (min-width: 800px) {
    padding: 10px;
  }
}

li {
  list-style-type: none;
  padding-bottom: 20px;
  @media only screen and (min-width: 800px) {
    width: 50%;
  }
}
</style>
