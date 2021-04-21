import LogInUser from "./LoginUser.interface";

export default interface SignUpUser extends LogInUser {
    forename: string, 
    surname: string, 
    birthdate: string,
    //TODO: ta inn bilde?
  }
  