<template>
  <div>
    <!--Add TheHeader component -->
    <h1>Tilbakestill passord</h1>
    <p>
      Vennligst skriv inn e-postadressen du registrerte deg med, så vil du motta
      en e-post med informasjon om gjennoppretting av passord
    </p>
    <input v-model="email" type="email" placeholder="E-post" id="email" />
    <button
      @click="sendMailForgotPassword"
      alt="Knapp for å sende e-post når passord er glemt"
    >
      Send
    </button>
    <p id="feedback" v-if="emailIsNotEmpty">{{ makeEmailFeedback }}</p>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent, ref } from "vue";
import { useRouter } from "vue-router";

export default defineComponent({
  name: "ForgottenPassword",
  setup() {
    const router = useRouter();
    const email = ref("");
    const emailFeedback = ref("");

    const emailIsNotEmpty = computed(() => {
      return email.value.trim() !== "";
    });

    //TODO Må ha en sjekk om at e-postadresse eksisterer
    const sendMailForgotPassword = (): void => {
      if (validEmail.value && emailIsNotEmpty.value) {
        router.push("log-in");
      }
    };

    const makeEmailFeedback = computed(() => {
      if (!validEmail.value) {
        return emailFeedback.value + "E-postadressen må være gyldig";
      }
      return emailFeedback.value + "";
    });

    const validEmail = computed(() => {
      return email.value.includes("@") && email.value.includes(".");
    });

    return {
      router,
      email,
      emailIsNotEmpty,
      makeEmailFeedback,
      sendMailForgotPassword,
      emailFeedback,
    };
  },
});
</script>
