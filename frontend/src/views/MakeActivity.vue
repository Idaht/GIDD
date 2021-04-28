<template>
  <h2>Opprett en ny aktivitet</h2>
  <div>Profilbilde</div>
  <div>{{ user.forename }} {{ user.surname }}</div>
  <div>Arrangør</div>
  <input v-model="activity.title" type="title" placeholder="Tittel" />
  <p v-if="!validTitle">Oppgi en tittel til aktiviteten</p>
  <h5>Startdato og -tidspunkt</h5>
  <select v-model="selectedYear" name="year">
    <option hidden disabled value>Velg år</option>
    <option v-for="(year, index) in availableYears" :value="year" :key="index">
      {{ year }}
    </option>
  </select>
  <select v-model="selectedMonth" name="month">
    <option hidden disabled value>Velg måned</option>
    <option v-for="(month, index) in months" :value="month.name" :key="index">
      {{ month.name }}
    </option>
  </select>
  <select v-model="selectedDay" name="day">
    <option hidden disabled value>Velg dato</option>
    <option v-for="index in daysInCurrentMonth" :value="index" :key="index">
      {{ index }}
    </option>
  </select>

  <select v-model="selectedHour" name="hour">
    <option hidden disabled value>Velg time</option>
    <option v-for="index in hoursList" :value="index" :key="index">
      {{ index }}
    </option>
  </select>
  <select v-model="selectedMinute" name="minutes">
    <option hidden disabled value>Velg minutt</option>
    <option v-for="index in minutes" :value="index" :key="index">
      {{ index }}
    </option>
  </select>
  <p v-if="!validDateTime">Oppgi riktig starttid</p>
  <input
    v-model="durationHour"
    type="durationHour"
    placeholder="Varighet timer"
  />
  <p v-if="!validDuration">Oppgi varighet på aktiviteten</p>
  <input v-model="activity.type" type="type" placeholder="Type aktivitet" />
  <p v-if="!validType">Oppgi aktivitetstype</p>
  <input
    v-model="participants"
    type="maxParticipants"
    placeholder="Maks antall deltakere"
  />
  <p v-if="!validMaxParticipants">Oppgi maks antall deltakere</p>
  <h4>Belastningsnivå</h4>
  <h5>Hva slags belastningsnivå er aktiviteten?</h5>
  <input v-model="isEasy" type="checkbox" id="easy" name="easy" />
  <label for="easy">Lett</label><br />
  <input v-model="isMedium" type="checkbox" id="medium" name="medium" />
  <label for="medium">Medium</label><br />
  <input v-model="isHard" type="checkbox" id="hard" name="hard" />
  <label for="hard">Høy</label><br />
  <p v-if="!validDifficulty">Oppgi en vanskelighetsgrad</p>
  <h3>Sted</h3>
  <div id="map-view">
    <!--TODO: ':center' fjernes når appen finner brukers lokasjon selv-->
    <Map id="map" :center="{ lat: 63.43049, lng: 10.39506 }" :getLocation="true" :activityData="[]"></Map>
  </div>
  <!--Midlertidig løsning for å se alle fletene, fjernes ved styling-->
  <br>
  <br>
  <br>
  <br>
  <br><br><br><br><br><br><br><br><br><br><br><br>
  <br>
  <br>
  <br>
  <p>Legg til et fysisk sted der arrangementet skal ta plass</p>
  <input v-model="activity.place" type="place" placeholder="Sted" />
  <input v-model="activity.city" type="city" placeholder="By" />
  <p v-if="!validPlaceCity">Oppgi et sted</p>
  <h3>Beskrivelse</h3>
  <p>Legg til en kort beskrivelse av aktiviteten (frivillig)</p>
  <input
    v-model="activity.description"
    type="description"
    placeholder="Beskrivelse"
  />
  <h3>Utstyr</h3>
  <p>Legg til utstyr som trengs for å gjennomføre aktiviteten (frivillig)</p>
  <input v-model="activity.equipment" type="equipment" placeholder="Utstyr" />
  <h3>Bilde</h3>
  <p>Her kan du laste opp et bilde som vises på aktivitetsfeeden (frivillig)</p>
  <p>Last opp</p>
  <p v-if="feedbackError">Noe gikk galt, prøv igjen!</p>
  <p v-if="feedbackMissingInfo">
    Sjekk at du har fylt inn all nødvendig informasjon
  </p>
  <p v-if="feedbackSomethingWentWrong">
    Noe gikk galt, prøv igjen
  </p>
  <button @click="makeActivity">Opprett aktivitet</button>
</template>

<script lang="ts">
import axios from "../axiosConfig";
import {
  computed,
  defineComponent,
  onBeforeMount,
  reactive,
  Ref,
  ref,
  provide,
  watch,
} from "vue";
import { useRouter } from "vue-router";
import MakeActivity from "@/interfaces/Activity/MakeActivity.interface";
import { useStore } from "@/store";
import Month from "../interfaces/Month.interface";
import Map from "@/components/Map.vue";
import ICoordinates from "@/interfaces/ICoordinates.interface";
import ILocation from "@/interfaces/ILocation.interface";
import { TrainingLevel } from "@/enums/TrainingLevel.enum";
import axiosNotConfig from "axios";
import data from "@/../config.json";

export default defineComponent({
  components: {
    Map,
  },

  setup() {
    const apiKey = data.googleAPIKey;
    const durationHour = ref("");
    //TODO må fikse kart
    const isEasy = ref(false);
    const isMedium = ref(false);
    const isHard = ref(false);
    const selectedYear = ref("");
    const selectedMonth = ref("");
    const selectedDay = ref("");
    const currentYear = new Date().getFullYear();
    const limitForUpperYear = 10;
    const selectedHour = ref("");
    const selectedMinute = ref("");
    const user = ref({});
    const router = useRouter();
    const store = useStore();
    const feedbackError = ref(false);
    enum difficultyValue {
      LOW = TrainingLevel.LOW,
      MEDIUM = TrainingLevel.MEDIUM,
      HIGH = TrainingLevel.HIGH,
    }
    const participants = ref("");
    const feedbackMissingInfo = ref(false);
    const feedbackSomethingWentWrong = ref(false)
    const coordinates = reactive({ lat: 0.0, lng: 0.0 } as ICoordinates);
    provide('coordinates', coordinates);

    //Activity object
    const activity = reactive({
      title: "",
      type: "",
      description: "",
      equipment: "",
      difficulty: -1,
      city: "",
      place: "",
      longitude: 0,
      latitude: 0,
      startTime: "",
      durationMinutes: -1,
      privateActivity: false,
      maxParticipants: -1,
      //activityPicture: "", //TODO send med bilde
    } as MakeActivity);

    const daysInFebruary = computed(() => {
      return isLeapYear.value ? 29 : 28;
    });

    const months: Ref<Month[]> = ref([
      { name: "Januar", numberOfDays: 31 },
      { name: "Februar", numberOfDays: daysInFebruary },
      { name: "Mars", numberOfDays: 31 },
      { name: "April", numberOfDays: 30 },
      { name: "Mai", numberOfDays: 31 },
      { name: "Juni", numberOfDays: 30 },
      { name: "Juli", numberOfDays: 31 },
      { name: "August", numberOfDays: 31 },
      { name: "September", numberOfDays: 30 },
      { name: "Oktober", numberOfDays: 31 },
      { name: "November", numberOfDays: 30 },
      { name: "Desember", numberOfDays: 31 },
    ]);

    //List of hours to use in dropdown menu - to get "0" in front of single digits
    const hoursList = ref([
      "00",
      "01",
      "02",
      "03",
      "04",
      "05",
      "06",
      "07",
      "08",
      "09",
      "10",
      "11",
      "12",
      "13",
      "14",
      "15",
      "16",
      "17",
      "18",
      "19",
      "20",
      "21",
      "22",
      "23",
    ]);

    //Same as hours, but with minutes
    const minutes = [
      "00",
      "01",
      "02",
      "03",
      "04",
      "05",
      "06",
      "07",
      "08",
      "09",
    ];

    const updateCityPlace = async () => {
      try {
        const response = await axiosNotConfig.get(`https://maps.googleapis.com/maps/api/geocode/json?latlng=${coordinates.lat + "," + coordinates.lng}&key=${apiKey}`).then();
        const responseData = response.data;
        if (response.status == 200) {
          let address: string[] = (responseData.results[0].formatted_address as string).split(",");
          let place = address[0];
          let city = address[1].split(" ");
          place != "Unnamed Road" ? activity.place = place : activity.place = ""; //Setting the place value
          city = city.filter(element =>  { //Filters away city names that is not valid
            if (element == "") {
              return false;
            }
            if (!isNaN(Number(element))) {
              return false;
            }
            return true;
          });

          city[0] || city[0] != "Unnamed" ? activity.city = city[0] : activity.city = ""; //Setting the city value
        }
      } catch (error) {
      //Something went wrong, user has to write place and city
      }
    };

    watch(() => coordinates.lat || coordinates.lng, (newValue, oldValue) => {
      if (newValue != oldValue) {
        updateCityPlace();
      }
    });

    //Before page loads, make the rest of the minutes list
    onBeforeMount(() => {
      let n = "0";
      for (let i = 10; i < 60; i++) {
        n = String(i);
        // eslint-disable-next-line vue/no-side-effects-in-computed-properties
        minutes.push(n);
      }
      return minutes;
    });

    /**
     * Method to make the activity once the button has been pressed.
     * Starts by getting the correct information to the last attributes
     * in the activity object, then tries to post the activity to backend.
     * If the activity form is not filled correctly or posting fails,
     * feedback will be showed or the user will be sent to the error page.
     * If successful, the user will be sent to the activity page of the
     * just made activity
     */
    const makeActivity = async (): Promise<void> => {
      activity.difficulty = calculateDifficulty.value;
      activity.durationMinutes = parseFloat(durationHour.value) * 60.0;
      activity.maxParticipants = parseInt(participants.value);
      activity.startTime = makeDateTime.value;
      if (coordinates.lat != 0.0 || coordinates.lng != 0.0)
      {
        activity.latitude = coordinates.lat;
        activity.longitude = coordinates.lng;
      }
      console.log(activity);
      try {
        if (!isActivityInvalid.value) {
          const response = await axios.post("/activities", activity);
          await axios.get(`/activities/` + response.data.activityId);
          router.push(`/activity/` + response.data.activityId);
        } else {
          feedbackMissingInfo.value = true;
        }
      } catch {
        if (isActivityInvalid.value) {
          feedbackMissingInfo.value = false;
          feedbackSomethingWentWrong.value = true;
        }
      }
    };

    /**
     * Method to turn the date and time into correct format
     */
    const makeDateTime = computed(() => {
      return (
        selectedYear.value +
        "-" +
        getMonthNumber.value +
        "-" +
        getDay.value +
        " " +
        selectedHour.value +
        ":" +
        selectedMinute.value
      );
    });

    /**
     * Method to add "0" on single days for date and time format
     */
    const getDay = computed(() => {
      if (parseInt(selectedDay.value) < 10) {
        return "0" + selectedDay.value;
      }
      return selectedDay.value.toString();
    });

    /**
     * Method to get the months corresponding number for date and time format
     */
    const getMonthNumber = computed(() => {
      if (selectedMonth.value === "Januar") {
        return "01";
      } else if (selectedMonth.value === "Februar") {
        return "02";
      } else if (selectedMonth.value === "Mars") {
        return "03";
      } else if (selectedMonth.value === "April") {
        return "04";
      } else if (selectedMonth.value === "Mai") {
        return "05";
      } else if (selectedMonth.value === "Juni") {
        return "06";
      } else if (selectedMonth.value === "Juli") {
        return "07";
      } else if (selectedMonth.value === "August") {
        return "08";
      } else if (selectedMonth.value === "September") {
        return "09";
      } else if (selectedMonth.value === "Oktober") {
        return "10";
      } else if (selectedMonth.value === "November") {
        return "11";
      } else if (selectedMonth.value === "Desember") {
        return "12";
      }
      return "Noe gikk galt";
    });

    const validPlaceCity = computed(() => {
      return activity.place !== "" && activity.city !== "";
    });

    const validDifficulty = computed(() => {
      return (
        activity.difficulty !== -1 ||
        isEasy.value ||
        isMedium.value ||
        isHard.value
      );
    });

    const validMaxParticipants = computed(() => {
      return (
        participants.value !== "" &&
        !isNaN(Number(participants.value)) &&
        Number(participants.value) &&
        !participants.value.includes("-")
      );
    });

    const validType = computed(() => {
      return activity.type !== "";
    });

    const validDuration = computed(() => {
      return (
        durationHour.value !== "" &&
        !isNaN(Number(durationHour.value)) &&
        Number(durationHour.value) > 0 &&
        !durationHour.value.includes("-")
      );
    });

    const validDateTime = computed(() => {
      return (
        selectedYear.value !== "" ||
        selectedMonth.value !== "" ||
        selectedDay.value !== "" ||
        selectedHour.value !== "" ||
        selectedMinute.value !== ""
      );
    });

    const validTitle = computed(() => {
      return activity.title !== "";
    });

    const calculateDifficulty = computed(() => {
      let difficultyNumber = 0;
      if (isEasy.value) {
        difficultyNumber += difficultyValue.LOW;
      }
      if (isMedium.value) {
        difficultyNumber += difficultyValue.MEDIUM;
      }
      if (isHard.value) {
        difficultyNumber += difficultyValue.HIGH;
      }
      return difficultyNumber;
    });

    const isActivityInvalid = computed(() => {
      return (
        activity.title === "" ||
        activity.startTime === "" ||
        activity.durationMinutes === -1 ||
        isNaN(activity.durationMinutes) ||
        activity.durationMinutes < 0 ||
        activity.type === "" ||
        activity.maxParticipants === -1 ||
        isNaN(activity.maxParticipants) ||
        activity.maxParticipants < 0 ||
        activity.difficulty === 0 ||
        activity.place === "" ||
        activity.city === ""
      );
    });

    /**
     * Gets user from backend to display name on the page
     */
    onBeforeMount(async () => {
      try {
        const response = await axios.get(`/users/${store.getters.user.userId}`);
        user.value = response.data;
      } catch {
        router.push("/error");
      }
    });

    /**
     * Method to find the available years the user can pick
     */
    const availableYears = computed(() => {
      const years = [];
      for (let i = currentYear + limitForUpperYear; i > currentYear - 1; i--) {
        years.push(i);
      }
      return years.reverse();
    });

    /**
     * Method to return a boolean depending
     * on if the year selected is a leap year
     */
    const isLeapYear = computed(() => {
      if (parseInt(selectedYear.value) === 0) return false;
      return (
        parseInt(selectedYear.value) % 400 === 0 ||
        (parseInt(selectedYear.value) % 100 !== 0 &&
          parseInt(selectedYear.value) % 4 === 0)
      );
    });

    /**
     * Find the days in the selected month
     */
    const daysInCurrentMonth = computed(() => {
      if (selectedMonth.value === "") {
        return 0;
      }
      return months.value.find(
        (month: { name: string }) => month.name === selectedMonth.value
      )?.numberOfDays;
    });

    return {
      makeActivity,
      user,
      activity,
      availableYears,
      daysInCurrentMonth,
      isLeapYear,
      selectedDay,
      selectedYear,
      selectedMonth,
      months,
      selectedHour,
      selectedMinute,
      isHard,
      isMedium,
      isEasy,
      durationHour,
      feedbackError,
      validTitle,
      validDateTime,
      validDuration,
      validType,
      validMaxParticipants,
      validDifficulty,
      validPlaceCity,
      participants,
      hoursList,
      minutes,
      feedbackMissingInfo,
      feedbackSomethingWentWrong,
    };
  },
});
</script>

<style scoped lang="scss">

$primary-color: #282828;

#map {
  padding-top: 20px;
  position: absolute;
  width: 100%;
  left: 0px;
  height: 25%;
  @media only screen and (min-width: 600px) {
    height: 350px;
  }
}

#map-view {
  color: $primary-color;
  margin: 35px;
  @media only screen and (min-width: 600px) {
    width: 45%;
    margin: auto;
    grid-template-columns: 1fr 1fr;
  }
}
</style>
