<template>
  <div id="sign-up">
    <span @click="prevPage" class="back-button">
      <i class="fa fa-arrow-left" aria-hidden="true"></i>
      {{ buttonBackNames }}
    </span>
    <div id="page-one" v-if="stage === 1">
      <h2>Opprett bruker</h2>
      <div id="sign-up-form-container">
        <input
          class="sign-up-form-input"
          type="epost"
          placeholder="E-post"
          v-model="user.email"
        />
        <input
          class="sign-up-form-input"
          type="fornavn"
          placeholder="Fornavn"
          v-model="user.forename"
        />
        <input
          class="sign-up-form-input"
          type="etternavn"
          placeholder="Etternavn"
          v-model="user.surname"
        />
        <!-- TODO: fiks sånn at option vises i dropdown. Er ikke mulig nå
        pga select bruker v-model, så må trolig fikses i js -->
        <div id="sign-up-form-birthday-container">
          <h4>Fødselsdato</h4>
          <div id="sign-up-form-birthday">
            <div id="birthday-year" class="birthday-form">
              <h5>År</h5>
              <select class="dropdown" v-model="selectedYear">
                <option hidden disabled value>Velg år</option>
                <option v-for="(year, index) in availableYears" :key="index">
                  {{ year }}
                </option>
              </select>
            </div>
            <div id="birthday-month" class="birthday-form">
              <h5>Måned</h5>
              <select name="month" v-model="selectedMonth">
                <option value="month" selected disabled hidden>
                  Velg måned
                </option>
                <option v-for="(month, index) in months" :key="month + index">
                  {{ month.name }}
                </option>
              </select>
            </div>
            <div id="birthday-day" class="birthday-form">
              <h5>Dag</h5>
              <select name="day" v-model="selectedDay">
                <option value="day" selected disabled hidden>Velg dato</option>
                <option v-for="index in daysInCurrentMonth" :key="index">
                  {{ index }}
                </option>
              </select>
            </div>
          </div>
        </div>
      </div>
      <div id="conditions-container">
        <ul>
          <li v-if="!isEmailValid">! Epost må inneholde både @ og .</li>
          <li v-if="!isNameValid">
            ! Navn må inneholde både fornavn og etternavn
          </li>
          <li v-if="!isBirthDateValid">
            ! Fødselsdato må inneholde år, måned og dato
          </li>
        </ul>
      </div>
    </div>

    <div id="page-two" v-else-if="stage === 2">
      <h2>Skriv inn passord</h2>
      <div id="sign-up-form-container">
        <input
          class="sign-up-form-input"
          type="password"
          v-model="user.password"
          placeholder="Passord"
        />
        <input
          class="sign-up-form-input"
          type="password"
          v-model="repeatPassword"
          placeholder="Gjenta passord"
        />
        <div id="conditions-container">
          <span> {{ passwordFeedback }} </span>
        </div>
      </div>
    </div>

    <!-- TODO: Fiks opplastning til profilbilde -->
    <div id="page-three" v-else-if="stage === 3">
      <h2>Last opp profilbilde</h2>
      <div class="sign-up-form-container-picture">
        <img
          id="profile-picture"
          src="../../img/hamster-pfp.jpg"
          alt="profile-picture"
        />
        <form @submit.prevent="handleSubmit">
          <div class="picture-upload-submit">
            <input
              id="picture-input"
              type="file"
              @change="uploadFile"
              multiple
            />
          </div>
          <div class="picture-upload-submit">
            <span>Last opp</span>
          </div>
        </form>
      </div>
    </div>

    <div id="page-four" v-else-if="stage === 4">
      <h2>Godta betingelser</h2>
      <div class="terms-and-conditions-container">
        {{ termsAndConditions }}
        {{ termsAndConditions }}
      </div>
      <!-- TODO: Deaktiver "registrer"-knappen frem til boksen er krysset av -->
      <input type="checkbox" name="checkbox" value="check" id="agree" /> Jeg
      godtar brukervilkårene
    </div>

    <div id="wrong" v-else>
      <h1>Something went wrong</h1>
    </div>

    <div id="navigation">
      <button
        @click="nextPage"
        class="router-link-button"
        :disabled="disableNextButton"
      >
        {{ nextButtonNames }}
      </button>

      <div id="dots-container">
        <button
          @onclick="onClickDot(1)"
          class="completion-dot"
          id="betch"
          :disabled="false"
        ></button>
        <button
          @onclick="onClickDot(2)"
          class="completion-dot"
          :disabled="true"
        ></button>
        <button
          @onclick="onClickDot(3)"
          class="completion-dot"
          :disabled="true"
        ></button>
        <button
          @onclick="onClickDot(4)"
          class="completion-dot"
          :disabled="true"
        ></button>
      </div>
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
    const termsChecked = ref(true);
    const disableDotTwo = ref(true);
    const disableDotThree = ref(true);
    const disableDotFour = ref(true);

    const buttonBackNames = computed(() => {
      if (stage.value === 1) return "Avbryt";
      return "Tilbake";
    });

    const nextButtonNames = computed(() => {
      if (stage.value === 4) {
        return "Registrer";
      } else return "Neste";
    });

    const disableNextButton = computed(() => {
      return disableNextButtonStageOne.value || disableNextButtonStageTwo.value;
    });

    const nextPage = () => {
      if (stage.value < 4) {
        stage.value++;
      } else if (stage.value === 4) {
        saveUser();
      }
    };

    const prevPage = () => {
      if (stage.value > 1) {
        stage.value--;
      } else if (stage.value === 1) {
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
      if (await store.dispatch("register", user)) {
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
      termsChecked,

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

<style scoped lang="scss">
$primary-color: #282828;
$secondary-color: #ea4b4b;
$disabled-color: #d7dce6;
$padding: 0.6rem 1rem 0.6rem 1rem;

h4 {
  margin: 0;
}

h5 {
  margin: 0;
  padding-right: 13px;
}

#sign-up {
  margin: 35px;
  text-align: left;
  color: $primary-color;
  margin-bottom: 130px;
  @media only screen and (min-width: 600px) {
    width: 30%;
    margin: auto;
  }
}

#sign-up-form-container {
  display: grid;
  grid-auto-rows: 3rem 3rem 3rem 6rem;
  margin-bottom: 20px;
}

.sign-up-form-container-picture {
  text-align: center;
}

.sign-up-form-input {
  width: 100%;
  border-radius: 0;
  border-width: 0;
  border-bottom: 2px #d7dce6 solid;
}

.sign-up-form-input:active,
.sign-up-form-input:focus {
  border: none;
  box-shadow: none;
  border-bottom: 5px $secondary-color solid;
}

#sign-up-form-birthday-container {
  display: grid;
  grid-template-rows: auto;
  margin: 20px 0px 20px 0px;
}

#sign-up-form-birthday {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  margin-top: 10px;
}

.birthday-form {
  width: 100%;
  text-align: center;
}

select {
  width: 90%;
  height: 2rem;
  border-radius: 0;
  border-width: 0;
  border-bottom: 2px $disabled-color solid;
  font-family: "Mulish", sans-serif;
  font-size: 1rem;
  text-align-last: center;
  color: #54545e;
}

option {
  height: 30px;
}

select:focus {
  box-shadow: none;
}

#conditions-container {
  padding-top: 5px;
  padding-bottom: 15px;
  height: 60px;
  font-weight: 600;
  color: $secondary-color;
  font-size: 10px;
  line-height: 20px;
}

ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
}

#profile-picture {
  width: 150px;
  border-radius: 50%;
  background-color: #999;
  align-items: center;
  justify-content: center;
  justify-self: center;
}

.picture-upload-submit {
  margin-top: 15px;
  font-weight: 600;
}

#picture-input {
  width: 100%;
}

#navigation {
  text-align: center;
  position: fixed;
  padding-top: 10px;
  padding-bottom: 30px;
  bottom: 0px;
  left: 0;
  right: 0;
  background-color: #ffffff;
}

button:disabled {
  background-color: $disabled-color;
  color: #9499a5;
}

#dots-container {
  display: grid;
  padding-top: 10px;
  margin: auto;
  grid-template-columns: 1fr 1fr 1fr 1fr;
  width: 100px;
  justify-content: center;
}

button.completion-dot {
  height: 15px;
  width: 15px;
  border-radius: 20px;
  margin: 5px;
  display: block;
  background: $secondary-color;
  padding: 0px;
}

button.completion-dot:disabled {
  border: 2px $secondary-color solid;
  background-color: #ffffff;
  opacity: 100%;
}

.back-button {
  color: $primary-color;
  text-transform: uppercase;
  letter-spacing: 1px;
  font-weight: 600;
  font-size: 0.7rem;
  width: 60px;
  text-align: center;
}

.back-button:hover {
  color: $secondary-color;
}

.terms-and-conditions-container {
  overflow-y: scroll;
  font-size: 0.8rem;
  line-height: 1rem;
  height: 16rem;
  margin-bottom: 20px;
}

::-webkit-scrollbar {
  width: 0;
  background: transparent;
}

::-webkit-scrollbar-thumb {
  background: #ff0000;
}
</style>