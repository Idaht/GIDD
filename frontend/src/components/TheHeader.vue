<template>
  <div class="the-header">
    <span id="login-check" v-if="loggedIn">
      <router-link to="/activity-feed">
        <!-- Add proper link -->
        <img src="../../img/logo.png" alt="Logo" height="50" />
        <!-- Change to real logo -->
      </router-link>
    </span>
    <span v-else>
      <router-link to="/homepage">
        <!-- Add proper link -->
        <img src="../../img/logo.png" alt="Logo" height="50" />
        <!-- Change to real logo -->
      </router-link>
    </span>
    <div class="menu-box">
      <span v-if="loggedIn">
        <img
          @click="toggleMenu"
          src="../../img/menu.png"
          alt="Logget inn meny"
          height="50"
        />
        <!--Change to logged in menu img-->
      </span>
      <span v-else>
        <img
          @click="toggleMenu"
          src="../../img/menu.png"
          alt="Logget ut meny"
          height="50"
        />
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
          <span v-for="(option, index) in loggedOutOptions" :key="index">
            {{ option.title }}
          </span>
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
