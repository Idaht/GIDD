<template>
    <div>
        <div>
            <div class="header" id="upper-header">
                <h3>Finn aktiviteter</h3>
                <h3 @click="feedViewClicked">Feed/Kart</h3> <!--Knapp som bytter mellom kart og feed -->
            </div>
            <div class="header" id="lower-header">
                <h3 @click="sortClicked">Sortering</h3>
                <h3 @click="filterClicked">Filter</h3>
            </div>
        </div>
        <div id="activities">
            <Map id="map"
                :center="{ lat: 1.2, lng: 1.2 }"
                :activityData="activities">
                <!--:center="{ lat: 63.43049, lng: 10.39506}"-->
            </Map>
        </div>
    </div>
</template>

<script lang="ts">

import { defineComponent, onBeforeMount, ref, Ref } from "vue";
import { useRouter } from "vue-router";
import axios from "@/axiosConfig";
import IActivity from "@/interfaces/IActivity.interface";
import Map from "@/components/Map.vue";

export default defineComponent({
  name: "MapView",
  components: {
      Map,
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

    const sortClicked = () : void => {
        //TODO: Open the sorting functionality, and remove console.log
        console.log("Sort clicked");
    };

    const filterClicked = () : void => {
        //TODO: Open the filtering functionality, and remove console.log
        console.log("Filter clicked");
    };

    const feedViewClicked = () : void => {
        router.push("/activity-feed");
    };

    return {
        sortClicked,
        filterClicked,
        feedViewClicked,
        activities,
    };
  },
});
</script>

<style scoped lang="scss">

#map
{
    height: 350px;
}

.header
{
    display: flex;
    flex-direction: row;
    justify-content: center;
}

#lower-header
{
    justify-content: start;
}

#lower-header > h3
{
    cursor: pointer;
}
</style>
