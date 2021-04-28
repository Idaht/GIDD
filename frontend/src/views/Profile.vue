<template>
  <div id="profile">
    <h1>Min profil</h1>
    <img id="profile-picture" :src="profilePicture" alt="profile-picture" />
    <div id="profile-wrapper">
      <div id="profile-name-wrapper">
        <div id="profile-name">
          <h2 id="profile-name-h3">{{ user.forename }} {{ user.surname }}</h2>
          <img
            v-if="trusted"
            id="verified"
            src="../../img/verified.png"
            height="20"
          />
        </div>
      </div>
      <div id="profile-information">
        <div id="profile-information-poins">
          <label>Poeng</label>
          <div>{{ user.score }}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import router from "@/router";
import { onBeforeMount, defineComponent, ref, Ref, computed } from "vue";
import User from "../interfaces/User/User.interface";
import axios from "../axiosConfig";

export default defineComponent({
  props: ["id"],
  setup(props) {
    const user = ref({}) as Ref<User>;
    const trusted = ref(false);

    const profilePicture = computed(() => {
      let val =
        user.value.profilePicture !== null
          ? user.value.profilePicture
          : require("../assets/hamster-pfp.jpg");
      return val;
    });

    /**
     * Connects to backend using a get request to get the user
     */
    onBeforeMount(async () => {
      try {
        const response = await axios.get(`/users/${props.id}`);
        user.value = response.data;
        trusted.value = user.value.trusted;
        console.log(trusted.value);
      } catch {
        router.push("/error");
      }
    });

    return {
      profilePicture,
      trusted,
      user,
    };
  },
});
</script>

<style scoped lang="scss">
@import url("https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css");

$primary-color: #282828;
$secondary-color: #ea4b4b;

#profile {
  margin: 35px;
}

#profile-information,
#profile-picture {
  margin: 30px 0px 30px 0px;
}

h1 {
  margin: 0;
  margin-bottom: 10px;
}

h3 {
  margin: 10px;
}

label {
  font-weight: 700;
}

#profile-wrapper {
  @media only screen and (min-width: 600px) {
    width: 45%;
    margin: auto;
  }
}

#profile-picture {
  width: 100px;
  border-radius: 50%;
  background-color: #999;
  align-items: center;
  justify-content: center;
}

#profile-name-wrapper {
  padding: 10px;
  display: grid;
  grid-template-columns: 1fr 2fr 2fr 1fr;
}

#profile-name {
  grid-column: 2/4;
  justify-self: center;
  display: grid;
  grid-template-columns: 3fr 1fr;
  text-align: center;
}

#profile-name-h3 {
  grid-column: 1/2;
  margin: 0px 10px 0px 10px;
  align-self: center;
}

#verified {
  align-self: center;
  grid-column: 2/3;
  margin: 0px 10px 0px 10px;
}

#profile-information {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
}
</style>
