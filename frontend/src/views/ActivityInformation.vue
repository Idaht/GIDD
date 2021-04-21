<template>
  <!-- Putt inn et bilde av en tilbakeknapp? -->
  <div>
  <img
    @click="returnToActivityFeed"
    src="https://static.thenounproject.com/png/1195139-200.png"
    alt="Tilbakeknapp til hovedsiden"
  />
  <div>Her skal kart</div>
  <h3 id="activityTitle">{{ activity.title }}</h3>
  <label>Når</label>
  <div>{{ activity.startTime }}</div>
  <label>Varighet</label>
  <div>{{ activity.durationMinutes }} minutter</div>
  <label>Hvor</label>
  <div>{{ activity.place }}, {{ activity.city }}</div>
  <label>Hva</label>
  <div>{{ activity.type }}</div>
  <label>Arrangør</label>
  <div>{{ activity.organizer }}</div>
  <label>Belastning</label>
  <div>{{ activity.difficulty }}</div>
  <label>Deltakere</label>
  <div>{{ numberOfParticipants }} / {{ activity.maxParticipants }}</div>

  <span id="signing-up" v-if="signedUp">
    Du er påmeldt!
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
  </span>
  <span v-else>
    <button @click="signUpActivity" alt="Knapp for å melde seg på en aktivitet">
      Meld deg på
    </button>
  </span>
  <div>Beskrivelse</div>
  <p>{{ activity.description }}</p>

  <div>Utstyr</div>
  <p>{{ activity.equipment }}</p>
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
