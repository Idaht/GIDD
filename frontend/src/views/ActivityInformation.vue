<template>
  <div id="activity-information">
    <div @click="back" id="nav-back">
      <i class="fa fa-arrow-left" aria-hidden="true"></i>
      Tilbake
    </div>
    <div id="map">
      <!--TODO: Fiks så API key hentes fra fil-->
      <img
        id="map-img"
        :src="
          'https://maps.googleapis.com/maps/api/staticmap?center=' +
          activity.latitude +
          ',' +
          activity.longitude +
          '&zoom=14&size=600x350&markers=color:blue%7Clabel:A%7C' +
          activity.latitude +
          ',' +
          activity.longitude +
          '&key=' +
          apiKey
        "
        alt="Map"
      />
    </div>
    <h2 id="activity-title">{{ activity.title }}</h2>
    <div id="weather">
      <Weather
        :latitude="activity.longitude"
        :longitute="activity.latitude"
        :time="activity.startTime"
      />
    </div>
    <div id="host">Arrangeres av {{ activity.organizer }}</div>
    <div id="information-wrapper">
      <label class="event-variable">Når</label>
      <div class="variable-value">{{ dateTimeFormatter }}</div>
      <label class="event-variable">Varighet</label>
      <div class="variable-value">{{ durationFormatter }}</div>
      <label class="event-variable">Hvor</label>
      <div class="variable-value">
        {{ activity.place }}, {{ activity.city }}
      </div>
      <label class="event-variable">Hva</label>
      <div class="variable-value">{{ activity.type }}</div>
      <label class="event-variable">Belastning</label>
      <div class="variable-value">{{ difficultyString }}</div>
      <label class="event-variable">Deltakere</label>
      <div class="variable-value">
        {{ numberOfParticipants }} / {{ activity.maxParticipants }}
      </div>
    </div>

    <div id="signing-up-wrapper">
      <div id="signing-up" v-if="signedUp">
        <div id="signing-up-conformation">Du er påmeldt!</div>
        <button
          @click="signOffActivity"
          alt="Knapp for å melde seg av en aktivitet"
          v-if="!isOrganizer()"
        >
          Meld deg av
        </button>
        <button @click="edit" v-else>Rediger</button>
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
import {
  computed,
  defineComponent,
  onBeforeMount,
  Ref,
  ref,
  watchEffect,
} from "vue";
import { useRouter } from "vue-router";
import axios from "../axiosConfig";
import data from "@/../config.json";
import { store } from "../store";
import IActivity from "@/interfaces/IActivity.interface"
import Weather from "../components/Weather.vue";
import User from "@/interfaces/User/User.interface";

export default defineComponent({
  name: "ActivityInformation",
  components: {
    Weather,
  },
  props: ["id"],
  setup(props) {
    /**
     * TODO:
     *  1. Hente navnet til arrangør og trusted og displaye det
     *  2. Rute chat page - venter på merge request
     *  3. Rute til edit activty - venter på MR
     *  6. Fikse redigeringsknappen sånn at det ruter deg til redigering - MR
     *  7. Lage fraværsregistrering - Nora :'(
     */

    //General methods
    const activityOrganizer = ref(""); //TODO: navnet til arrangør må hentes
    const router = useRouter();
    const apiKey = data.googleAPIKey;
    const activity: Ref<IActivity> = ref({
      activityId: -1,
      organizerId: -1,
      title: "",
      description: "",
      equipment: "",
      difficulty: -1,
      city: "",
      place: "",
      longitude: 0,
      latitude: 0,
      startTime: "",
      durationMinutes: 0,
      maxParticipants: 0,
      type: "",
      privateActivity: false,
      activityPicture: "",
      chatId: -1,
      organizerForename: "",
      organizerSurname: ""
    } as IActivity);
    const participants = ref([]); //list with all participants

    /**
     * Method for getting number of participants on an activity
     */
    const numberOfParticipants = computed((): number => {
      if (signedUp.value) return participants.value.length + 1;
      return participants.value.length;
    });

    /**
     * Button back
     */
    const back = (): void => {
      router.back();
    };

    //User methods
    const userId = ref(); //the logged in users ID
    const signedUp = ref(); //boolean, tells if the logged in user is signed op to that activity

    /**
     * Method for signing off an activity. Gives an alert window
     * when you sign off
     */
    const signOffActivity = async (): Promise<void> => {
      signedUp.value = false;
      window.alert("Du er nå meldt av");
      try {
        await axios.delete(
          `users/${store.getters.user.userId}/activities/${props.id}`
        );
      } catch (error) {
        router.push("/error");
      }
    };

    const getChatId = computed(() => {
      return activity.value.chatId;
    })

    /**
     * Opens chat
     */
    const openChat = (): void => {
      router.push(`/activity/${getChatId.value}/chat`);
    }
      //TODO check chat router
    //Organizer methods
    const organizerId = ref(); //id of the organizer

    /**
     * Checks if the loggen on user is the organizer of an activity
     */
    const isOrganizer = ref((): boolean => {
      if (organizerId.value) {
        signedUp.value = true;
      }
      return organizerId.value;
    });

    /**
     * Button to edit activity
     */
    const edit = ref(() => {
      router.push("/#"); ///TODO: endre denne til edit activity siden
    });

    /**
     * Method for signing up to an activity
     */
    const signUpActivity = async (): Promise<void> => {
      signedUp.value = true;
      try {
        await axios.post(
          `/activities/${props.id}/users/${store.getters.user.userId}`
        );
      } catch (error) {
        router.push("/error");
      }
    };

    //connection
    /**
     * Connects to backend using a get request to get the activity
     */
    onBeforeMount(async () => {
      try {
        //gets the info from backend
        const response = await axios.get(`/activities/${props.id}`);
        activity.value = response.data;
        const participantResponse = axios.get(`/activities/${props.id}/users`);
        const organizerResponse = axios.get(
          `/activities/${props.id}/organizer/${store.getters.user.userId}`
        );
        const userResponse = axios.get(`users/${store.getters.user.userId}`);
        const signedUpResponse = axios.get(
          `/activities/${props.id}/users/${store.getters.user.userId}`
        );

        //collects all the data and collects in array

        const res = await Promise.all([
          response,
          participantResponse,
          organizerResponse,
          userResponse,
          signedUpResponse,
        ]);

        //assignes the data to the right value
        activity.value = res[0].data;
        participants.value = res[1].data;
        organizerId.value = res[2].data;
        userId.value = res[3].data;
        signedUp.value = res[4].data;
      } catch (error) {
        router.push("/error");
      }
    });

    /**
     * Formats the duration to show hours and minutes
     */
    const durationFormatter = computed(() => {
      if (activity.value.durationMinutes > 60) {
        const timeHour = ref(activity.value.durationMinutes / 60);
        const extraMin = ref(activity.value.durationMinutes % 60);
        if (activity.value.durationMinutes % 60 === 0) {
          return timeHour.value + " timer";
        }
        return timeHour.value + " timer og " + extraMin.value + " minutter";
      }
      return activity.value.durationMinutes + " minutter";
    });

    /**
     * Formats the date and time
     */
    const dateTimeFormatter = computed(() => {
      const temp = activity.value.startTime.split(" ");
      const dateArray = temp[0].split("-");
      const date = ref(dateArray[2] + "/" + dateArray[1] + "/" + dateArray[0]);
      const time = ref(temp[1]);
      return date.value + " kl. " + time.value;
    });

    /**
     * Gets the activity difficulty
     */
    const difficultyString = computed(() => {
      let string = "";
      switch (activity.value.difficulty) {
        case 1:
          string = "Lett";
          break;
        case 2:
          string = "Middels";
          break;
        case 3:
          string = "Lett, Middels";
          break;
        case 4:
          string = "Krevende";
          break;
        case 5:
          string = "Lett, Krevende";
          break;
        case 6:
          string = "Middels, Krevende";
          break;
        case 7:
          string = "Lett, Middels, Krevende";
          break;
        default:
          string = "Fant ikke belastning";
      }
      return string;
    });

    return {
      activityOrganizer,
      numberOfParticipants,
      signedUp,
      signUpActivity,
      signOffActivity,
      back,
      openChat,
      activity,
      apiKey,
      isOrganizer,
      edit,
      dateTimeFormatter,
      durationFormatter,
      difficultyString,

      //dette skla fjernes
      organizerId,
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
