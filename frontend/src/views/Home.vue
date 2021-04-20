<template>
  <header>
    <div id="header-intro">
      <div id="header-intro-content">
        <h4>Gidd å bli kjent med nye folk</h4>
      </div>
    </div>
    <div id="header-read-more">
      <div id="header-read-more-signup">
        <router-link class="router-link-button" to="/sign-up"
          >Opprett bruker</router-link
        >
      </div>
      <div id="header-read-more-login">
        Har du allerede bruker?
        <router-link to="/log-in">Logg inn</router-link>
      </div>
      <div id="header-read-more-scroll">
        Les mer
        <i class="fa fa-caret-down"></i>
      </div>
    </div>
  </header>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  name: "Home",
  components: {},
});
</script>

<style lang="scss" scoped>
@import url("https://fonts.googleapis.com/css2?family=Tajawal:wght@700&display=swap");
@import url("https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css");

$primary-color: #282828;
$secondary-color: #ea4b4b;

header {
  display: grid;
  grid-template-rows: 130% 20%;
  margin-top: -84px;
  z-index: -1;
  @media only screen and (min-width: 600px) {
    grid-template-rows: 100% 20%;
  }
}

#header-intro {
  background: url("../../img/background.png");
  background-position: center;
  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover;
  grid-row: 1;
}

#header-intro-content {
  text-align: left;
  width: 70%;
  float: left;
  padding: 35px;
  margin-top: 40px;
}

h4 {
  font-family: "Tajawal", sans-serif;
  font-size: 2.3rem;
  color: #282828;
  letter-spacing: 0cm;
  line-height: 2.1rem;
  @media only screen and (min-width: 600px) {
    color: #ffffff;
  }
}

#header-read-more {
  grid-row: 2;
  padding: 1.3rem;
}

#header-read-more-signup {
  padding: 1rem;
}

#header-read-more-login {
  padding: 0.5rem;
  font-size: 0.833rem;
}

#header-read-more-scroll {
  color: $secondary-color;
  padding: 1.2rem;
}
</style>
