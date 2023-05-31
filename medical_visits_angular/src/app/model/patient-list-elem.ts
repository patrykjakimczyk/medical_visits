export type Address = {

}

export class PatientListElem {
  id: number;
  role: string;
  firstName: string;
  lastName: string;
  pesel: string;
  phoneNr: string;
  email: string;
  assignedDoctorId: number;

  constructor(
    id: number,
    role: string,
    firstName: string,
    lastName: string,
    pesel: string,
    phoneNr: string,
    email: string,
    assignedDoctorId: number,
    ) {
    this.id = id;
    this.role = role;
    this.firstName = firstName;
    this.lastName = lastName;
    this.pesel = pesel;
    this.phoneNr = phoneNr;
    this.email = email;
    this.assignedDoctorId = assignedDoctorId;
  }
}

// export class PatientDetails extends PatientListElem {

// }
