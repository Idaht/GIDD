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
import { BackendStatus } from "@/enums/BackendStatus.enum";
import LogInUser from "@/interfaces/User/LoginUser.interface";
import { useStore } from "@/store";
import { computed, defineComponent, ref } from "vue";
import { useRouter } from "vue-router";
export default defineComponent({
  name: "LogIn",
  setup() {
    const router = useRouter();
    const store = useStore();
    const email = ref("");
    const password = ref("");

    const isThereFeedback = computed(() => {
      return store.getters.authenticationStatus !== BackendStatus.OK;
    });

    const feedback = computed(() => {
      const status = store.getters.authenticationStatus;
      if (status === BackendStatus.PENDING) return "Laster inn...";
      if (status === BackendStatus.ERROR)
        return "Kan ikke logge på! Sjekk at e-postaddresse og passord er riktig";
      return "";
    });

    const login = async (): Promise<void> => {
      const user: LogInUser = { email: email.value, password: password.value };
      if (await store.dispatch("login", user)) router.push("/activity-feed");
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

<style scoped lang="scss">
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
