export class DoctorListElem {
  id: number;
  firstName: string;
  lastName: string;
  specializations: string[];
  phoneNr: string;
  email: string;


  constructor(
    id: number,
    firstName: string,
    lastName: string,
    specializations: string[],
    phoneNr: string,
    email: string
    ) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.specializations = specializations;
    this.phoneNr = phoneNr;
    this.email = email;
  }
}
