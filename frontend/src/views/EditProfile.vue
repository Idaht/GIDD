<template>
  <div>
    <h1>Instillinger</h1>
    <img src="" alt="Profilbilde" />
    <ImageSelector
      labelName=""
      @imageSelected="onSelectedImage"
      @removeImage="onRemoveImage"
    />

    <p>Endre fornavn</p>
    <input v-model="user.forename" type="name" placeholder="Nytt fornavn" />
    <p>Endre etternavn</p>
    <input v-model="user.surname" type="name" placeholder="Nytt etternavn" />
    <p>Endre email</p>
    <input v-model="user.email" type="email" placeholder="Ny email" />
    <p v-if="emailIsNotEmpty && !isValidEmail">
      E-postadressen må inneholde @ og .
    </p>
    <p>Endre passord</p>
    <input v-model="password" type="password" placeholder="Nytt passord" />
    <input
      v-model="repeatPassword"
      type="password"
      placeholder="Gjenta passord"
    />
    <p v-if="!matchingPasswords && passwordIsNotEmpty">
      Passordene må være like
    </p>
    <p v-if="!passwordIsValid && passwordIsNotEmpty">
      Nytt passord må inneholde minst 8 tegn
    </p>
    <p>Gammelt passord</p>
    <input v-model="oldPassword" type="password" />
    <p v-if="!oldPasswordWasCorrect">
      Gammelt passord var ikke riktig. Besøk:
      <router-link to="/forgotten-password">Glemt passord</router-link> om du
      har glemt ditt gamle passord
    </p>
    <button
      :disabled="!isValidForm"
      @click="saveProfileChanges"
      alt="Knapp som lagrer profilendringer"
    >
      Lagre endringer
    </button>
    <button @click="deleteUser" alt="Knapp for å slette brukeren">
      Slett brukeren
    </button>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent, Ref, ref } from "vue";
import { useRouter } from "vue-router";
import axios from "@/axiosConfig";
import { useStore } from "@/store";
import User from "@/interfaces/User/User.interface";
import EditUser from "@/interfaces/User/EditUser.interface";
import ImageSelector from "@/components/ImageSelector.vue";

export default defineComponent({
  name: "EditProfile",
  components: { ImageSelector },
  setup() {
    const store = useStore();
    const router = useRouter();
    //Using ref since we are setting values
    const user: Ref<User> = ref(store.getters.user);

    /**
     * Checks if name is valid; that both name textareas are not empty
     */
    const isValidName = computed(() => {
      return (
        user.value.forename.trim() !== "" && user.value.surname.trim() !== ""
      );
    });

    /**
     * When image is selected
     */
    const onSelectedImage = (image: string) => {
      userDTO.profilePicture = image;
    };

    /**
     * When image is removed
     */
    const onRemoveImage = () => {
      delete userDTO.profilePicture;
    };

    //Password
    const password = ref("");
    const passwordFeedback = ref("");
    const repeatPassword = ref("");

    /**
     * Checks if the password and the repeat password areas
     * match
     */
    const matchingPasswords = computed(() => {
      return password.value == repeatPassword.value;
    });
    const numberOfCharacterPass = 8;

    /**
     * Checks if password is valid; that the number of characters
     * is 8 or above
     */
    const passwordIsValid = computed(() => {
      return password.value.length >= 8;
    });

    /**
     * Checks that the password is not empty
     */
    const passwordIsNotEmpty = computed(() => {
      return password.value.trim() !== "";
    });

    /**
     * Checks if password is valid; that password is not empty and that
     * the passwords match
     */
    const isValidPassword = computed(() => {
      return matchingPasswords.value && passwordIsNotEmpty.value;
    });

    /**
     * Makes the password feedback, either that it is missing
     * characters or if it is valid
     */
    const makePasswordFeedback = computed(() => {
      if (password.value.length < numberOfCharacterPass) {
        return (
          passwordFeedback.value +
          "Passordet må være minst " +
          numberOfCharacterPass +
          " tegn"
        );
      }
      return passwordFeedback.value + "Passordet er gyldig";
    });

    //Gammelt passord
    const oldPassword = ref("");

    /**
     * Checks that the old password area is not empty
     */
    const oldPasswordIsNotEmpty = computed(() => {
      return oldPassword.value.trim() !== "";
    });

    //TODO check this with backend?
    const oldPasswordWasCorrect = ref(true);

    //Email

    /**
     * Checks that email area is not empty
     */
    const emailIsNotEmpty = computed(() => {
      return user.value.email.trim() !== "";
    });

    /**
     * checks if email is valid; that it includes "@" and "."
     */
    const isValidEmail = computed(() => {
      return user.value.email.includes("@") && user.value.email.includes(".");
    });

    /**
     * Checks if the form is valid; that the password is empty or, that the password is valid
     * and old password is not empty and that the passwords match. Then it checks if the name
     * and email are valid
     */
    const isValidForm = computed(() => {
      return (
        (!passwordIsNotEmpty.value ||
          (isValidPassword.value &&
            oldPasswordIsNotEmpty.value &&
            passwordIsValid.value &&
            matchingPasswords.value)) &&
        isValidEmail.value &&
        isValidName.value
      );
    });

    /**
     * EditUser object to send to backend
     */
    const userDTO: EditUser = {
      userId: user.value.userId,
      email: user.value.email,
      forename: user.value.forename,
      surname: user.value.surname,
    };

    /**
     * Method to save the profile changes made. First checks if form is valid,
     * if so, starts saving. Password is not empty, add new and old password
     * to the editUser object. If not profile picture, set it to string "null".
     * Send response via axios, await response from update user
     * and set user to the new user
     */
    const saveProfileChanges = async (): Promise<void> => {
      oldPasswordWasCorrect.value = true;
      if (isValidForm.value) {
        try {
          //TODO: change to redirect to something went wrong site
          //TODO: handle bug where _ctx.user is undefined
          //Needs to update the userDTO here, or the old user is sent
          userDTO.userId = user.value.userId;
          userDTO.email = user.value.email;
          userDTO.forename = user.value.forename;
          userDTO.surname = user.value.surname;

          if (passwordIsNotEmpty.value) {
            userDTO.newPassword = password.value;
            userDTO.oldPassword = oldPassword.value;
          }
          if (!userDTO.profilePicture) userDTO.profilePicture = "null";

          const response = await axios.post(
            `/users/${userDTO.userId}`,
            userDTO
          );
          await store.dispatch("updateUser", response.data);
          user.value = store.getters.user;
        } catch (error) {
          //TODO fix bug with error.response is undefined
          if (error.response.status === 400) {
            oldPasswordWasCorrect.value = false;
          } else {
            router.push("/error");
          }
        }
      }
    };

    /**
     * Delete user, first make a confirm window
     */
    const deleteUser = async (): Promise<void> => {
      if (window.confirm("Er du sikker på at du vil slette brukeren din")) {
        try {
          const response = await axios.delete(
            `/users/${store.getters.user.userId}`
          );
          if (response.status === 200) {
            store.dispatch("logout");
            router.replace("/log-in");
          }
        } catch (error) {
          router.push("/error");
          //TODO add errorhandling
        }
      }
    };

    return {
      user,
      password,
      passwordIsValid,
      passwordFeedback,
      passwordIsNotEmpty,
      onSelectedImage,
      onRemoveImage,
      saveProfileChanges,
      makePasswordFeedback,
      emailIsNotEmpty,
      repeatPassword,
      matchingPasswords,
      deleteUser,
      isValidEmail,
      isValidForm,
      oldPassword,
      oldPasswordWasCorrect,
    };
  },
});
</script>
