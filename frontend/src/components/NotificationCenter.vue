<template>
  <div class="menu-item" @click="isOpen = !isOpen">
    <a href="#">
      <img v-if="!hasNotifications" src="../../img/verified.png" height="50" />
      <img v-else src="../assets/hamster-pfp.jpg" height="50" />
      <!--Sett inn en liten bjelle elns, evt en v-if med en bjelle uten rødt merke og en med?
      Hvis bruker har notifikasjoner, rød bjelle, hvis ikke, ingen-->
    </a>
    <transition name="fade" appear>
      <div class="sub-menu" v-if="isOpen">
        <NotificationItem
          v-for="(notification, index) in notifications"
          :key="index"
          :notification="notification"
          @removeNotification="removeNotification($event)"
        />
      </div>
    </transition>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent, ref, Ref } from "vue";
import NotificationItem from "@/components/NotificationItem.vue";

export default defineComponent({
  name: "notificationCenter",
  components: { NotificationItem },
  setup() {
    const isOpen: Ref<boolean> = ref(false);
    const notifications = ref(["Notifikasjon 0", "Notifikasjon 1"]); //TODO hent fra backend

    const hasNotifications = computed(() => {
      return notifications.value.length !== 0;
    });

    const removeNotification = (index: number): void => {
      notifications.value.splice(index, 1);
    };
    return {
      notifications,
      removeNotification,
      hasNotifications,
      isOpen,
    };
  },
});
</script>
