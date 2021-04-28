<template>
  <div class="menu-item" @click="isOpen = !isOpen">
    <a href="#">
      <img src="../../img/verified.png" height="50" />
      <!--Sett inn en liten bjelle elns, evt en v-if med en bjelle uten rødt merke og en med?
      Hvis bruker har notifikasjoner, rød bjelle, hvis ikke, ingen-->
    </a>
    <transition name="fade" appear>
      <div class="sub-menu" v-if="isOpen">
        <div
          v-for="(notification, i) in notifications"
          :key="i"
          class="menu-item"
        >
          <a>{{ notification.notificationTitle }}</a>
        </div>
      </div>
    </transition>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";

export default defineComponent({
  name: "notificationItem",
  props: ["notifications"],
  setup() {
    const isOpen = ref(false);

    return {
      isOpen,
    };
  },
});
</script>

<style scoped>
nav .menu-item .sub-menu {
  position: absolute;
  background-color: rgb(233, 216, 194);
  top: calc(100% + 18px);
  left: 50%;
  transform: translateX(-50%);
  width: max-content;
  border-radius: 0px 0px 16px 16px;
}
</style>
