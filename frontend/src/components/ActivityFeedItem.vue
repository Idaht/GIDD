<template>
    <div id="activity" @click="activityClicked">
        <img title="Bilde av aktivitet" src=""/>
        <div>
            <h3>{{ activityData.description }}</h3>
            <h4>{{ details }}</h4>
        </div>
    </div>
</template>

<script lang="ts">


import { computed, defineComponent } from "vue";
import IActivity from "@/interfaces/IActivity.interface";

export default defineComponent({
  name: "ActivityPreviewFeed",

  props: {
    activityData: {
        type: Object as () => IActivity,
        required: true,
    }
  },

    setup(props) {
        const details = computed(() : string => {
            return props.activityData.startTime + " | " + props.activityData.place + ", " + props.activityData.city;
        });

        const activityClicked = () : void => {
            //TODO: Route til siden som viser aktiviteten
            console.log("Activity clicked: " + props.activityData.id);
        };

        return {
            details,
            activityClicked,
        }
    },
});
</script>

<style scoped>

#activity {
    cursor: pointer;
}
</style>
