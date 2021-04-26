<template>
  <div class="container">
    <form @submit.prevent="">
      <div class="form-group" id="file-button-group">
        <div id="label-input">
          <label for="fileInput">{{ labelName }}</label>
          <input
            ref="fileInput"
            id="fileInput"
            type="file"
            @change="uploadFile"
          />
        </div>
        <button @click="removeImage">Fjern bildet</button>
      </div>
    </form>
    <img :src="imageData" alt="Profilbildet visining:" />
  </div>
</template>

<script lang="ts">
import { defineComponent, Ref, ref } from "vue";

export default defineComponent({
  name: "ImageSelector",
  props: {
    labelName: String,
  },
  setup(props, { emit }) {
    const imageData = ref("") as Ref<string | ArrayBuffer | null | undefined>;
    const fileInput = ref(null) as Ref<any>;

    const uploadFile = (event: any) => {
      let input = event.target.files[0];
      if (input) {
        let reader = new FileReader();
        reader.onload = (e) => {
          imageData.value = e.target?.result;
          emit("imageSelected", imageData.value);
        };
        reader.readAsDataURL(input);
      }
    };

    const removeImage = () => {
      imageData.value = "";
      fileInput.value.value = "";
      emit("removeImage");
    };
    return {
      uploadFile,
      imageData,
      removeImage,
      fileInput,
    };
  },
});
</script>

<style scoped>
label {
  display: block;
}
img {
  width: 150px;
  height: 150px;
  border-radius: 50%;
}
#label-input {
  display: inline-block;
}
</style>
