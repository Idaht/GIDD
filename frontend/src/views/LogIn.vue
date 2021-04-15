<template>
  <div id="login">
    <h1>Logg inn</h1>
    <div class="login-field">
      <input v-model="email" type="email" placeholder="E-post" />
      <input v-model="password" type="password" placeholder="Passord" />
      <div class="login-btn">
        <button @click="login">LOGG INN</button>
        <p id="feedback" v-if="isThereFeedback">{{ feedback }}</p>
      </div>
      <router-link to="/forgotten-password">Glemt passord?</router-link>
    </div>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent, ref } from "vue";
import { useRouter } from "vue-router";
export default defineComponent({
  name: "LogIn",
  setup() {
    const router = useRouter();
    const feedback = ref("");
    const email = ref("");
    const password = ref("");

    const isThereFeedback = computed(() => {
      return feedback.value.trim() !== "";
    });
    const login = (): void => {
      feedback.value = "";
      //TODO add store.dispatch when backend is ready, and remove booelan
      const placeholderRemoveThis = false;
      if (placeholderRemoveThis) router.push("/activity-feed");
      //TODO maybe add better error message
      else feedback.value = "Kan ikke logge på!";
    };

    return {
      feedback,
      login,
      email,
      password,
      isThereFeedback,
    };
  },
});
</script>

<style lang="scss">
#login {
  width: 340px;
  margin: 0 auto;
  box-sizing: border-box;
  padding-top: 90px;
}

.login-field {
  margin: 30px;
}

input {
  margin: 1em 0 0 0;
}

button {
  margin: 1.5em 0 1em 0;
  align-content: center;
}

</style>
