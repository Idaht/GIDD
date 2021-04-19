export default interface IActivity {
    id: number,
    description: string,
    equipment: string,
    difficulty: string, //??
    city: string,
    place: string,
    longitude: number,
    latitude: number,
    startTime: string, //Sjekk det er en LocalDateTime type
    durationMinutes: number,
    isPrivateActivity: boolean,
  }