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
    <input v-model="firstName" type="name" placeholder="Nytt fornavn" />
    <p>Endre etternavn</p>
    <input v-model="lastName" type="name" placeholder="Nytt etternavn" />
    <p>Endre email</p>
    <input v-model="email" type="email" placeholder="Ny email" />
    <span v-if="emailIsNotEmpty">
      <p>
        {{ makeEmailFeedback }}
      </p>
    </span>
    <p>Endre passord</p>
    <input v-model="password" type="password" placeholder="Nytt passord" />
    <input
      v-model="repeatPassword"
      type="password"
      placeholder="Gjenta passord"
    />
    <span v-if="passwordIsNotEmpty">
      <p>{{ makePasswordFeedback }}</p>
    </span>
    <p>{{ matchingPasswords }}</p>
    <button @click="saveProfileChanges" alt="Knapp som lagrer profilendringer">
      Lagre endringer
    </button>
    <button @click="deleteUser" alt="Knapp for å slette brukeren">
      Slett brukeren
    </button>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent, ref } from "vue";
import { useRouter } from "vue-router";

export default defineComponent({
  name: "EditProfile",
  setup() {
    const router = useRouter();
    const firstName = ref("");
    const lastName = ref("");
    const email = ref("");
    const password = ref("");
    const validPassword = ref(false);
    const passwordFeedback = ref("");
    const emailFeedback = ref("");
    const repeatPassword = ref("");
    const matchingPasswords = ref("");
    const numberOfCharacterPass = ref(8);

    const passwordIsNotEmpty = computed(() => {
      return password.value.trim() !== "";
    });

    const emailIsNotEmpty = computed(() => {
      return email.value.trim() !== "";
    });

    const saveProfileChanges = (): void => {
      matchingPasswords.value = "";
      //TODO communicate with backend
      if (password.value != repeatPassword.value) {
        matchingPasswords.value = "Passordene må være like";
        password.value = "";
        repeatPassword.value = "";
      }
    };

    const makePasswordFeedback = computed(() => {
      if (password.value.length < numberOfCharacterPass.value) {
        return (
          passwordFeedback.value +
          "Passordet må være minst " +
          numberOfCharacterPass.value +
          " tegn"
        );
      }
      return passwordFeedback.value + "Passordet er gyldig";
    });

    const makeEmailFeedback = computed(() => {
      if (!(email.value.includes("@") && email.value.includes("."))) {
        return emailFeedback.value + "Emailen må være gyldig";
      }
      return emailFeedback.value + "";
    });

    const changeProfilePic = (): void => {
      //TODO fix upload
    };

    const deleteUser = (): void => {
      //Få opp en liten "Er du sikker?"
      //Så slett bruker
    };

    return {
      router,
      firstName,
      lastName,
      email,
      password,
      validPassword,
      passwordFeedback,
      passwordIsNotEmpty,
      saveProfileChanges,
      makePasswordFeedback,
      emailIsNotEmpty,
      makeEmailFeedback,
      changeProfilePic,
      repeatPassword,
      matchingPasswords,
      deleteUser,
    };
  },
});
</script>
