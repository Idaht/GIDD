<template>
  <nav>
    <NotificationItem :notifications="notifications" />
  </nav>
</template>

<script lang="ts">
import { computed, defineComponent, ref } from "vue";
import NotificationItem from "@/components/NotificationItem.vue";

export default defineComponent({
  name: "navbar",
  components: { NotificationItem },
  setup() {
    const makeNotifications = computed(() => {
      const array = [];
      //TODO connect to backend
      for (let i = 0; i < 5; i++) {
        const not = { notificationTitle: "Notifikasjon " + i };
        array.push(not);
      }
      return array;
    });
    const notifications = ref(makeNotifications);

    return {
      notifications,
    };
  },
});
</script>
