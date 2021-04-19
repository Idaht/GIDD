<template>
  <!-- Putt inn et bilde av en tilbakeknapp? -->
  <img
    @click="returnToActivityFeed"
    src="https://static.thenounproject.com/png/1195139-200.png"
    alt="Tilbakeknapp til hovedsiden"
  />
  <div>Her skal kart</div>
  <h3 id="activityTitle">{{ activityTitle }}</h3>
  <label>Når</label>
  <div>{{ activityDate }}</div>
  <label>Hvor</label>
  <div>{{ activityPlace }}</div>
  <label>Hva</label>
  <div>{{ activityType }}</div>
  <label>Arrangør</label>
  <div>{{ activityOrganizer }}</div>
  <label>Belastning</label>
  <div>{{ activityLoad }}</div>
  <label>Deltakere</label>
  <div>{{ numberOfParticipants }} / {{ maxParticipants }}</div>

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
  <p>{{ activityDescription }}</p>

  <div>Utstyr</div>
  <p>{{ activityEquipment }}</p>
</template>

<script lang="ts">
import { computed, defineComponent, onMounted, ref } from "vue";
import { useRouter } from "vue-router";

export default defineComponent({
  name: "ActivityInformation",
  setup() {
    const activityTitle = ref("Ballerinaopptreden");
    const activityDate = ref("20/04/2021");
    const activityPlace = ref("Dødens dal");
    const activityOrganizer = ref("Ola Nordmann");
    const activityLoad = ref("Supermann");
    const numberOfParticipants = ref(5);
    const maxParticipants = ref(10);
    const signedUp = ref(false);
    const activityDescription = ref(
      "Litt ballerina, litt fotball og en hel del golf"
    );
    const activityEquipment = ref("Golfball");
    const activityType = ref("Fotball");
    const router = useRouter();

    const isSignedUp = computed(() => {
      return signedUp.value;
    });

    const signUpActivity = (): void => {
      //"Er du sikker?" vindu før påmelding?
      signedUp.value = true;
      numberOfParticipants.value += 1;
      //TODO connect to backend
      backendCall;
      //TODO Få opp bekreftelse
    };

    const signOffActivity = (): void => {
      signedUp.value = false;
      numberOfParticipants.value -= 1;
      //TODO connect to backend
    };

    const returnToActivityFeed = (): void => {
      router.push("/activity-feed");
    };

    const openChat = (): void => {
      router.push("/chatPage");
      //TODO check chat router
    };

    /**
     * Method to connect to backend
     */
    const backendCall = onMounted(() => {
      //TODO Connect to backend
    });

    return {
      activityTitle,
      activityDate,
      activityPlace,
      activityOrganizer,
      activityLoad,
      activityType,
      numberOfParticipants,
      maxParticipants,
      signedUp,
      isSignedUp,
      signUpActivity,
      signOffActivity,
      activityDescription,
      activityEquipment,
      returnToActivityFeed,
      openChat,
    };
  },
});
</script>
