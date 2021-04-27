export default interface IEditActivity {
    activityId: number;
    city: string;
    description: string;
    type: string;
    maxNumberOfParticipants: number;
    difficulty: string;
    durationMinutes: number;
    equipment: string;
    place: string;
    privateActivity: boolean;
    startTime: string;
    title: string;
    activityPicture?: string;
  }
  