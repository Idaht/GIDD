<template>
    <div id="message">
        <p> {{messageData.forename}} {{ messageData.time }}: {{ messageData.message }}</p>
    </div>  
</template>

<script lang="ts">
import { defineComponent } from "vue";
import IMessage from "@/interfaces/IMessage.interface";

export default defineComponent ({
    name: "Message",
    props: {
    messageData: {
        type: Object as () => IMessage,
        required: true,
        },
    },
});
</script>