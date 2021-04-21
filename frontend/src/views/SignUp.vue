<template>
  <div id="page">
    <div id="pageOne" v-if="stage === 1">
      <h1>Opprett bruker</h1>
      <input type="epost" placeholder="e-post" v-model="user.email" />
      <input type="fornavn" placeholder="Fornavn" v-model="user.forename" />
      <input type="etternavn" placeholder="Etternavn" v-model="user.surname" />

      <h4>Fødselsdato</h4>
      <h5>Velg år</h5>
      <select v-model="selectedYear">
        <option hidden disabled value>Velg år</option>
        <option v-for="(year, index) in availableYears" :key="index">
          {{ year }}
        </option>
      </select>
      <h5>Velg måned</h5>
      <select name="month" v-model="selectedMonth">
        <option value="month" selected disabled hidden>Velg måned</option>
        <option v-for="(month, index) in months" :key="month + index">
          {{ month.name }}
        </option>
      </select>
      <h5>Velg dag</h5>
      <select name="day" v-model="selectedDay">
        <option value="day" selected disabled hidden>Velg dato</option>
        <option v-for="index in daysInCurrentMonth" :key="index">
          {{ index }}
        </option>
      </select>

      <h3>Krav:</h3>
      <ul>
        <!-- <ul><li v-if="!isEmailValid>-Epost må inneholde både @ og .</li></ul>-->
        <li v-if="!isEmailValid">Epost må inneholde både @ og .</li>
        <li v-if="!isNameValid">Navn må inneholde både fornavn og etternavn</li>
        <li v-if="!isBirthDateValid">
          Fødselsdato må inneholde år, måned og dato
        </li>
      </ul>
    </div>

    <div id="pageTwo" v-else-if="stage === 2">
      <h2>Skriv inn passord</h2>
      <input type="password" v-model="user.password" placeholder="Passord" />
      <input
        type="password"
        v-model="repeatPassword"
        placeholder="Gjenta passord"
      />
      <span> {{ passwordFeedback }} </span>
    </div>

    <div id="pageThree" v-else-if="stage === 3">
      <div class="container">
        <form @submit.prevent="handleSubmit">
          <div class="form-group">
            <input type="file" @change="uploadFile" multiple />
          </div>
          <div class="form-group">
            <button class="btn btn-success btn-block btn-lg">Last opp</button>
          </div>
        </form>
      </div>
    </div>

    <div id="pageFour" v-else-if="stage === 4">
      <h1>Godta betingelser</h1>
      <div class="terms-box">
        {{ termsAndConditions }}
      </div>
    </div>

    <div id="wrong" v-else>
      <h1>Something went wrong</h1>
    </div>

    <button @click="prevPage" class="router-link-button">
      {{ buttonBackNames }}
    </button>
    <button
      @click="nextPage"
      class="router-link-button"
      :disabled="disableNextButton"
    >
      {{ nextButtonNames }}
    </button>

    <div>
      <!-- TODO: Disse skal styles slik at de er tre små runde prikker-->
      <button
        @onclick="onClickDot(1)"
        class="GradeOfCompletion"
        :disabled="true"
      >
        1
      </button>
      <button
        @onclick="onClickDot(2)"
        class="GradeOfCompletion"
        :disabled="true"
      >
        2
      </button>
      <button
        @onclick="onClickDot(3)"
        class="GradeOfCompletion"
        :disabled="true"
      >
        3
      </button>
      <button
        @onclick="onClickDot(4)"
        class="GradeOfCompletion"
        :disabled="true"
      >
        4
      </button>
    </div>
  </div>
</template>

<script lang="ts">
import {
  defineComponent,
  computed,
  Ref,
  ref,
  watchEffect,
  reactive,
} from "vue";
import Month from "../interfaces/Month.interface";
import { useRouter } from "vue-router";
import axios from "../axiosConfig";
import SignUpUser from "../interfaces/User/SignUpUser.interface";
import { useStore } from "../store";

export default defineComponent({
  name: "SignUpScroll",
  setup() {
    //overall
    const router = useRouter();
    const stage = ref(1);
    const disableDotTwo = ref(true);
    const disableDotThree = ref(true);
    const disableDotFour = ref(true);

    const buttonBackNames = computed(() => {
      if (stage.value === 1) return "Avbryt";
      return "Tilbake";
    });

    const nextButtonNames = computed(() => {
      if (stage.value === 4) {
        return "Godta og registrer";
      } else return "Neste";
    });

    const disableNextButton = computed(() => {
      return disableNextButtonStageOne.value || disableNextButtonStageTwo.value;
    });

    const nextPage = () => {
      if (stage.value < 4) {
        stage.value++;
      }
      else if(stage.value === 4){
          saveUser(); 
      }
    };

    const prevPage = () => {
      if (stage.value > 1) {
        stage.value--;
      }
      else if (stage.value === 1) {
        router.push("/");
      }
    };

    const onClickDot = (dotValue: number) => {
      stage.value = dotValue;
    };

    //TODO:fungerer ikke enda, noe for sprint 2?
    const disableDots = watchEffect((): void => {
      if (stage.value === 1) {
        disableDotTwo.value = true;
        disableDotThree.value = true;
        disableDotFour.value = true;
      } else if (stage.value === 2) {
        disableDotTwo.value = false;
        disableDotThree.value = true;
        disableDotFour.value = true;
      } else if (stage.value === 3) {
        disableDotTwo.value = false;
        disableDotThree.value = false;
        disableDotFour.value = true;
      } else if (stage.value === 4) {
        disableDotTwo.value = false;
        disableDotThree.value = false;
        disableDotFour.value = false;
      }
    });

    //Methods for stage one
    const selectedYear = ref();
    const selectedMonth = ref("");
    const selectedDay = ref();
    const currentYear = new Date().getFullYear();
    const limitForLowerYear = 120;

    const availableYears = computed(() => {
      const years = [];
      for (let i = currentYear - limitForLowerYear; i < currentYear; i++) {
        years.push(i);
      }
      return years.reverse();
    });

    const isLeapYear = computed(() => {
      if (selectedYear.value === 0) return false;
      return (
        selectedYear.value % 400 === 0 ||
        (selectedYear.value % 100 !== 0 && selectedYear.value % 4 === 0)
      );
    });

    const daysInCurrentMonth = computed(() => {
      if (selectedMonth.value === "") {
        return 0;
      }
      //else if(selectedMonth.value === "Februar" && isLeapYear) return 29;
      else
        return months.value.find((month) => month.name === selectedMonth.value)
          ?.numberOfDays;
    });

    const isEmailValid = computed(() => {
      return user.email.includes("@") && user.email.includes(".");
    });

    const isNameValid = computed(() => {
      return user.forename.trim() !== "" && user.surname.trim() !== "";
    });

    const isBirthDateValid = computed(() => {
      return (
        selectedYear.value > 0 &&
        selectedMonth.value > "" &&
        selectedDay.value > 0
      );
    });

    const disableNextButtonStageOne = computed(() => {
      return (
        stage.value === 1 &&
        (!isEmailValid.value || !isNameValid.value || !isBirthDateValid.value)
      );
    });

    //methods for stage two
    const repeatPassword = ref("");

    const isPasswordSecure = computed((): boolean => {
      return user.password.length >= 8;
    });

    const doesPasswordsMatch = computed((): boolean => {
      return user.password === repeatPassword.value;
    });

    const passwordFeedback = computed(() => {
      if (!isPasswordSecure.value) {
        return "Passordet må være minst 8 tegn";
      } else if (!doesPasswordsMatch.value) {
        return "Passordene er ikke like";
      }
      return "";
    });

    const disableNextButtonStageTwo = computed((): boolean => {
      return (
        stage.value === 2 &&
        (!doesPasswordsMatch.value || !isPasswordSecure.value)
      );
    });

    const months: Ref<Month[]> = ref([
      { name: "Januar", numberOfDays: 31 },
      { name: "Februar", numberOfDays: 29 },
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

    //methods for stage three
    //TODO: upload fungerer ikke
    let files = ref();
    const uploadFile = ref((event: any) => {
      files = event.target.files;
    });

    const handleSubmit = ref(() => {
      const formData = new FormData();
      for (const i of Object.keys(files.value)) {
        formData.append("files", files.value[i]);
      }
      axios
        .post("http://localhost:4000/api/file-upload", formData, {})
        .then((res) => {
          console.log(res);
        });
    });

    //methods for stage four
    const termsAndConditions = ref(
        "Dette er terms and conditions. " + 
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit," +
        " sed do eiusmod tempor incididunt ut labore et dolore magna aliqua." +
        "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
        "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. " +
        "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
    );

    //methods for connection to backend
    const store = useStore();
    const birthdate = computed(() => {
      return (
        selectedYear.value + "-" + selectedMonth.value + "-" + selectedDay.value
      );
    });

    const user = reactive({
      email: "",
      password: "",
      forename: "",
      surname: "",
      birthdate: birthdate.value,
    } as SignUpUser);

    const saveUser = async (): Promise<void> => {
     if(await store.dispatch("register", user)){
        router.replace("/activity-feed");
      } else {
          router.push("/error");
      }
    };

    return {
      //stage one
      months,
      daysInCurrentMonth,
      //isLeapYear, til eventuell bruk senere
      availableYears,
      selectedYear,
      selectedMonth,
      selectedDay,
      isNameValid,
      isEmailValid,
      isBirthDateValid,
      disableNextButton,

      //stage two
      passwordFeedback,
      repeatPassword,

      //stage three
      uploadFile,
      handleSubmit,

      //stage four
      termsAndConditions,

      //overall
      buttonBackNames,
      disableDots,
      nextButtonNames,
      nextPage,
      prevPage,
      onClickDot,
      stage,
      user,
      saveUser,
    };
  },
});
</script>

<style scoped>
.terms-box {
  overflow: scroll;
}

li {
  list-style-type: none;
}

.page {
  overflow: scroll;
}
</style>
