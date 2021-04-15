<template>
  <div>
    <!--Add TheHeader component -->
    <h1>LOGG IN</h1>
    <input v-model="email" type="email" placeholder="E-post" />
    <input v-model="password" type="password" placeholder="Passord" />
    <button @click="login">LOGG INN</button>
    <p id="feedback" v-if="feedback.trim() !== ''">{{ feedback }}</p>
    <router-link to="/forgotten-password">Glemt passord?</router-link>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";
import { useRouter } from "vue-router";
export default defineComponent({
  name: "LogIn",
  setup() {
    const router = useRouter();
    const feedback = ref("");
    const email = ref("");
    const password = ref("");

    const login = (): void => {
      feedback.value = "";
      //TODO add store.dispatch when backend is ready, and remove booelan
      const placeholderRemoveThis = false;
      if (placeholderRemoveThis) router.push("/activityFeed");
      //TODO maybe add better error message
      else feedback.value = "Kan ikke logge på!";
    };

    return {
      feedback,
      login,
      email,
      password,
    };
  },
});
</script>

<style scoped></style>
