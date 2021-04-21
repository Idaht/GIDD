<template>
  <div id="activity-information">
    <div @click="returnToActivityFeed" id="nav-back">
      <i class="fa fa-arrow-left" aria-hidden="true"></i>
      Tilbake til feed
    </div>
    <!-- TODO: implementer kart -->
    <div id="map">
      <img id="map-img" src="../../img/map.png" alt="Map" />
    </div>
    <h2 id="activity-title">{{ activity.title }}</h2>
    <div id="host">Arrangeres av {{ activity.organizer }}</div>
    <div id="information-wrapper">
      <label class="event-variable">Når</label>
      <div class="variable-value">{{ activity.startTime }}</div>
      <label class="event-variable">Varighet</label>
      <div class="variable-value">{{ activity.durationMinutes }} minutter</div>
      <label class="event-variable">Hvor</label>
      <div class="variable-value">{{ activity.place }}, {{ activity.city }}</div>
      <label class="event-variable">Hva</label>
      <div class="variable-value">{{ activity.type }}</div>
      <label class="event-variable">Belastning</label>
      <div class="variable-value">{{ activity.difficulty }}</div>
      <label class="event-variable">Deltakere</label>
      <div class="variable-value">{{ numberOfParticipants }} / {{ activity.maxParticipants }}</div>
    </div>
    <div id="signing-up-wrapper">
      <div id="signing-up" v-if="signedUp">
        <div id="signing-up-conformation">
        Du er påmeldt!
        </div>
        <button
          @click="signOffActivity"
          alt="Knapp for å melde seg av en aktivitet"
        >
          Meld deg av
        </button>
        <button
          @click="openChat"
          alt="Knapp for å chatte med andre på samme aktivitet"
        >
          Chat
        </button>
      </div>
      <div v-else>
        <button
          @click="signUpActivity"
          alt="Knapp for å melde seg på en aktivitet"
        >
          Meld deg på
        </button>
      </div>
    </div>
    <div class="details-wrapper">
      <h3>Beskrivelse</h3>
      <p>{{ activity.description }}</p>
    </div>
    <div class="details-wrapper">
      <h3>Utstyr</h3>
      <p>{{ activity.equipment }}</p>
    </div>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent, onBeforeMount, onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import axios from "../axiosConfig";

export default defineComponent({
  name: "ActivityInformation",
  props: ["id"],
  setup(props) {
    //TODO finn arrangøren vha id
    const activityOrganizer = ref("");
    //TODO hent ut antall påmeldte
    const numberOfParticipants = ref(5);
    //TODO hent ut fra backend
    const signedUp = ref(false);
    const router = useRouter();
    const activity = ref({});

    const isSignedUp = computed(() => {
      return signedUp.value;
    });

    const signUpActivity = (): void => {
      //"Er du sikker?" vindu før påmelding?
      signedUp.value = true;
      numberOfParticipants.value += 1;
      //TODO connect to backend og få opp bekreftelse
    };

    const signOffActivity = (): void => {
      signedUp.value = false;
      numberOfParticipants.value -= 1;
      //TODO connect to backend og få opp bekreftelse
    };

    const returnToActivityFeed = (): void => {
      router.push("/activity-feed");
    };

    const openChat = (): void => {
      router.push("/chatPage");
      //TODO check chat router
    };

    /**
     * Connects to backend using a get request to get the activity
     */
    onBeforeMount(async () => {
      try {
        const response = await axios.get(`/activities/${props.id}`);
        activity.value = response.data;
      } catch {
        router.push("/error");
      }
    });

    //TODO del opp startTime og display det fint
    //const activityDate = ref(activity.startTime.toString());
    //const activityTime = ref(activity.startTime)

    return {
      //activityDate,
      //activityTime
      activityOrganizer,
      numberOfParticipants,
      signedUp,
      isSignedUp,
      signUpActivity,
      signOffActivity,
      returnToActivityFeed,
      openChat,
      activity,
    };
  },
});
</script>

<style scoped lang="scss">
$primary-color: #282828;
$secondary-color: #ea4b4b;

#activity-information {
  margin: 10px 35px 10px 35px;
  text-align: left;
  @media only screen and (min-width: 600px) {
    width: 45%;
    margin: auto;
  }
}

#nav-back {
  margin-bottom: 20px;
  font-size: 1rem;
}

#nav-back:hover,
#nav-back:active {
  color: $secondary-color;
}

#map-img {
  border-radius: 20px;
  width: 100%;
  @media only screen and (min-width: 600px) {
    width: 100%;
    height: auto;
  }
}

#activity-title {
  margin: 20px 0px 20px 0px;
  text-align: center;
}

#host {
  font-weight: 600;
  margin: 10px 0px 20px 0px;
}

#information-wrapper {
  display: grid;
  grid-template-columns: 1fr 3fr;
  grid-auto-rows: 2rem;
  width: 100%;
  text-align: left;
  align-items: center;
}

.event-variable {
  grid-column: 1/2;
  font-weight: 700;
}

.variable-value {
  grid-column: 2/3;
}

#signing-up-wrapper {
  margin: 20px 0px 20px 0px;
  justify-content: center;
  text-align: center;
}

#signing-up-conformation {
  margin-bottom: 10px;
  font-weight: 700;
}

button {
  text-transform: uppercase;
  color: #ffffff;
  font-weight: 600;
  margin: 0px 5px 0px 5px;
}

.details-wrapper {
  margin: 20px 0px 20px 0px;
}
</style>