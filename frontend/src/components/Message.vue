<template>
    <div id="message">
        <p> {{messageData.user.forename}} {{ messageData.timeSent }} {{ messageData.message }}</p>
    </div>  
</template>

<script lang="ts">
import { defineComponent } from "vue";
import IMessage from "@/interfaces/Message.interface";

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