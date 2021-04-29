<template>
  <div class="menu-item">
    <a>{{ notification }}</a
    ><a @click="removeNotification(index)">x</a>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  name: "notificationItem",
  props: {
    notification: {
      required: true,
      type: String,
    },
  },
  setup(props, { emit }) {
    const removeNotification = (index: number): void => {
      emit("removeNotification", index);
    };

    return {
      removeNotification,
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
