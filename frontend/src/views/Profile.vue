<template>
  <div>Image here</div>
  <h3>{{ user.forename }} {{ user.surname }}</h3>
  <label>Alder</label>
  <div>{{ age }}</div>
  <label>Poeng</label>
  <div>{{ user.score }}</div>
  <p>Noen stjerner elns her (rating)</p>
  <div>{{ user.rating }}</div>
  <label>Bio</label>
  <div>{{ userBio }}</div>
  <span v-if="trusted">
    <label>{{ trustedText }}</label>
    <div>Trusted badge yey</div>
  </span>
</template>

<script lang="ts">
import router from "@/router";
import { onBeforeMount, defineComponent, ref } from "vue";
import axios from "../axiosConfig";

export default defineComponent({
  props: ["id"],
  setup(props) {
    //TODO hente ut/fikse resterende variabler
    const user = ref({});
    const age = ref(24); //Ha? Hente fødselsdato og regne ut alderen?
    const userBio = ref("Jeg liker ost og å dø på joggeturer");
    const trusted = ref(true); //Hente ut dett
    const trustedText = ref("Brukeren er trusted");

    /**
     * Connects to backend using a get request to get the user
     */
    onBeforeMount(async () => {
      try {
        const response = await axios.get(`/users/${props.id}`);
        user.value = response.data;
      } catch {
        router.push("/error");
      }
    });

    return {
      age,
      userBio,
      trusted,
      trustedText,
      user,
    };
  },
});
</script>
