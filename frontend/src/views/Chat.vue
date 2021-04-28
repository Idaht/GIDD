<template>
    <div>
        <div class="header">
            <h1> {{ activity.title }} </h1>
            <button id="backButton" @click="goBack">BACK</button>
        </div>
        <hr>
        <div class="displayMessages" id="displayContainer">
            <div class="messages"
            v-for="message in messages"
            :key="message.messageId"
            :class="{ classForUserMessages: message.user.userId === userId.value }"
            >
                <Message :messageData="message"/>
            </div>
        </div>
        <hr>
        <div class="lower">
            <input type="message" id="messageInput" placeholder="Skriv melding her..."
            v-model="messageInput"
            @keyup.enter="sendMessage">
            <button @click="sendMessage">SEND</button>
        </div>
        
    </div>
</template>


<script lang="ts">
import { defineComponent, Ref, ref, onBeforeMount, computed } from "vue";
import { useRouter } from "vue-router";
import IMessage from "@/interfaces/Message.interface";
import axios from "@/axiosConfig";
import IUserInMessage from "@/interfaces/User/UserInMessage.interface";
import { useStore } from "@/store" 
import Message from "@/components/Message.vue";


export default defineComponent({
    name: "Chat",
    components: {
        Message,
    },
    props: { id: {required: true, type: String} },
    setup(props) {
        
        const messageInput = ref("");
        const store = useStore();
        const router = useRouter();

        const userId = computed(() => {
            return store.getters.user.userId;
        });

        const activity = ref({});

        onBeforeMount(async () => {
            try {
                const response = await axios.get(`/activities/${props.id}`); 
                activity.value = response.data;
            } catch (error) {
                router.push("/error");
            }
        });
        
        const forename = computed(() => {
            return store.getters.user.forename;
        });

        const messages:Ref<IMessage[]> = ref([]);

        let dateTime = new Date();

        const sendMessage = () => {
            if (messageInput.value === "") return;

            let message:IMessage = {
                message: messageInput.value,
                timeSent: dateTime.toString(),
                messageId: 2, 
                user: { 
                    userId: userId.value,
                    forename: forename.value,
                } as IUserInMessage,
            } as IMessage;

            messages.value.unshift(message);
            
            //Removes message from input field, so the user doesnt have to delete/remove their message before writing a new one
            messageInput.value = "";
        };

        const goBack = ():void => {
            router.back(); 
        };


        /**
        * Retrives messages from the database
        */
        onBeforeMount(async () => {
            try {
                const response = await axios.get(`/chats/${props.id}/messages`); 
                console.log(response);
                messages.value = response.data as IMessage[];
            } catch (error) {
                router.push("/error");
            }
        });

        return {
            goBack,
            sendMessage,
            messages,
            userId, 
            forename,
            messageInput,
            activity
        };
    },
});
</script>

<style scoped>

#messageInput {
    width: 90%;
}

.messages {
    border: black;
    border-radius: 20px;
    text-align: left;
    margin-left: 0.5%;  
}

.classForUserMessages {
    text-align: right;
    margin-right: 0.5%;
}

.displayMessages {
    height: 140px;
    overflow-y: scroll;
    overflow: auto;
    display: flex;
    flex-direction: column-reverse;
}

</style>

