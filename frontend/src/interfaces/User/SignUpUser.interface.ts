import LogInUser from "./LoginUser.interface";

export default interface SignUpUser extends LogInUser {
    forename: string, 
    surname: string, 
    email: string
    dateOfBirth: string,
    password: string,
    profilePicture?: string,
  }
  