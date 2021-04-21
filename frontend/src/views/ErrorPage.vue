<template>
  <h1>Ops! Noe gikk galt!</h1>
  <button @click="backToFeed">Gå tilbake og prøv igjen</button>
</template>

<script lang="ts">
import { useRouter } from "vue-router";
import { defineComponent, ref } from "vue";

export default defineComponent({
  setup() {
    const router = useRouter();

    const backToFeed = (): void => {
      router.push("/activity-feed");
    };
    return {
      backToFeed,
    };
  },
});
</script>
