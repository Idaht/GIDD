<template>
  <div id="nav">
    <TheHeader />
  </div>
  <router-view />
</template>

<script lang="ts">
import { defineComponent } from "vue";
import TheHeader from "@/components/TheHeader.vue";

export default defineComponent({
  name: "App",
  components: {
    TheHeader,
  }
});
</script>

<style lang="scss">
@import url("https://fonts.googleapis.com/css2?family=Mulish&display=swap");

$font-stack: "Mulish", sans-serif;
$primary-color: #282828;
$secondary-color: #ea4b4b;
$padding: 0.6rem 1rem 0.6rem 1rem;


body {
  margin: 0;
}

#app {
  font-family: "Mulish", sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  font-size: 16px;
  letter-spacing: 0.04rem;
  display: grid;
}

#nav {
  //TODO dont have empty nav
}

button {
  background: #ea4b4b;
  border: 0px solid #000000;
  box-sizing: border-box;
  border-radius: 40px;
  padding: $padding;
  font-variant: small-caps;
  font-family: "mulish";
  font-weight: 500;
  color: white;
  font-size: 0.833rem;
}

input:focus {
  outline: none;
  box-shadow: 0 0 3px 0 black;
}

input {
  background: #ffffff;
  box-sizing: border-box;
  border: 1px solid #9f9f9f;
  border-radius: 20px;
  padding: $padding;
  font: normal 1rem "mulish", sans-serif;
  color: $primary-color !important;
  margin: auto;
  display: inline;
}

a:link {
  color: $primary-color;
}

.router-link-button {
  color: #ffffff;
  background-color: $secondary-color;
  border-radius: 40px;
  text-transform: uppercase;
  padding: $padding;
  font-weight: 600;
  text-decoration: none;
  font-size: 0.6em;
}
</style>
