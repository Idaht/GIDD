export default interface IActivity {
  id: number;
  title: string;
  type: string;
  organizer: string;
  maxParticipants: number;
  description: string;
  equipment: string;
  difficulty: string; //??
  city: string;
  place: string;
  longitude: number;
  latitude: number;
  startTime: string; //Sjekk det er en LocalDateTime type
  durationMinutes: number;
  isPrivateActivity: boolean;
}
