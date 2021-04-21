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
                :center="{ lat: 63.43049, lng: 10.39506}"
                :activityData="activitiesTest">
            </Map>
        </div>
    </div>
</template>

<script lang="ts">

import { defineComponent, onBeforeMount, ref } from "vue";
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
    let response;
    let activities;

    onBeforeMount(async () => {
        //response = await axios.get("/activities");
        //activities = ref(response.data);
        //TODO: Get activities from backend server, and remove test data
        console.log("Må ha noe her midelertidig");
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
                longitude: 10.40645599,
                latitude: 63.41914883,
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
                longitude: 10.40744305,
                latitude: 63.41797743,
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
                longitude: 10.38032055,
                latitude: 63.42640661,
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
                longitude: 10.40400982,
                latitude: 63.41056384,
                startTime: "19:00",
                durationMinutes: 60,
                isPrivateActivity: false,
            }
        ]
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
