<template>
    <div>
        <h1>Endre Aktivitet</h1>
        <img src="" alt="Aktivitetsbilde"/>
        <ImageSelector 
        labelName=""
        @imageSelected="onSelectedImage"
        @removeImage="onRemoveImage"
        />
        <h1> {{ activity.title }} </h1>
        <h4>Endre tittel på aktiviteten</h4>
        <input v-model="activity.title" type="title" placeholder="Tittel">
        <p v-if="!isTitleValid">Oppgi gyldig tittel</p>

        <h4>Endre startdato og -tidspunkt</h4>
        <select v-model="activity.selectedYear" name="year">
            <option hidden disabled value>Velg år</option>
            <option v-for="(year, index) in availableYears" :value="year" :key="index">
                {{ year }}
            </option>
        </select>
        <select v-model="selectedMonth" name="month">
            <option hidden disabled value>Velg måned</option>
            <option v-for="(month, index) in months" :value="month.name" :key="index">
            {{ month.name }}
            </option>
        </select>
        <select v-model="selectedDay" name="day">
            <option hidden disabled value>Velg dato</option>
            <option v-for="index in daysInCurrentMonth" :value="index" :key="index">
            {{ index }}
            </option>
        </select>

        <select v-model="selectedHour" name="hour">
            <option hidden disabled value>Velg time</option>
            <option v-for="index in hoursList" :value="index" :key="index">
            {{ index }}
            </option>
        </select>
        <select v-model="selectedMinute" name="minutes">
            <option hidden disabled value>Velg minutt</option>
            <option v-for="index in minutes" :value="index" :key="index">
            {{ index }}
            </option>
        </select>
        <p v-if="!isStartTimeValid">Oppgi gyldig starttid</p>

        <h4>Endre sted</h4>
        <p>Legg til et fysisk sted der aktiviteten skal ta plass</p>
        <input v-model="activity.place" type="place" placeholder="Sted">
        <input v-model="activity.city" type="place" placeholder="By">
        <p v-if="!isPlaceValid">Oppgi et gyldig sted</p>

        <h4>Endre aktivitets typen</h4>
        <input v-model="activity.type" type="type" placeholder="Type aktivitet">
        <p v-if="!isTypeValid">Oppgi gyldig type aktivitet</p>

        <h4>Endre maks antall deltagere</h4>
        <input v-model="activity.numberOfParticipants" type="numberOfParticipants" placeholder="Maks antall deltagere">
        <p v-if="!isNumberOfParticipantsValid">Oppgi gyldig maks antall deltagere</p>

        <h4>Endre varigheten på aktiviteten</h4>
        <input v-model="activity.duration" type="duration" placeholder="Varighet">
        <p v-if="!isDurationValid">Oppgi gyldig varighet</p>

        <h4>Endre beskrivelse</h4>
        <p>Legg til en kort beskrivelse av aktiviteten (frivillig)</p>
        <input v-model="activity.description" type="description" placeholder="Beskrivelse">
        <p v-if="isDescriptionValid">Oppgi gyldig beskrivelse</p>

        <h4>Endre utstyr</h4>
        <p>Legg til utstyr som trengs for å gjennomføre aktiviteten (frivillig)</p>
        <input v-model="activity.equipment" type="equipment" placeholder="Utstyr">

        <h4>Endre belastningsnivå</h4>
        <h5>Hva slags belastningsnivå er aktiviteten?</h5>
        <input v-model="isEasy" type="checkbox" id="easy" name="easy" />
        <label for="easy">Lett</label><br />
        <input v-model="isMedium" type="checkbox" id="medium" name="medium" />
        <label for="medium">Medium</label><br />
        <input v-model="isHard" type="checkbox" id="hard" name="hard" />
        <label for="hard">Høy</label><br />
        <p v-if="isDifficultyValid">Oppgi gyldig vanskelighetsgrad</p>

        <p v-if="!feedbackMissingInfo">Sjekk at du har fylt inn all nødvendig informasjon</p>
        <button 
        @click="saveActivityChanges" 
        id="saveButton" 
        :disabled="!isValidForm">LAGRE</button> 
        <button @click="cancelActivity" id="cancelButton">AVLYS</button>
    </div>
</template>

<script lang="ts">
import { defineComponent, ref, onBeforeMount, computed, Ref } from "vue";
import { useRouter } from "vue-router";
import axios from "@/axiosConfig";
import IEditActivity from "@/interfaces/IEditActivity.interface";
import Month from "@/interfaces/Month.interface";
import ImageSelector from "@/components/ImageSelector.vue";

export default defineComponent ({
    name: "EditActivity",
    components: { ImageSelector },
    props: { id: {required: true}},

    setup(props) {
        const router = useRouter();
        const activity = ref({});
        const numberOfParticipants = ref();
        const durartion = ref();
        const selectedYear = ref("");
        const selectedMonth = ref("");
        const selectedDay = ref("");
        const currentYear = new Date().getFullYear();
        const limitForUpperYear = 10;
        const selectedHour = ref("");
        const selectedMinute = ref("");
        const isEasy = ref(false);
        const isMedium = ref(false);
        const isHard = ref(false);
        const feedbackError = ref(false);
        enum difficultyValue {
            easy = 1,
            medium = 2,
            hard = 4,
        };

        const onSelectedImage = (image: string) => {
            activity.picture = image;
        }

        const onRemoveImage = () => {
            delete activity.picture;
        }

        const feedbackMissingInfo = ref(false);

        const daysInFebruary = computed(() => {
            return isLeapYear.value ? 29 : 28;
        });

        const months: Ref<Month[]> = ref([
            { name: "Januar", numberOfDays: 31, monthNumber: 1 },
            { name: "Februar", numberOfDays: daysInFebruary, monthNumber: 2 },
            { name: "Mars", numberOfDays: 31, monthNumber: 3 },
            { name: "April", numberOfDays: 30, monthNumber: 4 },
            { name: "Mai", numberOfDays: 31, monthNumber: 5 },
            { name: "Juni", numberOfDays: 30, monthNumber: 6 },
            { name: "Juli", numberOfDays: 31, monthNumber: 7 },
            { name: "August", numberOfDays: 31, monthNumber: 8 },
            { name: "September", numberOfDays: 30, monthNumber: 9 },
            { name: "Oktober", numberOfDays: 31, monthNumber: 10 },
            { name: "November", numberOfDays: 30, monthNumber: 11 },
            { name: "Desember", numberOfDays: 31, monthNumber: 12 },
           ]);

        const hoursList = ref([
            "00",
            "01",
            "02",
            "03",
            "04",
            "05",
            "06",
            "07",
            "08",
            "09",
            "10",
            "11",
            "12",
            "13",
            "14",
            "15",
            "16",
            "17",
            "18",
            "19",
            "20",
            "21",
            "22",
            "23",
            ]);

        const minutes = [
            "00",
            "01",
            "02",
            "03",
            "04",
            "05",
            "06",
            "07",
            "08",
            "09",
            ];

        onBeforeMount(() => {
            let n = "0";
            for (let i = 10; i < 60; i++) {
                n = String(i);
                // eslint-disable-next-line vue/no-side-effects-in-computed-properties
                minutes.push(n);
            }
        return minutes;
        });
         /*
        onBeforeMount(async () => {
            try {
                const response = await axios.get(`/activities/${props.id}`);
                activity.value = response.data;
            } catch {
                router.push("/error");
            }
        });*/

        const isTitleValid = computed(() => {
            return activity.title !== "";
        })
        const isPlaceValid = computed(() => {
            return (activity.value.place.trim() !== "" && activity.value.city !== "");
        });

        const isStartTimeValid = computed(() => {
            return (activity.value.startTime.trim() !== "");
        });

        const isTypeValid = computed(() => {
            return (activity.value.type.trim() !== "");
        });

        const isNumberOfParticipantsValid = computed(() => {
            return (activity.numberOfParticipants.value !== "" &&
                activity.value.numberOfParticipants >= 0 && activity.value.numberOfParticipants <= maxNumberOfParticipants);
        });

        const isDescriptionValid = computed(() => {
            return (activity.value.description !== "");
        });

        const isEquipmentNeeded = computed(() => {
            return (activity.value.equipment !== "");
        });

        const isDurationValid = computed(() => {
            return (activity.durartion.value !== "" &&
            !isNaN(Number(activity.durartion.value)) &&
            Number(activity.durartion.value) > 0 &&
            !activity.duration.value.includes("-"));
        });
        
        const isDateTimeValid = computed(() => {
            return (
                selectedYear.value !== "" ||
                selectedMonth.value !== "" ||
                selectedDay.value !== "" ||
                selectedHour.value !== "" ||
                selectedMinute.value !== ""
            );
        });

        const isValidForm = computed(() => {
            return (isPlaceValid && 
            isStartTimeValid && 
            isTypeValid &&
            isNumberOfParticipantsValid &&
            isDescriptionValid);
        });


        const isDifficultyValid = computed(() => {
            return (
                activity.difficulty.value !== -1 ||
                isEasy.value ||
                isMedium.value ||
                isHard.value
            );
        });

        const calculateDifficulty = computed(() => {
            let difficultyNumber = 0;
            if (isEasy.value) {
                difficultyNumber += difficultyValue.easy;
            }
            if (isMedium.value) {
                difficultyNumber += difficultyValue.medium;
            }
            if (isHard.value) {
                difficultyNumber += difficultyValue.hard;
            }
            return difficultyNumber;
        });

        const activityDTO: IEditActivity = {
            activityId: 2,
            city: "city",
            description: "desc",
            difficulty: "diff",
            durationMinutes: 50,
            equipment: "equip",
            place: "place",
            privateActivity: false,
            startTime: "tid",
            title: "navn"
        }

        const saveActivityChanges = async(): Promise<void> => {
            if (isValidForm.value) {
                try {
                    const response = await axios.post(`/activities/${props.id}`, activityDTO);
                    if (response.status === 201) {
                        window.alert("Endringene ble lagret");
                    }
                } catch (error) {
                    router.push("/error");
                }
            }
        };

        const cancelActivity = async(): Promise<void> => {
            if (window.confirm("Er du sikker på at du vil avlyse aktiviteten din?")) {
                try {
                    const response = await axios.delete(`/activities/${props.id}`);
                    if (response.status === 200) {
                        router.replace("/activity-feed");
                    }
                } catch (error) {
                    router.push("/error");
                }
            }
        };

        const availableYears = computed(() => {
            const years = [];
            for (let i = currentYear + limitForUpperYear; i > currentYear - 1; i--) {
                years.push(i);
            }
        return years.reverse();
        });

            /**
             * Method to return a boolean depending
             * on if the year selected is a leap year
             */
        const isLeapYear = computed(() => {
            if (parseInt(selectedYear.value) === 0) return false;
            return (
                parseInt(selectedYear.value) % 400 === 0 ||
                (parseInt(selectedYear.value) % 100 !== 0 &&
                parseInt(selectedYear.value) % 4 === 0)
            );
        });

        const daysInCurrentMonth = computed(() => {
            if (selectedMonth.value === "") {
                return 0;
            }
            return months.value.find(
                (month: { name: string }) => month.name === selectedMonth.value
            )?.numberOfDays;
        });

        const makeDateTime = computed(() => {
            return (
                selectedYear.value +
                "-" +
                getMonthNumber.value +
                "-" +
                getDay.value +
                " " +
                selectedHour.value +
                ":" +
                selectedMinute.value
            );
        });

        const getDay = computed(() => {
            if (parseInt(selectedDay.value) < 10) {
                return "0" + selectedDay.value;
            }
            return selectedDay.value.toString();
        });

        const getMonthNumber = computed(() => {
            if (selectedMonth.value === "Januar") {
                return "01";
            } else if (selectedMonth.value === "Februar") {
                return "02";
            } else if (selectedMonth.value === "Mars") {
                return "03";
            } else if (selectedMonth.value === "April") {
                return "04";
            } else if (selectedMonth.value === "Mai") {
                return "05";
            } else if (selectedMonth.value === "Juni") {
                return "06";
            } else if (selectedMonth.value === "Juli") {
                return "07";
            } else if (selectedMonth.value === "August") {
                return "08";
            } else if (selectedMonth.value === "September") {
                return "09";
            } else if (selectedMonth.value === "Oktober") {
                return "10";
            } else if (selectedMonth.value === "November") {
                return "11";
            } else if (selectedMonth.value === "Desember") {
                return "12";
            }
            return "Noe gikk galt";
        });

        return {
            numberOfParticipants,
            activity,
            activityDTO,
            durartion,
            isValidForm,
            isDifficultyValid,
            isTitleValid,
            isDurationValid,
            calculateDifficulty,
            isEquipmentNeeded,

            onSelectedImage,
            onRemoveImage,

            availableYears,
            daysInCurrentMonth,
            isLeapYear,
            selectedYear,
            selectedMonth,
            selectedDay,
            selectedHour,
            selectedMinute,
            months,
            hoursList,
            minutes,
            feedbackMissingInfo,

            saveActivityChanges,
            cancelActivity
        }
    },
});
</script>

<style scoped>

</style>