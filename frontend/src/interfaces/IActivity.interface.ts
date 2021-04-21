export default interface IActivity {
  activityId: number;
  city: string;
  description: string;
  difficulty: string;
  durationMinutes: number;
  equipment: string;
  latitude: number;
  longitude: number;
  organizerId: number;
  place: string;
  privateActivity: boolean;
  startTime: string;
  title: string;
}
