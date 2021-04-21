<template>
  <div>
    <!--Add TheHeader component -->
    <h1>Instillinger</h1>
    <img src="" alt="Profilbilde" />
    <!-- Profile pic here -->
    <button @click="changeProfilePic" alt="Knapp for å endre profilbilde">
      Endre profilbilde
    </button>
    <p>Endre fornavn</p>
    <input v-model="user.forename" type="name" placeholder="Nytt fornavn" />
    <p>Endre etternavn</p>
    <input v-model="user.surname" type="name" placeholder="Nytt etternavn" />
    <p>Endre email</p>
    <input v-model="user.email" type="email" placeholder="Ny email" />
    <p v-if="emailIsNotEmpty && !isValidEmail">
      E-postadressen må inneholde @ og .
    </p>
    <p>Endre passord</p>
    <input v-model="password" type="password" placeholder="Nytt passord" />
    <input
      v-model="repeatPassword"
      type="password"
      placeholder="Gjenta passord"
    />
    <p v-if="!matchingPasswords && passwordIsNotEmpty">
      Passordene må være like
    </p>
    <p v-if="!passwordIsValid && passwordIsNotEmpty">
      Nytt passord må inneholde minst 8 tegn
    </p>
    <p>Gammelt passord</p>
    <input v-model="oldPassword" type="password" />
    <p v-if="!oldPasswordWasCorrect">
      Gammelt passord var ikke riktig. Besøk:
      <router-link to="/forgotten-password">Glemt passord</router-link> om du
      har glemt ditt gamle passord
    </p>
    <button
      :disabled="!isValidForm"
      @click="saveProfileChanges"
      alt="Knapp som lagrer profilendringer"
    >
      Lagre endringer
    </button>
    <button @click="deleteUser" alt="Knapp for å slette brukeren">
      Slett brukeren
    </button>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent, Ref, ref } from "vue";
import { useRouter } from "vue-router";
import axios from "@/axiosConfig";
import { useStore } from "@/store";
import User from "@/interfaces/User/User.interface";
import EditUser from "@/interfaces/User/EditUser.interface";

export default defineComponent({
  name: "EditProfile",
  setup() {
    const store = useStore();
    const router = useRouter();
    //Using ref since we are setting values
    const user: Ref<User> = ref(store.getters.user);

    const isValidName = computed(() => {
      return (
        user.value.forename.trim() !== "" && user.value.surname.trim() !== ""
      );
    });

    //Password
    const password = ref("");
    const passwordFeedback = ref("");
    const repeatPassword = ref("");
    const matchingPasswords = computed(() => {
      return password.value == repeatPassword.value;
    });
    const numberOfCharacterPass = 8;
    const passwordIsValid = computed(() => {
      return password.value.length >= 8;
    });

    const passwordIsNotEmpty = computed(() => {
      return password.value.trim() !== "";
    });

    const isValidPassword = computed(() => {
      return matchingPasswords.value && passwordIsNotEmpty.value;
    });

    const makePasswordFeedback = computed(() => {
      if (password.value.length < numberOfCharacterPass) {
        return (
          passwordFeedback.value +
          "Passordet må være minst " +
          numberOfCharacterPass +
          " tegn"
        );
      }
      return passwordFeedback.value + "Passordet er gyldig";
    });

    //Gammelt passord
    const oldPassword = ref("");

    const oldPasswordIsNotEmpty = computed(() => {
      return oldPassword.value.trim() !== "";
    });

    const oldPasswordWasCorrect = ref(true);

    //Email
    const emailIsNotEmpty = computed(() => {
      return user.value.email.trim() !== "";
    });

    const isValidEmail = computed(() => {
      return user.value.email.includes("@") && user.value.email.includes(".");
    });

    const isValidForm = computed(() => {
      return (
        (!passwordIsNotEmpty.value ||
          (isValidPassword.value &&
            oldPasswordIsNotEmpty.value &&
            passwordIsValid.value &&
            matchingPasswords.value)) &&
        isValidEmail.value &&
        isValidName.value
      );
    });
    const saveProfileChanges = async (): Promise<void> => {
      oldPasswordWasCorrect.value = true;
      if (isValidForm.value) {
        try {
          //TODO change to redirect to something went wrong site
          //TODO handle bug where _ctx.user is undefined
          const userDTO: EditUser = {
            userId: user.value.userId,
            email: user.value.email,
            forename: user.value.forename,
            surname: user.value.surname,
          };
          if (passwordIsNotEmpty.value) {
            userDTO.newPassword = password.value;
            userDTO.oldPassword = oldPassword.value;
          }
          const response = await axios.post(
            `/users/${userDTO.userId}`,
            userDTO
          );
          await store.dispatch("updateUser", response.data);
          user.value = store.getters.value;
        } catch (error) {
          //TODO fix bug with error.response is undefined
          if (error.response.status === 400) {
            oldPasswordWasCorrect.value = false;
          } else {
            router.push("/error");
          }
        }
      }
    };

    const changeProfilePic = (): void => {
      //TODO fix upload
    };

    const deleteUser = async (): Promise<void> => {
      if (window.confirm("Er du sikker på at du vil slette brukeren din")) {
        try {
          const response = await axios.delete(
            `/users/${store.getters.user.userId}`
          );
          if (response.status === 200) {
            store.dispatch("logout");
            router.replace("/log-in");
          }
        } catch (error) {
          //TODO add errorhandling
        }
      }
    };

    return {
      user,
      password,
      passwordIsValid,
      passwordFeedback,
      passwordIsNotEmpty,
      saveProfileChanges,
      makePasswordFeedback,
      emailIsNotEmpty,
      changeProfilePic,
      repeatPassword,
      matchingPasswords,
      deleteUser,
      isValidEmail,
      isValidForm,
      oldPassword,
      oldPasswordWasCorrect,
    };
  },
});
</script>
