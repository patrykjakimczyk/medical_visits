export type Speciality = {
  id: number,
  specialityName: string
}

export class DoctorListElem {
  id: number;
  firstName: string;
  lastName: string;
  specialities: Speciality[];
  phoneNr: string;
  email: string;


  constructor(
    id: number,
    firstName: string,
    lastName: string,
    specialities: Speciality[],
    phoneNr: string,
    email: string
    ) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.specialities = specialities;
    this.phoneNr = phoneNr;
    this.email = email;
  }
}
