<template>
    <div id="activity-feed">
        <div id="header-title">
            <h2>Kommende aktiviteter</h2>
        </div>
        <div class="topnav">
            <button @click="toParicipant" :disabled="disableButtons">Deltaker</button>
            <button @click="toOrganizer" :disabled="!disableButtons">Arrangør</button>
        </div>
        <div id="participant" v-if="stage === 1">
            <ActivityFeedItem v-for="activity in activitiesParticipant" :key="activity.activityId" :activityData="activity" />
        </div>
        <div id="organizer" v-if="stage === 2">
            <h2>Her er dine aktiviteter</h2>
            <li v-for="activity in activitiesOrganizer" :key="activity.activityId">
            <ActivityFeedItem :activityData="activity" />
            </li>
        </div>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent, onBeforeMount, ref, Ref, watchEffect } from "vue";
import ActivityFeedItem from "../components/ActivityFeedItem.vue";
import axios from "@/axiosConfig";
import { useRouter } from "vue-router";
import IActivity from "@/interfaces/IActivity.interface";
import { store } from "../store";

export default defineComponent({
  name: "ActivityFeed",
  components: {
    ActivityFeedItem,
  },
  setup() {
    const router = useRouter();
    const stage = ref(1);
    const activitiesParticipant: Ref<IActivity[]> = ref([]);
    const activitiesOrganizer: Ref<IActivity[]> = ref([]);
    const userId:number = store.getters.user.userId;

    onBeforeMount(async () => {
      try {
        const participantResponse = await axios.get(`/users/${userId}/my-activities`);
        activitiesParticipant.value = participantResponse.data as IActivity[];
        console.log(activitiesParticipant.value);
        //TODO: Fikse slik at det er arrangør og ikke påmeldt her
        const organizerResponse = await axios.get(`/users/${userId}/organized-activities`);
        activitiesOrganizer.value = organizerResponse.data as IActivity[];
        console.log(userId);
      } catch (error) {
        router.push("/error");
      }
    });

    const toParicipant = ():void => {
        stage.value = 1; 
    };

    const toOrganizer = ():void => {
        stage.value = 2;
    };

    const disableButtons = computed(() =>{
        return stage.value === 1; 
    })

    return {
      toParicipant,
      toOrganizer,
      stage,
      disableButtons,
      activitiesParticipant,
      activitiesOrganizer,
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