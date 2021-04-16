<template>
  <div class="the-header">
    <span id="login-check" class="logo-nav" v-if="loggedIn">
      <router-link to="/activity-feed">
        <!-- Add proper link -->
        <img src="../../img/logo.png" alt="Logo" height="20" />
      </router-link>
    </span>
    <span v-else class="logo-nav">
      <router-link to="/homepage">
        <!-- Add proper link -->
        <img src="../../img/logo.png" alt="Logo" height="20" />
      </router-link>
    </span>
    <div class="menu-box">
      <span v-if="loggedIn">
        <a href="#" class="icon" @click="toggleMenu">
            <i class="fa fa-bars"></i>
          </a>
        <!--Change to logged in menu img-->
      </span>
      <span v-else>
        <a href="#" class="icon" @click="toggleMenu">
            <i class="fa fa-bars"></i>
          </a>
        <!--Changed to logged out menu img-->
      </span>

      <span id="menu-toggle" v-if="menuVisible">
        <span v-if="loggedIn">
          <span
            id="logged-in-option"
            v-for="(option, index) in loggedInOptions"
            :key="index"
          >
            {{ option.title }}
          </span>
        </span>
        <span v-else>
          <div id="close-menu" @click="toggleMenu">
            <i class="fa fa-times"></i>
          </div>
          <div v-for="(option, index) in loggedOutOptions" :key="index" class="menu-options">
            {{ option.title }}
          </div>
        </span>
      </span>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, Ref, ref } from "vue";
import MenuOption from "../interface/menuOption.interface";

/**
 * Defines components to be used
 *
 * @param loggedInOptions:     Side menu options when a user is logged in  - Array
 * @param loggedOutOptions:    Side menu options when a user is logged out - Array
 * @param loggedIn:            Check if user is logged in or out           - Boolean
 * @param menuVisible:         Check if menu is visible or not             - Boolean
 * @param toggleMenu:          Method to toggle the visibility of the menu
 */
export default defineComponent({
  name: "TheHeader",
  setup() {
    const loggedInOptions: Ref<MenuOption[]> = ref([
      { title: "Min profil", path: "/my-profile" },
      { title: "Innstiller", path: "/settings" },
      { title: "Finn aktivitet", path: "/find-acitivity" },
    ]);
    const loggedOutOptions: Ref<MenuOption[]> = ref([
      { title: "Logg inn", path: "/log-out" },
      { title: "Opprett bruker", path: "/log-out" },
    ]);
    const loggedIn = ref(false);
    const menuVisible = ref(false);

    const toggleMenu = (): void => {
      menuVisible.value = !menuVisible.value;
    };

    return {
      loggedInOptions,
      loggedOutOptions,
      loggedIn,
      menuVisible,
      toggleMenu,
    };
  },
});
</script>

<style lang="scss">
@import url("https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css");

$primary-color: #282828;
$secondary-color: #ea4b4b;

.the-header {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  padding: 30px;
}

.menu-box {
  grid-column: 3/4;
  justify-self: end;
}

.fa-bars {
  font-size: 1.2em;
}

#menu-toggle {
  position: absolute;
  height: 100%;
  background-color: #F9F9F9;
  width: 200px;
  float:right;
  z-index: 1;
  right: 0px;
  top: -5px;

}

.logo-nav {
  justify-self: start;
}

.menu-options {
  padding: 20px;
  margin: 10px;
  text-align: left;
  border-radius: 5px;
}

.menu-options:hover {
  background-color: $secondary-color;
  color: #ffffff;
}

#close-menu {
  padding: 33px;
  text-align: right;

}

.fa-times {
  font-size: 1.2em;
}


</style>